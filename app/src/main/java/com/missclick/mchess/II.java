package com.missclick.mchess;

import android.util.Log;

import java.util.ArrayList;

public class II {
    int[][] field;
    ArrayList<figures> white;
    ArrayList<figures> black;
    Controller controller;
    II(int[][] field, Controller controller){
        this.field = field;
        this.white = controller.getWhite();
        this.black = controller.getBlack();
        this.controller = controller;
    }

    void calculateBest(){

    }

    void move(){
        randomStep();
    }

    void randomStep(){
        ArrayList<figures> blackCopy = new ArrayList<figures>(black);
        figures figure = randFigure(blackCopy);
        ArrayList<Coordinate> moveList = figure.check(field, white, black);
        int index = (int) (Math.random() * moveList.size());
        Log.d("logII", "moving from X:" + figure.getCoor().getX() + " Y: " + figure.getCoor().getY() +
                "to X: " + moveList.get(index).getX() + " Y: " + moveList.get(index).getY());
        controller.selectFigure(figure);
        controller.move(moveList.get(index), figure);
    }

    figures randFigure(ArrayList<figures> list){
        int index = (int) ( Math.random() * (list.size()));
        figures figure = black.get(index);
        if(figure.check(field, white, black) != null && !figure.check(field, white, black).isEmpty()) return figure;
        else{
            list.remove(figure);
            randFigure(list);
        }
        Log.d("logII", "null");
        return null;
    }
}
