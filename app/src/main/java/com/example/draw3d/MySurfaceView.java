package com.example.draw3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable, View.OnTouchListener {
    private Point virtulLine;
    public Point location = new Point();
    public boolean isettingLocation = false;

//    public MySurfaceView(Context context) {
//        super(context);
//        this.getHolder().addCallback(this);
//        this.setOnTouchListener(this);
//    }
    public MySurfaceView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.getHolder().addCallback(this);
        this.setOnTouchListener(this);
    }
    public void setLocation(int x,int y)
    {
        isettingLocation = true;
        location.x = x;
        location.y = y;
    }
    public void cancelSettingLocation(){
        isettingLocation = false;
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                //TODO 添加item选中判断
                break;
            case MotionEvent.ACTION_MOVE:
                virtulLine = new Point();
                virtulLine.x = (int) motionEvent.getX();
                virtulLine.y = (int) motionEvent.getY();
//                Canvas canvas = this.getHolder().lockCanvas();
//                canvas.translate(motionEvent.getX(),motionEvent.getY());
                break;
             case MotionEvent.ACTION_UP:
                    virtulLine = null;
                    break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void run() {
        long t = 0 ;
        Canvas canvas;
        while (true) {
            canvas = this.getHolder().lockCanvas();
            canvas.drawColor(Color.WHITE);
            t = System.currentTimeMillis();
            drawBg(canvas,this.getWidth(),this.getHeight());
            if (virtulLine!=null)
                drawVirtual(canvas,virtulLine);
            if (isettingLocation)
                drawLocationPut(canvas,location);
            this.getHolder().unlockCanvasAndPost(canvas);
            try {
                Thread.sleep(Math.max(0, 33-(System.currentTimeMillis()-t)));
             } catch (InterruptedException e) {
            // TODO Auto-generated catch block					e.printStackTrace();
            //
            }
        }


    }




    public void drawBg(Canvas canvas, int width, int height)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(1.0f);
        for (int i=0;i<width;i+=20)
        {
            canvas.drawLine(i,0,i,height,paint);
        }
        for (int i=0;i<height;i+=20)
            canvas.drawLine(0,i,width,i,paint);
    }
    public void drawVirtual(Canvas canvas,Point path)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1.0f);
        paint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
        canvas.drawLine(this.getWidth()/2,this.getHeight()/2,virtulLine.x,virtulLine.y,paint);
      //  canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.paint),virtulLine.x,virtulLine.y,paint);
//        Rect mSrcRect = new Rect(0, 0, 40, 40);
//        Rect mDestRect = new Rect(virtulLine.x, virtulLine.y, virtulLine.x+40, virtulLine.y+40);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.paint),mSrcRect,mDestRect,paint);
    }
    public void drawLocationPut(Canvas canvas,Point point)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setFilterBitmap(true);

        paint.setDither(true);
        Rect mSrcRect = new Rect(0, 0, 200, 200);
        Rect mDestRect = new Rect(location.x-20, location.y-20, location.x+20, location.y+20);


        canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.location),mSrcRect,mDestRect,paint);
    }
}
