package com.missclick.mchess;

import java.util.ArrayList;

abstract class figures {
    int color;
    boolean firstStep = true;
    Coordinate coor;
    int move(int x, int y){
       this.coor = new Coordinate(x,y);
       this.firstStep = false;
       return 0;
    }
    ArrayList check(int field[][]){
        //ABSTRACT
        return null;
    }
    Coordinate getCoor(){ return coor; }
    ArrayList checkRook(int field[][],int x,int y,ArrayList movelist){
        for(int i=1;i<8;i++){
            if(x+i<8){
                if (field[x+i][y] < 0 && this.color == 0) break;
                if (field[x+i][y] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x + i, y));
                if (field[x+i][y] != 0) break;
            }
            else break;
        }
        for(int i=1;i<8;i++){
            if(x-i>=0){
                if (field[x-i][y] < 0 && this.color == 0) break;
                if (field[x-i][y] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x - i, y));
                if (field[x-i][y] != 0) break;
            }
            else break;
        }
        for(int i=1;i<8;i++){
            if(y+i<8){
                if (field[x][y+i] < 0 && this.color == 0) break;
                if (field[x+i][y+i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x, y+i));
                if (field[x][y+i] != 0) break;
            }
            else break;
        }
        for(int i=1;i<8;i++){
            if(y-i>=0){
                if (field[x][y-i] < 0 && this.color == 0) break;
                if (field[x+i][y-i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x, y-i));
                if (field[x][y-i] != 0) break;
            }
            else break;
        }
        return movelist;
    }
    ArrayList checkBishop(int field[][],int x,int y,ArrayList movelist){
        for(int i=1;i<8;i++){
            if(x+i<8 && y+i<8){
                if (field[x+i][y+i] < 0 && this.color == 0) break;
                if (field[x+i][y+i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x + i, y + i));
                if (field[x+i][y+i] != 0) break;
            }
            else break;
        }
        for(int i=1;i<8;i++){
            if(x+i<8 && y-i>=0){
                if (field[x+i][y-i] < 0 && this.color == 0) break;
                if (field[x+i][y-i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x + i, y - i));
                if (field[x+i][y-i] != 0) break;
            }
            else break;
        }
        for(int i=1;i<8;i++){
            if(x-i<8 && y+i<8){
                if (field[x-i][y+i] < 0 && this.color == 0) break;
                if (field[x-i][y+i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x - i, y + i));
                if (field[x-i][y+i] != 0) break;
            }
            else break;
        }
        for(int i=1;i<8;i++){
            if(x+i<8 && y+i<8){
                if (field[x-i][y-i] < 0 && this.color == 0) break;
                if (field[x-i][y-i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x - i, y - i));
                if (field[x-i][y-i] != 0) break;
            }
            else break;
        }
        return movelist;
    }
}

class Pawn extends figures{
    Pawn(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    int move(int x, int y){
        this.coor = new Coordinate(x,y);
        this.firstStep = false;
        if(y == 0 || y == 7) return 1;
        else return 0;
    }
    @Override
    ArrayList check(int field[][]){
        ArrayList movelist = new ArrayList();
        int x = this.coor.getX();
        int y = this.coor.getY();
        if(this.color == 0){
            if(y == 1) if(field[x][y+2] == 0) movelist.add(new Coordinate(x,y+2));
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
    ArrayList check(int field[][]){
        ArrayList movelist = new ArrayList();
        int x = this.coor.getX();
        int y = this.coor.getY();
        return checkRook(field,x,y,movelist);
    }
}

class Bishop extends figures{
    Bishop(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    ArrayList check(int field[][]){
        ArrayList movelist = new ArrayList();
        int x = this.coor.getX();
        int y = this.coor.getY();
        return checkBishop(field,x,y,movelist);
    }
}

class Knight extends figures{
    Knight(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    ArrayList check(int field[][]){
        ArrayList movelist = new ArrayList();
        int x = this.coor.getX();
        int y = this.coor.getY();
        if(this.color == 0){
            if(x+1<8 && y+2<8) if(field[x+1][y+2]>=0) movelist.add(new Coordinate(x+1,y+2));
            if(x-1>=0 && y+2<8) if(field[x-1][y+2]>=0) movelist.add(new Coordinate(x-1,y+2));
            if(x+2<8 && y+1<8) if(field[x+2][y+1]>=0) movelist.add(new Coordinate(x+2,y+1));
            if(x-2>=0 && y+1<8) if(field[x+2][y+1]>=0) movelist.add(new Coordinate(x-2,y+1));
            if(x+1<8 && y-2>=0) if(field[x+1][y+2]>=0) movelist.add(new Coordinate(x+1,y-2));
            if(x-1>=0 && y-2>=0) if(field[x-1][y+2]>=0) movelist.add(new Coordinate(x-1,y-2));
            if(x+2<8 && y-1>=0) if(field[x+2][y+1]>=0) movelist.add(new Coordinate(x+2,y-1));
            if(x-2>=0 && y-1>=0) if(field[x+2][y+1]>=0) movelist.add(new Coordinate(x-2,y-1));

        }
        else{
            if(x+1<8 && y+2<8) if(field[x+1][y+2]<=0) movelist.add(new Coordinate(x+1,y+2));
            if(x-1>=0 && y+2<8) if(field[x-1][y+2]<=0) movelist.add(new Coordinate(x-1,y+2));
            if(x+2<8 && y+1<8) if(field[x+2][y+1]<=0) movelist.add(new Coordinate(x+2,y+1));
            if(x-2>=0 && y+1<8) if(field[x+2][y+1]<=0) movelist.add(new Coordinate(x-2,y+1));
            if(x+1<8 && y-2>=0) if(field[x+1][y+2]<=0) movelist.add(new Coordinate(x+1,y-2));
            if(x-1>=0 && y-2>=0) if(field[x-1][y+2]<=0) movelist.add(new Coordinate(x-1,y-2));
            if(x+2<8 && y-1>=0) if(field[x+2][y+1]<=0) movelist.add(new Coordinate(x+2,y-1));
            if(x-2>=0 && y-1>=0) if(field[x+2][y+1]<=0) movelist.add(new Coordinate(x-2,y-1));
        }
        return movelist;
    }
}

class Queen extends figures{
    Queen(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    ArrayList check(int field[][]){
        ArrayList movelist = new ArrayList();
        int x = this.coor.getX();
        int y = this.coor.getY();
        movelist = checkRook(field,x,y,movelist);
        return checkBishop(field,x,y,movelist);
    }
}

class King extends figures{
    King(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    ArrayList check(int field[][],ArrayList<figures> enemy){
        ArrayList<Coordinate> movelist = new ArrayList();
        ArrayList<Coordinate> movelistEnemy = new ArrayList();
        int x = this.coor.getX();
        int y = this.coor.getY();
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;i++) if(x+i<8 && y+i<8 && x+i>=0 && y+i>=0){
                if(i==0 && j==0) continue;
                if(field[x+i][y+i]>=0 && this.color == 0) movelist.add(new Coordinate(x+i,y+i));
                if(field[x+i][y+i]<=0 && this.color == 1) movelist.add(new Coordinate(x+i,y+i));
            }
        }
        for(figures figure : enemy) movelistEnemy.addAll(figure.check(field));
        for(Coordinate coord : movelist) for(Coordinate coords : movelistEnemy){
            if(coord == coords) movelist.remove(coord);
        }
        return movelist;
    }
}