package com.missclick.mchess;

import android.util.Log;

import java.util.ArrayList;

class Controller {
    private int[][] field;
    private ArrayList<figures> black;
    private ArrayList<figures> white;
    private ArrayList<Coordinate> moveList;
    private Coordinate selected = null;
    private int step = 0;
    Controller(){
        black = new ArrayList<>();
        white = new ArrayList<>();
        moveList = new ArrayList<>();
        field = new int[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                field[i][j] = 0;
            }
        }
        createFigures();
    }
    figures selectFigure(figures figure){
        cancelSelected();
        if(figure == null) return null;
        if(figure.getColor() == 1) moveList = figure.check(field,black,white);
        else moveList = figure.check(field,white,black);
        figure.setSelected(true);
        selected = figure.getCoor();
        return figure;
    }

    private void cancelSelected(){
        selected = null;
        for(figures figure : black){
            if(figure.isSelected) figure.setSelected(false);
        }
        for(figures figure : white){
            if(figure.isSelected) figure.setSelected(false);
        }
    }

    void move(Coordinate coor, figures figure){
        boolean check = false;
        if(moveList != null) {
            for (Coordinate coord : moveList) {
                if (coord.getX() == coor.getX() && coord.getY() == coor.getY()) check = true;
            }
        }
        if(check){
            if(figure == null) return;
            boolean clear = true;
            if ( field[coor.getX()][coor.getY()] != 0) clear = false;
            field[coor.getX()][coor.getY()] = field[figure.getCoor().getX()][figure.getCoor().getY()];
            field[figure.getCoor().getX()][figure.getCoor().getY()] = 0;
            if(figure.getColor() == 0) {
                if(!clear) white.remove(findFigure(coor));
                switch (figure.move(coor, field, white, black)){
                    case 1:
                        Log.d("MYLOG","ШАХ белым!!!");
                        break;
                    case 2:
                        black.remove(findFigure(coor));
                        black.add(new Queen(0, new Coordinate(coor.getX(), coor.getY())));
                        field[coor.getX()][coor.getY()] = -5;
                        break;
                    case 3:
                        Log.d("MYLOG","МАТ белым!!!");
                        break;
                    case 4:
                        Log.d("MYLOG","ПАТ!!!");
                        break;
                }
            }
            if(figure.getColor() == 1) {
                if(!clear) black.remove(findFigure(coor));
                switch (figure.move(coor, field, black, white)){
                    case 1:
                        Log.d("MYLOG","ШАХ черным!!!");
                        break;
                    case 2:
                        white.remove(findFigure(coor));
                        white.add(new Queen(1, new Coordinate(coor.getX(), coor.getY())));
                        field[coor.getX()][coor.getY()] = 5;
                        break;
                    case 3:
                        Log.d("MYLOG","МАТ черным!!!");
                        break;
                    case 4:
                        Log.d("MYLOG","ПАТ!!!");
                        break;
                }
            }
            step++;
            moveList = null;
        }
        cancelSelected();
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
    int[][] getField(){ return field; }
    ArrayList<figures> getBlack(){ return black; }
    ArrayList<figures> getWhite(){ return white; }
    ArrayList<Coordinate> getMoveList(){ return moveList; }
    int getStep(){ return step; }
    Coordinate getSelected(){return selected;}
}
