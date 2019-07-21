package com.missclick.mchess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Point;
import android.view.Display;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private DrawView drawView;
    private Controller controller;
    private int width;
    private int height;
    float h;
    int a;
    int b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        h = (float) (height * 0.82);
        controller = new Controller();
        drawView = new DrawView(this, controller);
        drawView.setOnTouchListener(this);
        setContentView(drawView);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                //if ((x/(width/8))  % 2 == 0 ){
                a = (int) Math.ceil(x*8/width);
                b = (int) Math.ceil(y*8/h);
                controller.selectFigure(new Coordinate(((int) a), (int) b));
        }
        drawView.postInvalidate();
        return false;
    }
}
