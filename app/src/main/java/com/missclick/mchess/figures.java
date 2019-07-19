package com.missclick.mchess;

abstract class figures {
    int color;
    Coordinate coor;
    void move(int x, int y){
        //ABSTRACT
    }
}

class Pawn extends figures{
    @Override
    void move(int x, int y){

    }
}
