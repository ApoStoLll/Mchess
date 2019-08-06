package com.missclick.mchess;

public class Step {
    private figures figure;
    private Coordinate to;

    public Coordinate getFrom() {
        return from;
    }

    private Coordinate from;
    private Boolean firstStep = false;
    private int[][] currentField;

    private boolean hit = false;
    Step(Coordinate to, figures figure){
        this.to = to;
        this.figure = figure;
        from = new Coordinate(figure.getCoor().getX(), figure.getCoor().getY());
        firstStep = figure.getFirstStep();
        //this.currentField = currentField;
    }

    public figures getFigure() {
        return figure;
    }

    public boolean getFirstStep() {
        return firstStep;
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
