package com.missclick.mchess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    private int width;
    private int height;
    private Paint p;
    private Controller controller;

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
        p.setStrokeWidth(10);
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

        void drawMap(Canvas canvas, int[][] field){
            int scale = (width < height ? width : height) / 8;
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                   // Log.d("FIEL", "Field " + i + j + " = " + field[i][j]);
                    if(field[i][j] == 0){
                        //Log.d("EMPTY", "EMPTY");
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.STROKE);
                    }
                    if(field[i][j] == 1 || field[i][j] == -1){ //PAWN
                        p.setColor(Color.GREEN);
                        p.setStyle(Paint.Style.FILL);
                    }
                    if(field[i][j] == 2 || field[i][j] == -2){ //ROOK
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.FILL);
                    }
                    if(field[i][j] == 3 || field[i][j] == -3){ //KNIGHT
                        p.setColor(Color.BLUE);
                        p.setStyle(Paint.Style.FILL);
                    }
                    if(field[i][j] == 4 || field[i][j] == -4){ //BISHOP
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.FILL);
                    }
                    if(field[i][j] == 5 || field[i][j] == -5){ //QUEEN
                        p.setColor(Color.YELLOW);
                        p.setStyle(Paint.Style.FILL);
                    }
                    if(field[i][j] == 6 || field[i][j] == -6){ //KING
                        p.setColor(Color.CYAN);
                        p.setStyle(Paint.Style.FILL);
                    }
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
                    drawMap(canvas, controller.getField());
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

}
