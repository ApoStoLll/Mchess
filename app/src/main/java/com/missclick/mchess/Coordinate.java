package com.missclick.mchess;
//1 - PAWN, 2 - ROOK, 3 - KNIGHT, 4 - BISHOP, 5 - QUEEN, 6 - KING

public class Coordinate {
    private int x,y;
    Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    int getX(){ return x; }
    int getY(){ return y; }
}
