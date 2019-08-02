package com.missclick.mchess;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{

    private DrawView drawView;
    private Controller controller;
    private int width;
    private int height;
    private int scale;
    private int OFFSET;
    private figures selectedFigure;
    private boolean one;
    private II ii;

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
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if(type == 2) one = false;
        if(type == 1) {
            Log.d("MYLOG", "ONE player");
            one = true;
            ii = new II(controller.getField(),controller);
        }
        setContentView(drawView);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY() - OFFSET;
        if (event.getAction() == MotionEvent.ACTION_DOWN){ // нажатие
                int a = (int) (x/scale);
                int b = (int) (y/(scale));
                if(!one) multiPlayer(a, b);
                else singlePlayer(a,b);
        }
        return false;
    }
    void multiPlayer(int a, int b){
        if(b < 8 && b >= 0) {
            figures figuer = controller.findFigure(new Coordinate(a, b));
            if(figuer == null || (figuer.getColor() == 0 && controller.getStep() % 2 != 0 ||
                    figuer.getColor() == 1 && controller.getStep() % 2 == 0)) {
                figures tempFigure = controller.selectFigure(figuer);
                if(tempFigure == null) controller.move(new Coordinate(a,b), selectedFigure);
                else selectedFigure = tempFigure;
            }
            else if(selectedFigure != null && figuer.getColor() != selectedFigure.getColor()){
                controller.move(new Coordinate(a,b), selectedFigure);
            }
            drawView.postInvalidate();
        }

    }
    void singlePlayer(int a, int b){
        if(b < 8 && b >= 0) {
            figures figuer = controller.findFigure(new Coordinate(a, b));
            if(figuer == null && selectedFigure != null){
                controller.move(new Coordinate(a,b), selectedFigure);
                ii.randomStep();
            }
            else{
                controller.selectFigure(figuer);
                selectedFigure = figuer;
            }
            drawView.postInvalidate();
        }
    }

}
