package com.missclick.mchess;

import java.util.ArrayList;

abstract class figures {
    int color;
    Coordinate coor;
    void move(int x, int y){
        //ABSTRACT
    }
    ArrayList check(int field[][]){
        //ABSTRACT
        return null;
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

    ArrayList check(int field[][]){
        ArrayList movelist = new ArrayList();
        int x = this.coor.getX();
        int y = this.coor.getY();
        if(this.color == 0){
            if(this.coor.getY() == 1) if(field[x][y+2] == 0) movelist.add(new Coordinate(x,y+2));
            if(field[x][y+1] == 0) movelist.add(new Coordinate(x,y+1));
            if(x-1>=0) if(field[x-1][y+1] > 0) movelist.add(new Coordinate(x-1,y+1));
            if(x+1<8) if(field[x+1][y+1] > 0) movelist.add(new Coordinate(x+1,y+1));
        }
        else{
            if(this.coor.getY() == 6) if(field[x][y-2] == 0) movelist.add(new Coordinate(x,y-2));
            if(field[x][y-1] == 0) movelist.add(new Coordinate(x,y-1));
            if(x-1>=0) if(field[x-1][y-1] < 0) movelist.add(new Coordinate(x-1,y-1));
            if(x+1<8) if(field[x+1][y-1] < 0) movelist.add(new Coordinate(x+1,y-1));
        }
        return movelist;
    }
}

class Rook extends figures{
    Rook(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    void move(int x, int y){

    }
}

class Bishop extends figures{
    Bishop(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    void move(int x, int y){

    }
}

class Knight extends figures{
    Knight(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    void move(int x, int y){

    }
}

class Queen extends figures{
    Queen(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    void move(int x, int y){

    }
}

class King extends figures{
    King(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    void move(int x, int y){

    }
}