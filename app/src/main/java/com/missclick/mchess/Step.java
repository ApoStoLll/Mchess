package com.missclick.mchess;

public class Step {
    private figures figure;
    private Coordinate to;
    private int[][] currentField;
    Step(Coordinate to, figures figure, int[][] currentField){
        this.to = to;
        this.figure = figure;
        this.currentField = currentField;
    }

    public figures getFigure() {
        return figure;
    }

    public void setFigure(figures figure) {
        this.figure = figure;
    }

    public Coordinate getTo() {
        return to;
    }

    public void setTo(Coordinate to) {
        this.to = to;
    }

    public int[][] getCurrentField() {
        return currentField;
    }

    public void setCurrentField(int[][] currentField) {
        this.currentField = currentField;
    }
}
