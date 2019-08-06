package com.missclick.mchess;

public class Step {
    private figures figure;
    private Coordinate to;
    private int[][] currentField;

    private boolean hit = false;
    Step(Coordinate to, figures figure){
        this.to = to;
        this.figure = figure;
        //this.currentField = currentField;
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

    public void setHit(boolean hit) { this.hit = hit;}

    public void setTo(Coordinate to) {
        this.to = to;
    }

    public int[][] getCurrentField() {
        return currentField;
    }

    public void setCurrentField(int[][] currentField) {
        this.currentField = currentField;
    }

    public boolean isHit() {
        return hit;
    }
}
