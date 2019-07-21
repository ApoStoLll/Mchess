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
    private Controller controller;
    private Bitmap bitmap;

    public DrawView(Context context, Controller controller) {
        super(context);
        this.controller = controller;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        getHolder().addCallback(this);
        p = new Paint();
        paint = new Paint();
        paint.setStrokeWidth(10);
        p.setStrokeWidth(10);
        Paint paint1 = new Paint();
        paint1.setStrokeWidth(10);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(Color.GREEN);
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
            int scale = (width < height ? width : height) / 8;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++) {
                    if((i + j) % 2 == 0){
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.GRAY);
                    }
                    else{
                        paint.setStyle(Paint.Style.FILL);
                        //paint.setColor(Color.rgb(255, 243, 176));
                        paint.setColor(Color.WHITE);
                    }
                    canvas.drawRect(i * scale, j * scale, (i+1) * scale, (j+1) * scale, paint);
                }
            }
        }


        void drawMap(Canvas canvas, int[][] field){
            int scale = (width < height ? width : height) / 8;
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
                        Rect rectDst = new Rect(i * scale, j * scale, (i + 1) * scale, (j + 1) * scale);
                        canvas.drawBitmap(bitmap, rectSrc, rectDst, p);
                    }
                    //canvas.drawRect(i * scale, j * scale, (i+1) * scale, (j+1) * scale, p);
                }
            }
        }

        void drawMoveList(Canvas canvas, ArrayList<Coordinate> moveList){
            int scale = (width < height ? width : height) / 8;
            if(!moveList.isEmpty()){
                for(Coordinate coor : moveList){
                    int i = coor.getX();
                    int j = coor.getY();
                    canvas.drawRect(i * scale, j * scale, (i+1) * scale, (j+1) * scale, p);
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
                    drawMoveList(canvas, controller.getMoveList());
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

}
