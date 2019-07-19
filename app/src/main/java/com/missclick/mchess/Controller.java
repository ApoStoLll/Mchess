package com.missclick.mchess;

import android.util.Log;

import java.util.ArrayList;

class Controller {
    private int[][] field;
    private ArrayList<figures> black;
    private ArrayList<figures> white;
    Controller(){
        black = new ArrayList<>();
        white = new ArrayList<>();
        field = new int[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                field[i][j] = 0;
            }
        }
        createFigures();
    }
    private void createFigures(){
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
        white.add(new Queen(0, new Coordinate(4, 7)));
        field[4][7] = -5;
        white.add(new King(1, new Coordinate(3, 7)));
        field[3][7] = 6;
        black.add(new King(0, new Coordinate(4, 0)));
        field[4][0] = -6;
        for(figures figure : black){
            Log.d("MYLOG", "BLACK X: " + figure.coor.getX());
            Log.d("MYLOG", "BLACK Y: " + figure.coor.getY());
        }
        for(figures figure : white){
            Log.d("MYLOG", "WHITE X: " + figure.coor.getX());
            Log.d("MYLOG", "WHITE Y: " + figure.coor.getY());
        }
    }
    int[][] getField(){ return field; }
    ArrayList<figures> getBlack(){ return black; }
    ArrayList<figures> getWhite(){ return white; }
}
