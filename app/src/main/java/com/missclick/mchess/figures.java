package com.missclick.mchess;

abstract class figures {
    int color;
    Coordinate coor;
    void move(int x, int y){
        //ABSTRACT
    }
    Coordinate getCoor(){ return coor; }
}

class Pawn extends figures{
    Pawn(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    void move(int x, int y){

    }
}

class Rook extends figures{
    @Override
    void move(int x, int y){

    }
}

class Bishop extends figures{
    @Override
    void move(int x, int y){

    }
}

class Knight extends figures{
    @Override
    void move(int x, int y){

    }
}

class Queen extends figures{
    @Override
    void move(int x, int y){

    }
}

class King extends figures{
    @Override
    void move(int x, int y){

    }
}