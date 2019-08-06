package com.missclick.mchess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    private int width;
    private int height;
    private Paint p;
    private Paint paint;
    private Paint paintSide;
    private Paint paintMove;
    private Controller controller;
    private Bitmap bitmap;
    private Bitmap bitmapSide;
    private Bitmap bitmapPoint;
    private Bitmap bitmapPointSecond;
    private Bitmap bitmapDead;
    private Bitmap bitmapPawnwh;
    private Bitmap bitmapPawnbl;
    private Bitmap bitmapKnightwh;
    private Bitmap bitmapKnightbl;
    private Bitmap bitmapRookwh;
    private Bitmap bitmapRookbl;
    private Bitmap bitmapBishopwh;
    private Bitmap bitmapBishopbl;
    private Bitmap bitmapQueenwh;
    private Bitmap bitmapQueenbl;
    private Bitmap bitmapKingwh;
    private Bitmap bitmapKingbl;
    private int OFFSET;
    private int scale;
    private Rect sideSrc;
    private Rect sideDstTop;
    private Rect sideDstBot;
    private Rect pointSrc;

    public DrawView(Context context, Controller controller) {
        super(context);
        this.controller = controller;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        scale = (width < height ? width : height) / 8;
        OFFSET = (height - 9 * scale) / 2;
        getHolder().addCallback(this);
        p = new Paint();
        paintSide = new Paint();
        paintSide.setStrokeWidth(10);
        paintSide.setStyle(Paint.Style.FILL);
        paintSide.setColor(Color.rgb(230,230,230));
        paint = new Paint();
        paint.setStrokeWidth(10);
        p.setStrokeWidth(10);
        paintMove = new Paint();
        paintMove.setStrokeWidth(10);
        paintMove.setStyle(Paint.Style.FILL);
        paintMove.setColor(Color.GREEN);
        p.setTextSize(7 * scale / 10);
        bitmapSide = BitmapFactory.decodeResource(getResources(), R.drawable.fon2);
        bitmapPoint = BitmapFactory.decodeResource(getResources(), R.drawable.point);
        bitmapPointSecond  = BitmapFactory.decodeResource(getResources(), R.drawable.point21);
        bitmapPawnwh = BitmapFactory.decodeResource(getResources(), R.drawable.pawnwh);
        bitmapPawnbl = BitmapFactory.decodeResource(getResources(), R.drawable.pawnbl);
        bitmapRookwh = BitmapFactory.decodeResource(getResources(), R.drawable.rookwh);
        bitmapRookbl = BitmapFactory.decodeResource(getResources(), R.drawable.rookbl);
        bitmapKnightwh = BitmapFactory.decodeResource(getResources(), R.drawable.knightwh);
        bitmapKnightbl = BitmapFactory.decodeResource(getResources(), R.drawable.knightbl);
        bitmapBishopwh = BitmapFactory.decodeResource(getResources(), R.drawable.bishopwh);
        bitmapBishopbl = BitmapFactory.decodeResource(getResources(), R.drawable.bishopbl);
        bitmapQueenwh = BitmapFactory.decodeResource(getResources(), R.drawable.queenwh);
        bitmapQueenbl = BitmapFactory.decodeResource(getResources(), R.drawable.queenbl);
        bitmapKingwh = BitmapFactory.decodeResource(getResources(), R.drawable.kingwh);
        bitmapKingbl = BitmapFactory.decodeResource(getResources(), R.drawable.kingbl);

        sideSrc = new Rect(0, 0, bitmapSide.getWidth(), bitmapSide.getHeight());
        sideDstTop = new Rect(0, 0, width, OFFSET);
        sideDstBot = new Rect(0, 8 * scale + OFFSET, width, height);
        pointSrc = new Rect(0, 0, bitmapPoint.getWidth(), bitmapPoint.getHeight());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class DrawThread extends Thread {

        private boolean running = false;
        private SurfaceHolder surfaceHolder;

        DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        void setRunning(boolean running) {
            this.running = running;
        }

        void drawCells(Canvas canvas){
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++) {
                    if((i + j) % 2 == 1){
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.rgb(120, 89, 58));
                    }
                    else{
                        paint.setStyle(Paint.Style.FILL);
                        //paint.setColor(Color.rgb(255, 243, 176));
                        paint.setColor(Color.rgb(199, 192, 186));
                    }
                    canvas.drawRect(i * scale, j * scale + OFFSET, (i+1) * scale, (j+1) * scale + OFFSET, paint);
                }
            }
        }
        void drawSide(Canvas canvas){
            canvas.drawBitmap(bitmapSide, sideSrc, sideDstTop, paintSide);
            canvas.drawBitmap(bitmapSide, sideSrc, sideDstBot, paintSide);
        }

        void drawMap(Canvas canvas, int[][] field){
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(field[i][j] == 1) bitmap = bitmapPawnwh;
                    if(field[i][j] == -1) bitmap = bitmapPawnbl;
                    if(field[i][j] == 2) bitmap = bitmapRookwh;
                    if(field[i][j] == -2) bitmap = bitmapRookbl;
                    if(field[i][j] == 3) bitmap = bitmapKnightwh;
                    if(field[i][j] == -3) bitmap = bitmapKnightbl;
                    if(field[i][j] == 4) bitmap = bitmapBishopwh;
                    if(field[i][j] == -4) bitmap = bitmapBishopbl;
                    if(field[i][j] == 5) bitmap = bitmapQueenwh;
                    if(field[i][j] == -5) bitmap = bitmapQueenbl;
                    if(field[i][j] == 6) bitmap = bitmapKingwh;
                    if(field[i][j] == -6) bitmap = bitmapKingbl;
                    if(field[i][j] != 0) {
                        Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        Rect rectDst = new Rect(i * scale, j * scale + OFFSET, (i + 1) * scale, (j + 1) * scale + OFFSET);
                        canvas.drawBitmap(bitmap, rectSrc, rectDst, p);
                    }
                }
            }
        }

        void drawMoveList(Canvas canvas, ArrayList<Coordinate> moveList,Coordinate selected){
            if(moveList != null && !moveList.isEmpty()){
                for(Coordinate coor : moveList){
                    int i = coor.getX();
                    int j = coor.getY();
                    Rect rectDst = new Rect(i * scale, j * scale + OFFSET, (i+1) * scale, (j+1) * scale + OFFSET);
                    canvas.drawBitmap(bitmapPoint, pointSrc, rectDst, paintMove);
                }
            }
            if(selected != null){
                int i = selected.getX();
                int j = selected.getY();
                Rect rectDst = new Rect(i * scale, j * scale + OFFSET, (i+1) * scale, (j+1) * scale + OFFSET);
                canvas.drawBitmap(bitmapPointSecond, pointSrc, rectDst, paintMove);
            }
        }

        void drawDead(Canvas canvas, ArrayList<figures> deadBlack, ArrayList<figures> deadWhite){
            if(!deadWhite.isEmpty()){
                int i = 0;
                int pawn = 0;
                for(figures figur : deadWhite) {
                    if(figur.getClass().getName().equals("com.missclick.mchess.Pawn")) {
                        pawn++;
                        continue;
                    }
                    if(figur.getClass().getName().equals("com.missclick.mchess.Knight")) bitmapDead = bitmapKnightwh;
                    if(figur.getClass().getName().equals("com.missclick.mchess.Bishop")) bitmapDead = bitmapBishopwh;
                    if(figur.getClass().getName().equals("com.missclick.mchess.Rook")) bitmapDead = bitmapBishopwh;
                    if(figur.getClass().getName().equals("com.missclick.mchess.Queen")) bitmapDead = bitmapQueenwh;
                    Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    Rect rectDst = new Rect(i * scale, -1 * scale + OFFSET, (1+i) * scale, OFFSET);
                    canvas.drawBitmap(bitmapDead, rectSrc, rectDst, p);
                    i++;
                }
                if(pawn !=0) {
                    bitmapDead = bitmapPawnwh;
                    Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    Rect rectDst = new Rect(i * scale, -1 * scale + OFFSET, (1 + i) * scale, OFFSET);
                    canvas.drawBitmap(bitmapDead, rectSrc, rectDst, p);
                    if(pawn > 1) canvas.drawText("x"+ pawn, (i + 1) * scale - (scale/7), OFFSET - (scale/5), p);
                }
            }
            if(!deadBlack.isEmpty()){
                int i = 0;
                int pawn = 0;
                for(figures figur : deadBlack) {
                    if(figur.getClass().getName().equals("com.missclick.mchess.Pawn")) {
                        pawn++;
                        continue;
                    }
                    if(figur.getClass().getName().equals("com.missclick.mchess.Knight")) bitmapDead = bitmapKnightbl;
                    if(figur.getClass().getName().equals("com.missclick.mchess.Bishop")) bitmapDead = bitmapBishopbl;
                    if(figur.getClass().getName().equals("com.missclick.mchess.Rook")) bitmapDead = bitmapRookbl;
                    if(figur.getClass().getName().equals("com.missclick.mchess.Queen")) bitmapDead = bitmapQueenbl;
                    Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    Rect rectDst = new Rect(i * scale, 8 * scale + OFFSET, (1+i) * scale, OFFSET + 9*scale);
                    canvas.drawBitmap(bitmapDead, rectSrc, rectDst, p);
                    i++;
                }
                if(pawn !=0) {
                    bitmapDead = bitmapPawnbl;
                    Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    Rect rectDst = new Rect(i * scale, 8 * scale + OFFSET, (1+i) * scale, OFFSET + 9*scale);
                    canvas.drawBitmap(bitmapDead, rectSrc, rectDst, p);
                    if(pawn > 1) canvas.drawText("x"+ pawn, (i + 1) * scale - (scale/7), OFFSET + 9*scale - (scale/5), p);
                }
            }
        }

        void drawSituation(Canvas canvas, String situation){
            if(situation != null){
                canvas.drawText(situation, 3*scale + scale/4 , scale, p);
            }
        }
        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;
                    drawCells(canvas);
                    drawMap(canvas, controller.getField());
                    drawMoveList(canvas, controller.getMoveList(), controller.getSelected());
                    drawSide(canvas);
                    drawDead(canvas, controller.getDeadBlack(), controller.getDeadWhite());
                    drawSituation(canvas, controller.getSituation());
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

}
