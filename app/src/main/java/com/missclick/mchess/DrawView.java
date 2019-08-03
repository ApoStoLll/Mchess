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
        OFFSET = (height - 8 * scale) / 2;
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
        bitmapSide = bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fon2);
        bitmapPoint = bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.point);
        bitmapPointSecond = bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.point21); // вставить другую картинку
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
                   // Log.d("FIEL", "Field " + i + j + " = " + field[i][j])
                    if(field[i][j] == 1) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pawnwh);
                    if(field[i][j] == -1) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pawnbl);
                    if(field[i][j] == 2) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rookwh);
                    if(field[i][j] == -2) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rookbl);
                    if(field[i][j] == 3) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.knightwh);
                    if(field[i][j] == -3) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.knightbl);
                    if(field[i][j] == 4) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bishopwh);
                    if(field[i][j] == -4) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bishopbl);
                    if(field[i][j] == 5) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queenwh);
                    if(field[i][j] == -5) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queenbl);
                    if(field[i][j] == 6) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kingwh);
                    if(field[i][j] == -6) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kingbl);
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
            if(deadBlack != null && !deadBlack.isEmpty()){
                if(true) {
                    bitmapDead = BitmapFactory.decodeResource(getResources(), R.drawable.pawnbl);
                    Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    Rect rectDst = new Rect(0 * scale, -1 * scale + OFFSET, 1 * scale, (0) * scale + OFFSET);
                    canvas.drawBitmap(bitmapDead, rectSrc, rectDst, p);
                }
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
                    drawDead(canvas, controller.getDeadBlack(), controller.getDeadWhite());
                    drawSide(canvas);
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

}
