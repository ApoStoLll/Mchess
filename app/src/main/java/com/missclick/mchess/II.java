package com.missclick.mchess;

import android.util.Log;

import java.util.ArrayList;

public class II {
    //private int[][] field;
    private ArrayList<figures> white;
    private ArrayList<figures> black;
    private Controller controller;
    private Step bestStep;
    II(Controller controller){
        //this.field = field;
        //this.white = controller.getWhite();
        //this.black = controller.getBlack();
        this.controller = controller;
    }

    ArrayList<Step> getMoves(ArrayList<figures> enemy, ArrayList<figures> allies){
        ArrayList<Step> moves = new ArrayList<>();
        for(figures figure : allies){
            ArrayList<Coordinate> moveList = figure.check(controller.getField(), enemy, allies);
            if(moveList != null && !moveList.isEmpty())
                for(int i = 0; i < moveList.size(); i++)
                    moves.add(new Step(moveList.get(i), figure));
        }
        return moves;
    }

     static int[][] arrCopy(int[][] arr){
        int[][] copy = new int[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++)
                copy[i][j] = arr[i][j];
        }
        return copy;
    }

    /*int makeMove(Step step){//, ArrayList<figures> allies, ArrayList<figures> enemy){
        //int[][] copy = arrCopy(field);
        //step.getFigure().move(step.getTo(), field, enemy, allies);
        controller.move(step);
        return 0;
    }*/

    private int evaluateBoard(ArrayList<figures> allies){
        int value = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++) {
                if(allies.get(0).getColor() == 0 && controller.getField()[i][j] < 0) value += controller.getField()[i][j];
                if(allies.get(0).getColor() == 1 && controller.getField()[i][j] > 0) value += controller.getField()[i][j];
            }
        }
        //Log.d("EVALUATE", "Value : " + value);
        return value;
    }



    int minimax(int depth, ArrayList<figures> allies){
        if(depth == 0)
            return evaluateBoard(controller.getBlack()) + evaluateBoard(controller.getWhite());
        if(allies.get(0).getColor() == 0){
            //black
            ArrayList<Step> moves = getMoves(controller.getWhite(), controller.getBlack());
            int bestMove = 999;
            for(Step move : moves){
                controller.selectFigure(move.getFigure());
                controller.move(move);
                //moveFigure(field, move);
                int boardValue = minimax(depth - 1, controller.getWhite());
                controller.revert();
                if(bestMove > boardValue){
                    bestMove = boardValue;
                    //Log.d("Calculate", "bestValue: " + bestMove);
                    bestStep = move;
                }
            }
            return bestMove;
        } else{ //white
            int bestMove = -999;
            ArrayList<Step> moves = getMoves(controller.getBlack(), controller.getWhite());
            for(Step move : moves){
                controller.selectFigure(move.getFigure());
                controller.move(move);
                //moveFigure(field, move);
                int boardValue = minimax(depth - 1, controller.getBlack());
                Log.d("Minimax", "Revert white");
                controller.revert();
                if(bestMove < boardValue){
                    bestMove = boardValue;
                   // Log.d("Calculate", "bestValue: " + bestMove);
                    //bestStep = move;
                }
            }
            return bestMove;
        }
    }

    void moveFigure(int[][] field, Step step){
        ArrayList<Coordinate> moveList = new ArrayList<>();
        figures figure = step.getFigure();
        /*if(figure.getColor() == 1) moveList = figure.check(field,black,white);
        else moveList = figure.check(field,white,black);*/
        field[step.getTo().getX()][step.getTo().getY()] = field[step.getFrom().getX()][step.getFrom().getY()];
        field[step.getFrom().getX()][step.getFrom().getY()] = 0;
        if(figure.getColor() == 1) figure.move(step.getTo(), field, black, white);
        else figure.move(step.getTo(), field, white, black);

    }

    void move(){
        //int[][] fieldCopy = II.arrCopy(controller.getField());

        minimax(3, controller.getBlack());
        if(bestStep != null){
            Log.d("Calculate", "        MOVING     ");
            controller.selectFigure(bestStep.getFigure());
            controller.move(bestStep);
            //controller.num++;
        }
        else Log.d("II", "best = null");
    }

    Step randomStep(){
        figures figure = randFigure();
        ArrayList<Coordinate> moveList = figure.check(controller.getField(), controller.getWhite(), controller.getBlack());
        int index = (int) (Math.random() * moveList.size());
        /*Log.d("logII", "moving from X:" + figure.getCoor().getX() + " Y: " + figure.getCoor().getY() +
                "to X: " + moveList.get(index).getX() + " Y: " + moveList.get(index).getY());
        controller.selectFigure(figure);
        controller.move(new Step(moveList.get(index), figure), false);*/
        return new Step(moveList.get(index), figure);
    }

    figures randFigure(){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < controller.getBlack().size(); i++){
            int index = (int) ( Math.random() * (controller.getBlack().size()));
            arr.add(index);
            figures figure = controller.getBlack().get(index);
            if(figure.check(controller.getField(), controller.getWhite(), controller.getBlack()) != null
                    && !figure.check(controller.getField(), controller.getWhite(), controller.getBlack()).isEmpty()) return figure;
            else index = (int) ( Math.random() * (controller.getBlack().size()));
        }
        Log.d("logII", "null");
        return null;
    }
}
