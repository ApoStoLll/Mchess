package com.missclick.mchess;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
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
    //private figures selectedFigure;
    private boolean one;
    private AI ii;
   // private MediaPlayer shah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        scale = (width < height ? width : height) / 8;
        OFFSET = (height - 9 * scale) / 2;
        controller = new Controller();
        drawView = new DrawView(this, controller);
        drawView.setOnTouchListener(this);
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if(type == 2) one = false;
        if(type == 1) {
            Log.d("MYLOG", "ONE player");
            one = true;
            ii = new AI(controller);
        }
        setContentView(drawView);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //drawView.postInvalidate();
        float x = event.getX();
        float y = event.getY() - OFFSET;
        if (event.getAction() == MotionEvent.ACTION_DOWN){ // нажатие
                int a = (int) (x/scale);
                int b = (int) (y/(scale));
                if(a == 7 && (int)(event.getY()/(scale)) == 0) {
                    //selectedFigure = null;
                    controller.revert(controller.getField());
                }
                if(!one) multiPlayer(a, b);
                else singlePlayer(a,b);
        }

        return false;
    }
    void multiPlayer(int a, int b){
        if(b < 8 && b >= 0) {
            /*figures figuer = controller.findFigure(new Coordinate(a, b));
            if(figuer == null || (figuer.getColor() == 0 && controller.getNum() % 2 != 0 ||
                    figuer.getColor() == 1 && controller.getNum() % 2 == 0)) {
                figures tempFigure = controller.selectFigure(figuer);
                if(tempFigure == null && selectedFigure != null){
                    controller.move(new Step(new Coordinate(a, b), selectedFigure));//new Coordinate(a,b), selectedFigure);
                    selectedFigure = null;
                }
                else selectedFigure = tempFigure;
            }
            else if(selectedFigure != null && figuer.getColor() != selectedFigure.getColor()){
                controller.move(new Step(new Coordinate(a,b), selectedFigure));
                selectedFigure = null;
            }
            drawView.postInvalidate();
            //shah = MediaPlayer.create(this,R.raw.shah);*/
            figures figure = controller.findFigure(new Coordinate(a, b));
            if(figure != null){
                if(figure.getColor() == 0 && controller.getNum() % 2 != 0 ||
                        figure.getColor() == 1 && controller.getNum() % 2 == 0)
                    controller.selectFigure(figure);
                else
                    if(controller.getSelected() != null)
                        controller.move(new Step(new Coordinate(a, b), controller.getSelected()), controller.getField());
            }
            else{
                if(controller.getSelected() != null)
                    controller.move(new Step(new Coordinate(a, b), controller.getSelected()), controller.getField());
            }
            drawView.postInvalidate();
        }

    }
    void singlePlayer(int a, int b){
        if(b < 8 && b >= 0) {
            figures figuer = controller.findFigure(new Coordinate(a, b));
            if((figuer == null && controller.getSelected() != null) ||
                    (figuer != null && figuer.getColor() == 0 && controller.getSelected() != null)){
                Log.d("Game Activity", "move()");
                controller.move(new Step(new Coordinate(a, b), controller.getSelected()), controller.getField());
            }
            if(controller.getNum() % 2 != 0) ii.move();
            else{
                if(figuer != null && figuer.getColor() == 1) {
                    Log.d("Game Activity", "select figure");
                    controller.selectFigure(figuer);
                }
                else{
                    ///controller.cancelSelected();
                    controller.moveList = null;
                }
            }
            drawView.postInvalidate();
        }
    }

}
