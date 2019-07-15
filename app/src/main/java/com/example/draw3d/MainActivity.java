package com.example.draw3d;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private MySurfaceView surface;
    private Button draw;
    private boolean isdrawing=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surface = findViewById(R.id.gameView);
        draw = findViewById(R.id.button);
        draw.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (isdrawing)
                {
                    isdrawing = false;
                    draw.setBackgroundResource(R.drawable.location);
                    surface.cancelSettingLocation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isdrawing = true;
                surface.setLocation(draw.getLeft()+(int) motionEvent.getX(),draw.getTop()+(int)motionEvent.getY());
                draw.setBackgroundResource(R.drawable.recover);
                break;
            case MotionEvent.ACTION_UP:

                break;
                default:
                    break;
        }
        return true;
    }
}
