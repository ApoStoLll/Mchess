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