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
            if(this.firstStep && field[x][y+2] == 0) movelist.add(new Coordinate(x,y+2));
            if(field[x][y+1] == 0) movelist.add(new Coordinate(x,y+1));
            if(x-1>=0) if(field[x-1][y+1] > 0) movelist.add(new Coordinate(x-1,y+1));
            if(x+1<8) if(field[x+1][y+1] > 0) movelist.add(new Coordinate(x+1,y+1));
        }
        else{
            if(this.firstStep && field[x][y-2] == 0) movelist.add(new Coordinate(x,y-2));
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
            for(int i=0;i<2;i++) for(int j=0;j<2;j++) if(x-1+2*i<8 && x-1+2*i>=0 && y-2+4*i<8 && y-2+4*i>=0)
                if(field[x-1+2*i][y-2+4*i]>=0) movelist.add(new Coordinate(x-1+2*i,y-2+4*i));
            for(int i=0;i<2;i++) for(int j=0;j<2;j++) if(x-2+4*i<8 && x-2+4*i>=0 && y-1+2*i<8 && y-1+2*i>=0)
                if(field[x-2+4*i][y-1+2*i]>=0) movelist.add(new Coordinate(x-2+4*i,y-1+2*i));
        }
        else{
            for(int i=0;i<2;i++) for(int j=0;j<2;j++) if(x-1+2*i<8 && x-1+2*i>=0 && y-2+4*i<8 && y-2+4*i>=0)
                if(field[x-1+2*i][y-2+4*i]<=0) movelist.add(new Coordinate(x-1+2*i,y-2+4*i));
            for(int i=0;i<2;i++) for(int j=0;j<2;j++) if(x-2+4*i<8 && x-2+4*i>=0 && y-1+2*i<8 && y-1+2*i>=0)
                if(field[x-2+4*i][y-1+2*i]<=0) movelist.add(new Coordinate(x-2+4*i,y-1+2*i));
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
    int move(int x, int y,ArrayList<figures> allies){
        if(x == 6 && y == this.coor.getY()) findRook(allies,false,y).move(5,y) ;
        if(x == 2 && y == this.coor.getY()) findRook(allies,true,y).move(3,y) ;
        this.coor = new Coordinate(x,y);
        this.firstStep = false;
        return 0;
    }
    ArrayList check(int field[][],ArrayList<figures> enemy,ArrayList<figures> allies){
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

        if(this.firstStep && findRook(allies,false,this.coor.getY()).firstStep)
            for(int i=1;i<4;i++){                       // long rokirovka
            if(field[x-i][y] != 0) break;
            for(Coordinate coords : movelistEnemy)
                if(new Coordinate(x,y)==coords || new Coordinate(x-2,y)==coords) break;
            if(i==3) movelist.add(new Coordinate(x-i,y));
        }
        if(this.firstStep && findRook(allies,true,this.coor.getY()).firstStep)
            for(int i=1;i<3;i++){                       // short rokirovka
            if(field[x-i][y] != 0) break;
            for(Coordinate coords : movelistEnemy)
                if(new Coordinate(x,y)==coords || new Coordinate(x+2,y)==coords) break;
            if(i==2) movelist.add(new Coordinate(x+i,y));
        }

        return movelist;

    }
    figures findRook(ArrayList<figures> allies,boolean tiny,int y){
        for(figures figure : allies){
            if(tiny) if(figure.getCoor().getX() == 7)
                if(figure.getCoor().getY() == y)
                    if(figure.firstStep) return figure;
                    else if(figure.getCoor().getX() == 0)
                        if(figure.getCoor().getY() == y)
                            if(figure.firstStep) return figure;

        }
        return null;
    }
}