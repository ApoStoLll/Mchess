package com.missclick.mchess;

import java.util.ArrayList;
import android.util.Log;

abstract class figures {
    int color;
    boolean firstStep = true;
    boolean isSelected = false;
    boolean shag = false;
    Coordinate coor;

    int move(Coordinate coor, int[][] field, ArrayList<figures> enemy, ArrayList<figures> allies) {
        this.coor = coor;
        this.firstStep = false;
        for (Coordinate coord : hit(field, enemy, allies)) {
            if (enemy.get(0).coor.getX() == coord.getX() && enemy.get(0).coor.getY() == coord.getY()) {
                enemy.get(0).shag = true;
                return 1;
            }
        }
        return 0;
    }

    ArrayList<Coordinate> check(int[][] field, ArrayList<figures> enemy, ArrayList<figures> allies) {
        //ABSTRACT
        return null;
    }

    ArrayList<Coordinate> hit(int[][] field, ArrayList<figures> enemy, ArrayList<figures> allies) {
        return null;
    }

    Coordinate getCoor() {
        return coor;
    }

    int getColor() {
        return color;
    }

    void setSelected(boolean select) {
        isSelected = select;
    }



    ArrayList<Coordinate> checkRook(int[][] field, int x, int y, ArrayList<Coordinate> movelist,
                                    ArrayList<figures> enemy, ArrayList<figures> allies) {
        for (int i = 1; i < 8; i++) {
            if (x + i < 8) {
                if (field[x + i][y] < 0 && this.color == 0) break;
                if (field[x + i][y] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x + i, y));
                if (field[x + i][y] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0) {
                if (field[x - i][y] < 0 && this.color == 0) break;
                if (field[x - i][y] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x - i, y));
                if (field[x - i][y] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (y + i < 8) {
                if (field[x][y + i] < 0 && this.color == 0) break;
                if (field[x][y + i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x, y + i));
                if (field[x][y + i] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (y - i >= 0) {
                if (field[x][y - i] < 0 && this.color == 0) break;
                if (field[x][y - i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x, y - i));
                if (field[x][y - i] != 0) break;
            } else break;
        }
        return movelist;/*
        if (!allies.get(0).shag) return movelist;
        else {
            ArrayList<Coordinate> movelistOverall = new ArrayList<>();
            for (Coordinate coord : movelist) {
                int old = field[coord.getX()][coord.getY()];
                field[coord.getX()][coord.getY()] = field[this.coor.getX()][this.coor.getY()];
                for (figures figur : enemy)
                    for (Coordinate coords : figur.hit(field, enemy, allies)) {
                        if (coords.getX() == allies.get(0).getCoor().getX() &&
                                coords.getY() == allies.get(0).getCoor().getY())
                            movelistOverall.add(coord);
                    }
                field[coord.getX()][coord.getY()] = old;
            }
            movelist.removeAll(movelistOverall);
            return movelist;
        }*/
    }

    ArrayList<Coordinate> checkShag(ArrayList<Coordinate> movelist, int[][] field, ArrayList<figures> enemy, ArrayList<figures> allies){
        ArrayList<Coordinate> movelistOverall = new ArrayList<>();
        int[][] fieldCopy = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fieldCopy[i][j] = field[i][j];
            }
        }
        for (Coordinate coord : movelist) {
            int old = field[coord.getX()][coord.getY()];
            fieldCopy[coord.getX()][coord.getY()] = field[this.coor.getX()][this.coor.getY()];
            Log.d("MYLOG", "checkimg");
            for (figures figur : enemy)
                for (Coordinate coords : figur.hit(fieldCopy, enemy, allies)) {
                    Log.d("MYLOG", "working before if");
                    if (coords.getX() == allies.get(0).getCoor().getX() &&
                            coords.getY() == allies.get(0).getCoor().getY())
                        Log.d("MYLOG", "working.... (if)");
                    movelistOverall.add(coord);
                }
            fieldCopy[coord.getX()][coord.getY()] = old;
        }
        movelist.removeAll(movelistOverall);
        return movelist;
    }

    ArrayList<Coordinate> checkBishop(int[][] field, int x, int y, ArrayList<Coordinate> movelist,
                                      ArrayList<figures> enemy, ArrayList<figures> allies) {
        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && y + i < 8) {
                if (field[x + i][y + i] < 0 && this.color == 0) break;
                if (field[x + i][y + i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x + i, y + i));
                if (field[x + i][y + i] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && y - i >= 0) {
                if (field[x + i][y - i] < 0 && this.color == 0) break;
                if (field[x + i][y - i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x + i, y - i));
                if (field[x + i][y - i] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0 && y + i < 8) {
                if (field[x - i][y + i] < 0 && this.color == 0) break;
                if (field[x - i][y + i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x - i, y + i));
                if (field[x - i][y + i] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0 && y - i >= 0) {
                if (field[x - i][y - i] < 0 && this.color == 0) break;
                if (field[x - i][y - i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x - i, y - i));
                if (field[x - i][y - i] != 0) break;
            } else break;
        }
        //return movelist;
        if (!allies.get(0).shag) return movelist;
        else return checkShag(movelist, field, enemy, allies);
    }

    ArrayList<Coordinate> hitBishop(int[][] field, int x, int y, ArrayList<Coordinate> movelist,
                                    ArrayList<figures> enemy, ArrayList<figures> allies) {
        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && y + i < 8) {
                if (field[x + i][y + i] < 0 && this.color == 0) break;
                if (field[x + i][y + i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x + i, y + i));
                if (field[x + i][y + i] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && y - i >= 0) {
                if (field[x + i][y - i] < 0 && this.color == 0) break;
                if (field[x + i][y - i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x + i, y - i));
                if (field[x + i][y - i] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0 && y + i < 8) {
                if (field[x - i][y + i] < 0 && this.color == 0) break;
                if (field[x - i][y + i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x - i, y + i));
                if (field[x - i][y + i] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0 && y - i >= 0) {
                if (field[x - i][y - i] < 0 && this.color == 0) break;
                if (field[x - i][y - i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x - i, y - i));
                if (field[x - i][y - i] != 0) break;
            } else break;
        }
        return movelist;

    }

    ArrayList<Coordinate> hitRook(int[][] field, int x, int y, ArrayList<Coordinate> movelist,
                                  ArrayList<figures> enemy, ArrayList<figures> allies) {
        for (int i = 1; i < 8; i++) {
            if (x + i < 8) {
                if (field[x + i][y] < 0 && this.color == 0) break;
                if (field[x + i][y] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x + i, y));
                if (field[x + i][y] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0) {
                if (field[x - i][y] < 0 && this.color == 0) break;
                if (field[x - i][y] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x - i, y));
                if (field[x - i][y] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (y + i < 8) {
                if (field[x][y + i] < 0 && this.color == 0) break;
                if (field[x][y + i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x, y + i));
                if (field[x][y + i] != 0) break;
            } else break;
        }
        for (int i = 1; i < 8; i++) {
            if (y - i >= 0) {
                if (field[x][y - i] < 0 && this.color == 0) break;
                if (field[x][y - i] > 0 && this.color == 1) break;
                movelist.add(new Coordinate(x, y - i));
                if (field[x][y - i] != 0) break;
            } else break;
        }
        return movelist;
    }
}
class Pawn extends figures {
    Pawn(int color, Coordinate coor) {
        this.coor = coor;
        this.color = color;
    }

    @Override
    int move(Coordinate coor, int[][] field, ArrayList<figures> enemy, ArrayList<figures> allies) {
        this.coor = coor;
        this.firstStep = false;
        for (Coordinate coord : hit(field, enemy, allies)) {
            if (enemy.get(0).coor.getX() == coord.getX() && enemy.get(0).coor.getY() == coord.getY()) {
                enemy.get(0).shag = true;
                return 1;
            }
        }
        if (coor.getY() == 0 || coor.getY() == 7) return 2;
        return 0;
    }

    @Override
    ArrayList<Coordinate> hit(int[][] field, ArrayList<figures> enemy, ArrayList<figures> allies) {
        ArrayList<Coordinate> movelist = new ArrayList<>();
        int x = this.coor.getX();
        int y = this.coor.getY();
        if (x + 1 < 8) movelist.add(new Coordinate(x + 1,y + 1-2*this.color));
        if (x - 1 >= 0) movelist.add(new Coordinate(x - 1,y + 1-2*this.color));
        return movelist;
    }
    @Override
    ArrayList<Coordinate> check(int[][] field,ArrayList<figures> enemy,ArrayList<figures> allies){
        ArrayList<Coordinate> movelist = new ArrayList<>();
        int x = this.coor.getX();
        int y = this.coor.getY();
        if(field[x][y+1-2*this.color] == 0) {
            movelist.add(new Coordinate(x,y+1-2*this.color));
            if(this.firstStep && field[x][y+2-4*this.color] == 0)
                movelist.add(new Coordinate(x,y+2-4*this.color));
        }
        if(x-1>=0) if(field[x-1][y+1-2*this.color] > 0) movelist.add(new Coordinate(x-1,y+1-2*this.color));
        if(x+1<8) if(field[x+1][y+1-2*this.color] > 0) movelist.add(new Coordinate(x+1,y+1-2*this.color));
        if (!allies.get(0).shag) return movelist;
        else return checkShag(movelist, field, enemy, allies);
    }
}

class Rook extends figures{
    Rook(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    ArrayList<Coordinate> hit(int[][] field,ArrayList<figures> enemy,ArrayList<figures> allies){
        return check(field,enemy,allies);}
    @Override
    ArrayList<Coordinate> check(int[][] field,ArrayList<figures> enemy,ArrayList<figures> allies){
        ArrayList<Coordinate> movelist = new ArrayList<>();
        int x = this.coor.getX();
        int y = this.coor.getY();
        return hitRook(field,x,y,movelist,enemy,allies);
    }
}

class Bishop extends figures{
    Bishop(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    ArrayList<Coordinate> hit(int[][] field,ArrayList<figures> enemy,ArrayList<figures> allies){
        return check(field,enemy,allies);}
    @Override
    ArrayList<Coordinate> check(int[][] field,ArrayList<figures> enemy,ArrayList<figures> allies){
        ArrayList<Coordinate> movelist = new ArrayList<>();
        int x = this.coor.getX();
        int y = this.coor.getY();
        return hitBishop(field,x,y,movelist,enemy,allies);
    }
}

class Knight extends figures{
    Knight(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    ArrayList<Coordinate> hit(int[][] field,ArrayList<figures> enemy,ArrayList<figures> allies){
        return check(field,enemy,allies);}
    @Override
    ArrayList<Coordinate> check(int[][] field,ArrayList<figures> enemy,ArrayList<figures> allies){
        ArrayList<Coordinate> movelist = new ArrayList<>();
        int x = this.coor.getX();
        int y = this.coor.getY();
        if(this.color == 0){
            for(int i=0;i<2;i++) for(int j=0;j<2;j++)
                if(x-1+2*i<8 && x-1+2*i>=0 && y-2+4*j<8 && y-2+4*j>=0)
                if(field[x-1+2*i][y-2+4*j]>=0) movelist.add(new Coordinate(x-1+2*i,y-2+4*j));
            for(int i=0;i<2;i++) for(int j=0;j<2;j++)
                if(x-2+4*i<8 && x-2+4*i>=0 && y-1+2*j<8 && y-1+2*j>=0)
                if(field[x-2+4*i][y-1+2*j]>=0) movelist.add(new Coordinate(x-2+4*i,y-1+2*j));
        }
        else{
            for(int i=0;i<2;i++) for(int j=0;j<2;j++)
                if(x-1+2*i<8 && x-1+2*i>=0 && y-2+4*j<8 && y-2+4*j>=0)
                if(field[x-1+2*i][y-2+4*j]<=0) movelist.add(new Coordinate(x-1+2*i,y-2+4*j));
            for(int i=0;i<2;i++) for(int j=0;j<2;j++)
                if(x-2+4*i<8 && x-2+4*i>=0 && y-1+2*j<8 && y-1+2*j>=0)
                if(field[x-2+4*i][y-1+2*j]<=0) movelist.add(new Coordinate(x-2+4*i,y-1+2*j));
        }
        if (!allies.get(0).shag) return movelist;
        else {
            ArrayList<Coordinate> movelistOverall = new ArrayList<>();
            for (Coordinate coord : movelist) {
                int old = field[coord.getX()][coord.getY()];
                field[coord.getX()][coord.getY()] = field[this.coor.getX()][this.coor.getY()];
                for (figures figur : enemy)
                    for (Coordinate coords : figur.hit(field, enemy, allies)) {
                        if (coords.getX() == allies.get(0).getCoor().getX() &&
                                coords.getY() == allies.get(0).getCoor().getY())
                            movelistOverall.add(coord);
                    }
                field[coord.getX()][coord.getY()] = old;
            }
            movelist.removeAll(movelistOverall);
            return movelist;
        }
    }
}

class Queen extends figures{
    Queen(int color, Coordinate coor){
        this.coor = coor;
        this.color = color;
    }
    @Override
    ArrayList<Coordinate> hit(int[][] field,ArrayList<figures> enemy,ArrayList<figures> allies){
        ArrayList<Coordinate> movelist = new ArrayList<>();
        int x = this.coor.getX();
        int y = this.coor.getY();
        movelist = hitBishop(field,x,y,movelist,enemy,allies);
        return hitRook(field,x,y,movelist,enemy,allies);
    }
    @Override
    ArrayList<Coordinate> check(int[][] field,ArrayList<figures> enemy,ArrayList<figures> allies){
        ArrayList<Coordinate> movelist = new ArrayList<>();
        int x = this.coor.getX();
        int y = this.coor.getY();
        movelist = checkBishop(field,x,y,movelist,enemy,allies);
        return checkRook(field,x,y,movelist,enemy,allies);
    }
}

class King extends figures {
    King(int color, Coordinate coor) {
        this.coor = coor;
        this.color = color;
    }

    @Override
    int move(Coordinate coor, int[][] field, ArrayList<figures> enemy, ArrayList<figures> allies) {
        if (coor.getX() == this.coor.getX() - 2) {
            findRook(allies, false, coor.getY()).move(new Coordinate(3, coor.getY()), field, enemy, allies);
            field[0][coor.getY()] = 0;
            field[3][coor.getY()] = -2 + 4 * this.color;
        }
        if (coor.getX() == this.coor.getX() + 2) {
            findRook(allies, true, coor.getY()).move(new Coordinate(5, coor.getY()), field, enemy, allies);
            field[7][coor.getY()] = 0;
            field[5][coor.getY()] = -2 + 4 * this.color;
        }
        this.coor = coor;
        this.firstStep = false;
        for (Coordinate coord : check(field, enemy, allies)) {
            if (enemy.get(0).coor.getX() == coord.getX() && enemy.get(0).coor.getY() == coord.getY()) {
                enemy.get(0).shag = true;
                return 1;
            }
        }
        return 0;
    }

    @Override
    ArrayList<Coordinate> check(int[][] field, ArrayList<figures> enemy, ArrayList<figures> allies) {
        ArrayList<Coordinate> movelist = new ArrayList<>();
        ArrayList<Coordinate> movelistOverall = new ArrayList<>();
        ArrayList<Coordinate> movelistEnemy = new ArrayList<>();
        int x = this.coor.getX();
        int y = this.coor.getY();
        movelist = hit(field, enemy, allies);
        for (figures figure : enemy) movelistEnemy.addAll(figure.hit(field, enemy, allies));
        for (Coordinate coord : movelist)
            for (Coordinate coords : movelistEnemy)
                if (coord.getX() == coords.getX() && coord.getY() == coords.getY())
                    movelistOverall.add(coord);
        movelist.removeAll(movelistOverall);
        if (this.firstStep && findRook(allies, false, this.coor.getY()) != null) {   //long rokirovka
            boolean ok = true;
            for (Coordinate coords : movelistEnemy) {
                if (x == coords.getX() && y == coords.getY()) ok = false;
                if (x - 2 == coords.getX() && y == coords.getY()) ok = false;
            }
            for (int i = 1; i < 4; i++) {
                if (field[x - i][y] != 0) break;
                if (i == 3 && ok) movelist.add(new Coordinate(x - 2, y));
            }
        }
        if (this.firstStep && findRook(allies, true, this.coor.getY()) != null) {        // short rokirovka
            boolean ok = true;
            for (Coordinate coords : movelistEnemy) {
                if (x == coords.getX() && y == coords.getY()) ok = false;
                if (x + 2 == coords.getX() && y == coords.getY()) ok = false;
            }
            for (int i = 1; i < 3; i++) {
                if (field[x + i][y] != 0) break;
                if (i == 2 && ok) movelist.add(new Coordinate(x + 2, y));
            }
        }
        return movelist;
    }

    @Override
    ArrayList<Coordinate> hit(int[][] field, ArrayList<figures> enemy, ArrayList<figures> allies) {
        ArrayList<Coordinate> movelist = new ArrayList<>();
        int x = this.coor.getX();
        int y = this.coor.getY();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++)
                if (x + i < 8 && y + j < 8 && x + i >= 0 && y + j >= 0) {
                    if (i == 0 && j == 0) continue;
                    if (field[x + i][y + j] >= 0 && this.color == 0)
                        movelist.add(new Coordinate(x + i, y + j));
                    if (field[x + i][y + j] <= 0 && this.color == 1)
                        movelist.add(new Coordinate(x + i, y + j));
                }
        }
        return movelist;
    }

    private figures findRook(ArrayList<figures> allies, boolean tiny, int y) {
        for (figures figure : allies) {
            if (tiny) {
                if (figure.getCoor().getX() == 7)
                    if (figure.getCoor().getY() == y)
                        if (figure.firstStep) return figure;
            } else if (figure.getCoor().getX() == 0)
                if (figure.getCoor().getY() == y)
                    if (figure.firstStep) return figure;
        }
        return null;
    }
}
