package com.missclick.mchess;

import android.util.Log;

import java.util.ArrayList;

public class II {
    //private int[][] field;
    //private ArrayList<figures> white;
    //private ArrayList<figures> black;
    private Controller controller;
    private Step bestStep;
    II(Controller controller){
        //this.field = field;
        //this.white = controller.getWhite();
        //this.black = controller.getBlack();
        this.controller = controller;
    }

    ArrayList<Step> getMoves(ArrayList<figures> black){
        ArrayList<Step> moves = new ArrayList<>();
        for(figures figure : black){
            ArrayList<Coordinate> moveList = figure.check(controller.getField(), controller.getWhite(), black);
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

    /*int alphaBeta(int alpha, int beta, int depth, ArrayList<figures> allies, ArrayList<figures> enemy){
        int value;
        ArrayList<Step> moves = getMoves(allies);
        for(Step move : moves){
            makeMove(move);
            if(depth > 1)
                value = alphaBeta(alpha, beta, depth - 1, enemy, allies);
            else{
                if(move.isHit()) value = evaluateBoard(field, allies) + evaluateBoard(field, enemy);
                else{
                    bestStep = randomStep();
                    controller.revert();
                    continue;
                }
            }
            //value = evaluateBoard(field, allies) + evaluateBoard(field, enemy);
            Log.d("II", "value = " + value);
            controller.revert();
            if(value > alpha){
                if(value >= beta) {
                    bestStep = move;
                    Log.d("II", "BETA");
                    return beta;
                }
                if(bestStep == null) bestStep = move;
                Log.d("II", "al = val");
                alpha = value;
            }
        }
        //proverka na shag
        Log.d("II", "ALPHA");
        return alpha;
    }*/

    /*void calculateBest(){
        //alphaBeta(-30, 30, 4, black, white);
        int bestValue = 0;
        bestStep = randomStep();
        ArrayList<Step> moves = getMoves(controller.getBlack());
        for(Step move : moves){
            controller.selectFigure(move.getFigure());
            controller.move(move);
            int boardValue = evaluateBoard(controller.getBlack()) + evaluateBoard(controller.getWhite());
            Log.d("Calculate", "boardValue: " + boardValue);
            controller.revert();
            if(boardValue < bestValue){
                bestValue = boardValue;
                Log.d("Calculate", "bestValue: " + bestValue);
                bestStep = move;
            }
        }
    }*/

    int minimax(int depth, ArrayList<figures> allies){
        if(depth == 0)
            return evaluateBoard(controller.getBlack()) + evaluateBoard(controller.getWhite());
        if(allies.get(0).getColor() == 0){
            //black
            ArrayList<Step> moves = getMoves(controller.getBlack());
            int bestMove = -999;
            for(Step move : moves){
                controller.selectFigure(move.getFigure());
                controller.move(move);
                bestMove = Math.max(bestMove, minimax(depth - 1, controller.getWhite()));
                controller.revert();
            }
            return bestMove;
        }
        else{
            int bestMove = -999;
            ArrayList<Step> moves = getMoves(controller.getWhite());
            for(Step move : moves){
                controller.selectFigure(move.getFigure());
                controller.move(move);
                bestMove = Math.min(bestMove, minimax(depth - 1, controller.getBlack()));
                controller.revert();
            }
            return bestMove;
        }
    }

    void move(){
        minimax(3, controller.getBlack());
        controller.selectFigure(bestStep.getFigure());
        controller.move(bestStep);
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
