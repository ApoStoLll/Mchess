package com.missclick.mchess;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

class Controller {
    private int[][] field;
    private ArrayList<figures> black;
    private ArrayList<figures> white;
    private ArrayList<figures> deadBlack;
    private ArrayList<figures> deadWhite;
     ArrayList<Coordinate> moveList;
    private figures selected = null;
     int num = 0;
    private String situation = null;
    private ArrayList<Step> steps;
    Controller(){
        black = new ArrayList<>();
        white = new ArrayList<>();
        deadBlack = new ArrayList<>();
        deadWhite = new ArrayList<>(); //
        moveList = new ArrayList<>();
        steps = new ArrayList<>();
        field = new int[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                field[i][j] = 0;
            }
        }
        createFigures();
        //MediaPlayer.create(GameActivity.class,R.raw.shah).start();

    }
    void selectFigure(figures figure){
        cancelSelected();
        if(figure == null) return ;
        if(figure.getColor() == 1) moveList = figure.check(field,new ArrayList<figures>(black), new ArrayList<figures>(white));
        else moveList = figure.check(field, new ArrayList<figures>(white), new ArrayList<figures>(black));
        figure.setSelected(true);
        selected = figure;
        //return figure;
    }

     void cancelSelected(){
        selected = null;

        for(figures figure : black){
            if(figure.isSelected) figure.setSelected(false);
        }
        for(figures figure : white){
            if(figure.isSelected) figure.setSelected(false);
        }
        //moveList = null;
    }

    void revert(int[][] field){
        if(steps.size() > 0) {
            moveBack(steps.get(steps.size()-1));
            num--;
        }
        else Log.d("mylog", "Cant revert");
    }

    public static ArrayList<figures> cloneList(ArrayList<figures> arrayList) {
        ArrayList<figures> clonedList = new ArrayList<figures>(arrayList.size());
        for (figures figure : arrayList) {
            if(figure instanceof Pawn)
                clonedList.add(new Pawn(figure));
            if(figure instanceof Rook)
                clonedList.add(new Rook(figure));
            if(figure instanceof Knight)
                clonedList.add(new Knight(figure));
            if(figure instanceof Bishop)
                clonedList.add(new Bishop(figure));
            if(figure instanceof Queen)
                clonedList.add(new Queen(figure));
        }
        return clonedList;
    }

    void moveBack(Step step){//, int[][] field){
        cancelSelected();
        field = step.getCurrentField();//AI.arrCopy(step.getCurrentField());
        situation = step.situation;
        //Log.d("mylog", "back");
        step.getFigure().setCoordinate(step.getFrom());
        //this.black = cloneList(step.getBlack());
        //this.white = cloneList(step.getWhite());
        //Log.d("mylog", "setCoord");
        step.getFigure().setFirstStep(step.getFirstStep());
       // Log.d("mylog", "setFirst");
        if(step.isHit()) backFigure(step, field);
        steps.remove(steps.get(steps.size()-1));
        Log.d("mylog", "del");
    }

    void move(Step step, int[][] field){
        boolean check = false;
        if(moveList != null)
            for (Coordinate coord : moveList)
                if (coord.getX() == step.getTo().getX() && coord.getY() == step.getTo().getY()) check = true;
        if(!check || step.getFigure() == null) {
            Log.d("Controller", "return");
            //if(moveList == null) Log.d("Controller", "NULL");
            return;
        }
        step.setCurrentField(AI.arrCopy(field));
        //step.setBlack(cloneList(this.black));
        //step.setWhite(cloneList(this.white));
        boolean clear = true;
        if(field[step.getTo().getX()][step.getTo().getY()] != 0) clear = false;
        Log.d("Controller", "field FROM: " + field[step.getFigure().getCoor().getX()][step.getFigure().getCoor().getY()]);
        field[step.getTo().getX()][step.getTo().getY()] = field[step.getFigure().getCoor().getX()][step.getFigure().getCoor().getY()];
        Log.d("Controller", "X: " + step.getTo().getX() + "Y: " + step.getTo().getY());
        Log.d("COntorller field", "field: " + field[step.getTo().getX()][step.getTo().getY()]);
        field[step.getFigure().getCoor().getX()][step.getFigure().getCoor().getY()] = 0;
        if(step.getFigure().getColor() == 0) helpMove(step, white, deadWhite, clear);
        else helpMove(step, black, deadBlack, clear);
        num++;
        moveList = null;
        cancelSelected();
        steps.add(step);
    }

    private void helpMove(Step step, ArrayList<figures> enemy, ArrayList<figures> deadEnemy, boolean clear){
        if(!clear){
            deadEnemy.add(findFigure(step.getTo()));
            enemy.remove(findFigure(step.getTo()));
            step.setHit(true);
        }
        step.situation = situation;
        situation = null;
        ArrayList<figures> allies;
        if(enemy.get(0).getColor() == 0) allies = white;
        else allies = black;
        switch (step.getFigure().move(step.getTo(), field, enemy, allies)) {
            case 1:
                situation = "ШАХ";
                break;
            case 2:
                white.remove(findFigure(step.getTo()));
                white.add(new Queen(1, new Coordinate(step.getTo().getX(), step.getTo().getY())));
                field[step.getTo().getX()][step.getTo().getY()] = -5 + 10*step.getFigure().getColor();
                break;
            case 3:
                situation = "МАТ";
                break;
            case 4:
                situation = "ПАТ";
                break;
        }
    }

     figures findFigure(Coordinate coord){
        for(figures figure : black){
            if(figure.getCoor().getY() == coord.getY() && figure.getCoor().getX() == coord.getX()) {
                return figure;
            }
        }
        for(figures figure : white){
            if(figure.getCoor().getY() == coord.getY() && figure.getCoor().getX() == coord.getX()){
                return figure;
            }
        }
        return null;
    }

    private void backFigure(Step step, int[][] field){
        if(field[step.getTo().getX()][step.getTo().getY()] == 1) {
            figures figur = new Pawn(1,step.getTo());
            if(step.getTo().getY() != 6) figur.setFirstStep(false);
            white.add(figur);
            //if(deadBlack.size() > 0) deadWhite.remove(deadWhite.size()-1);
        }
        if(field[step.getTo().getX()][step.getTo().getY()] == -1) {
            figures figur = new Pawn(0,step.getTo());
            if(step.getTo().getY() != 1) figur.setFirstStep(false);
            black.add(figur);
            //if(deadBlack.size() > 0) deadBlack.remove(deadWhite.size()-1);
        }
        if(field[step.getTo().getX()][step.getTo().getY()] == 2) {
            figures figur = new Rook(1,step.getTo());
            white.add(figur);
        }
        if(field[step.getTo().getX()][step.getTo().getY()] == -2) {
            figures figur = new Rook(0,step.getTo());
            black.add(figur);
        }
        if(field[step.getTo().getX()][step.getTo().getY()] == 3) {
            figures figur = new Knight(1,step.getTo());
            white.add(figur);
        }
        if(field[step.getTo().getX()][step.getTo().getY()] == -3) {
            figures figur = new Knight(0,step.getTo());
            black.add(figur);
        }
        if(field[step.getTo().getX()][step.getTo().getY()] == 3) {
            figures figur = new Bishop(1,step.getTo());
            white.add(figur);
        }
        if(field[step.getTo().getX()][step.getTo().getY()] == -3) {
            figures figur = new Bishop(0,step.getTo());
            black.add(figur);
        }
        if(field[step.getTo().getX()][step.getTo().getY()] == 3) {
            figures figur = new Queen(1,step.getTo());
            white.add(figur);
        }
        if(field[step.getTo().getX()][step.getTo().getY()] == -3) {
            figures figur = new Queen(0,step.getTo());
            black.add(figur);
        }
    }

    private void createFigures(){
        white.add(new King(1, new Coordinate(4, 7)));
        field[4][7] = 6;
        black.add(new King(0, new Coordinate(4, 0)));
        field[4][0] = -6;
        for(int i = 0; i < 8; i ++) {
             black.add(new Pawn(0, new Coordinate(i, 1)));
             field[i][1] = -1;
             white.add(new Pawn(1, new Coordinate(i, 6)));
             field[i][6] = 1;
        }
        black.add(new Rook(0, new Coordinate(0, 0)));
        field[0][0] = -2;
        white.add(new Rook(1, new Coordinate(0, 7)));
        field[0][7] = 2;
        black.add(new Rook(0, new Coordinate(7, 0)));
        field[7][0] = -2;
        white.add(new Rook(1, new Coordinate(7, 7)));
        field[7][7] = 2;
        black.add(new Knight(0, new Coordinate(1, 0)));
        field[1][0] = -3;
        white.add(new Knight(1, new Coordinate(1, 7)));
        field[1][7] = 3;
        black.add(new Knight(0, new Coordinate(6, 0)));
        field[6][0] = -3;
        white.add(new Knight(1, new Coordinate(6, 7)));
        field[6][7] = 3;
        black.add(new Bishop(0, new Coordinate(2, 0)));
        field[2][0] = -4;
        white.add(new Bishop(1, new Coordinate(2, 7)));
        field[2][7] = 4;
        black.add(new Bishop(0, new Coordinate(5, 0)));
        field[5][0] = -4;
        white.add(new Bishop(1, new Coordinate(5, 7)));
        field[5][7] = 4;
        black.add(new Queen(0, new Coordinate(3, 0)));
        field[3][0] = -5;
        white.add(new Queen(1, new Coordinate(3, 7)));
        field[3][7] = 5;
    }
    Step getLastStep() {return steps.get(steps.size() - 1); }
    int[][] getField(){ return field; }
    ArrayList<figures> getBlack(){ return black; }
    ArrayList<figures> getWhite(){ return white; }
    ArrayList<figures> getDeadBlack(){ return deadBlack; }
    ArrayList<figures> getDeadWhite(){ return deadWhite; }
    ArrayList<Coordinate> getMoveList(){ return moveList; }
    int getNum(){ return num; }
    figures getSelected(){return selected;}
    String getSituation() {return situation;}

}
 /*void move(Step step){
       // Log.d("MOVE", "move");
        step.setCurrentField(AI.arrCopy(field));
        step.setWhite(new ArrayList<>(white));
        step.setBlack(new ArrayList<>(black));
        boolean check = false;
        Coordinate coor = step.getTo();
        figures figure = step.getFigure();
        if (moveList != null) {
            for (Coordinate coord : moveList) {
                if (coord.getX() == coor.getX() && coord.getY() == coor.getY()) check = true;
            }
        }
        if (check) {
            if (figure == null) return;
            boolean clear = true;
            if (field[coor.getX()][coor.getY()] != 0) clear = false;
            field[coor.getX()][coor.getY()] = field[figure.getCoor().getX()][figure.getCoor().getY()];
            field[figure.getCoor().getX()][figure.getCoor().getY()] = 0;
            if (figure.getColor() == 0) {
                if (!clear) {
                    deadWhite.add(findFigure(coor));
                    white.remove(findFigure(coor));
                    //Log.d("MOVE", "hit");
                    step.setHit(true);
                }
                situation = null;
                switch (figure.move(coor, field, white, black)) {
                    case 1:
                        situation = "ШАХ";
                        break;
                    case 2:
                        black.remove(findFigure(coor));
                        black.add(new Queen(0, new Coordinate(coor.getX(), coor.getY())));
                        field[coor.getX()][coor.getY()] = -5;
                        break;
                    case 3:
                        situation = "МАТ";
                        break;
                    case 4:
                        situation = "ПАТ";
                        break;
                }
            }
            if (figure.getColor() == 1) {
                if (!clear) {
                    deadBlack.add(findFigure(coor));
                    step.setHit(true);
                    black.remove(findFigure(coor));
                    //Log.d("MOVE", "hit");
                }
                situation = null;
                switch (figure.move(coor, field, black, white)) {
                    case 1:
                        situation = "ШАХ";
                        break;
                    case 2:
                        white.remove(findFigure(coor));
                        white.add(new Queen(1, new Coordinate(coor.getX(), coor.getY())));
                        field[coor.getX()][coor.getY()] = 5;
                        break;
                    case 3:
                        situation = "МАТ";
                        break;
                    case 4:
                        situation = "ПАТ";
                        break;
                }
            }
            num++;
            moveList = null;
        }
        cancelSelected();
        steps.add(step);
    }*/