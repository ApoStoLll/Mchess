package com.missclick.mchess;

public class Step {
    figures figure;
    Coordinate to;
    int[][] currentField;
    Step(Coordinate to, figures figure, int[][] currentField){
        this.to = to;
        this.figure = figure;
        this.currentField = currentField;
    }
}
