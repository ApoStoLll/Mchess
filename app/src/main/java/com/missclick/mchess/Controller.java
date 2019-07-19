package com.missclick.mchess;

public class Controller {
    private int field[][];
    Controller(){
        field = new int[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                field[i][j] = 0;
            }
        }
    }
}
