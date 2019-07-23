package com.missclick.mchess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Point;
import android.view.Display;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private DrawView drawView;
    private Controller controller;
    private int width;
    private int height;
    private int scale;
    private int OFFSET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        scale = (width < height ? width : height) / 8;
        OFFSET = (height - 8 * scale) / 2;
        controller = new Controller();
        drawView = new DrawView(this, controller);
        drawView.setOnTouchListener(this);
        setContentView(drawView);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY() - OFFSET;
        //Log.d("MYLOG", "y: " + y + " OFFSET: " + OFFSET + " SCALE: " + scale);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                //if ((x/(width/8))  % 2 == 0 ){
                int a = (int) (x/scale);
                int b = (int) (y/(scale));
                if(b < 8 && b >= 0)  controller.selectFigure(new Coordinate(a,  b));
                //Log.d("MYLOG", "b: " + b + " y: " + y);

        }
        drawView.postInvalidate();
        return false;
    }
}
