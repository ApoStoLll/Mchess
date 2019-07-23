package com.missclick.mchess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSingle;
    Button btnMult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        switch (v.getId()){
            case R.id.button2: //2 player
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.button3:
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
        }
    }
}
