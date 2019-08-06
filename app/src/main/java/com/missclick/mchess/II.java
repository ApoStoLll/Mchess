package com.missclick.mchess;

import android.util.Log;

import java.util.ArrayList;

public class II {
    private int[][] field;
    private ArrayList<figures> white;
    private ArrayList<figures> black;
    private Controller controller;
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
                    moves.add(new Step(moveList.get(i), figure, field));
        }
        return moves;
    }

    int evaluate(Step step){

        return 0;
    }

    void calculateBest(){

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
        controller.move(moveList.get(index), figure);
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
