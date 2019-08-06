package com.missclick.mchess;

import android.util.Log;

import java.util.ArrayList;

public class II {
    private int[][] field;
    private ArrayList<figures> white;
    private ArrayList<figures> black;
    private Controller controller;
    private Step bestStep;
    II(int[][] field, Controller controller){
        this.field = field;
        this.white = controller.getWhite();
        this.black = controller.getBlack();
        this.controller = controller;
    }

    ArrayList<Step> getMoves(ArrayList<figures> black){
        ArrayList<Step> moves = new ArrayList<>();
        for(figures figure : black){
            ArrayList<Coordinate> moveList = figure.check(field, white, black);
            if(moveList != null && !moveList.isEmpty())
                for(int i = 0; i < moveList.size(); i++)
                    moves.add(new Step(moveList.get(i), figure));
        }
        return moves;
    }

    public static int[][] arrCopy(int[][] arr){
        int[][] copy = new int[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++)
                copy[i][j] = arr[i][j];
        }
        return copy;
    }

    int makeMove(Step step){//, ArrayList<figures> allies, ArrayList<figures> enemy){
        int[][] copy = arrCopy(field);
        //step.getFigure().move(step.getTo(), field, enemy, allies);
        controller.move(step, false);
        return 0;
    }

    int evaluateBoard(int[][] field, ArrayList<figures> allies){
        int value = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++) {
                if(allies.get(0).getColor() == 0 && field[i][j] < 0) value += field[i][j];
                if(allies.get(0).getColor() == 1 && field[i][j] > 0) value += field[i][j];
            }
        }
        return value;
    }

    int alphaBeta(int alpha, int beta, int depth, ArrayList<figures> allies, ArrayList<figures> enemy){
        int value;
        ArrayList<Step> moves = getMoves(allies);
        for(Step move : moves){
            makeMove(move);
            if(depth > 1)
                value = alphaBeta(alpha, beta, depth - 1, enemy, allies);
            else
                value = evaluateBoard(field, allies) - evaluateBoard(field, enemy);
            controller.revert();
            if(value > alpha){
                if(value >= beta) {
                    bestStep = move;
                    return beta;
                }
                if(bestStep == null) bestStep = move;
                alpha = value;
            }
        }
        //proverka na shag
        return alpha;
    }

    void calculateBest(){
        alphaBeta(-5, 5, 30, black, white);
        controller.selectFigure(bestStep.getFigure());
        controller.move(bestStep, true);
    }

    void move(){
        randomStep();
    }

    void randomStep(){
        figures figure = randFigure();
        ArrayList<Coordinate> moveList = figure.check(field, white, black);
        int index = (int) (Math.random() * moveList.size());
        Log.d("logII", "moving from X:" + figure.getCoor().getX() + " Y: " + figure.getCoor().getY() +
                "to X: " + moveList.get(index).getX() + " Y: " + moveList.get(index).getY());
        controller.selectFigure(figure);
        controller.move(new Step(moveList.get(index), figure), false);
    }

    figures randFigure(){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < black.size(); i++){
            int index = (int) ( Math.random() * (black.size()));
            arr.add(index);
            figures figure = black.get(index);
            if(figure.check(field, white, black) != null && !figure.check(field, white, black).isEmpty()) return figure;
            else index = (int) ( Math.random() * (black.size()));
        }
        Log.d("logII", "null");
        return null;
    }
}
