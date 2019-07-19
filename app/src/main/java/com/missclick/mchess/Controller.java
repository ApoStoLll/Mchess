package com.missclick.mchess;

import java.util.ArrayList;

public class Controller {
    private int field[][];
    private ArrayList<figures> black;
    private ArrayList<figures> white;
    Controller(){
        field = new int[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                field[i][j] = 0;
            }
        }
        for(int i = 0; i < 8; i ++){
            black.add(new Pawn(0));
            white.add(new Pawn(1));
        }
    }
}
