package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView2 extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder sfh;
    private Paint paint;
    private int textX=30,textY=30;
    private Thread th;
    private boolean flag;
    private Canvas canvas;
    private int screenW,screenH;

    public MySurfaceView2(Context context){
        super(context);
        //实例SufaceView
        sfh=this.getHolder();
        //为SufaceView添加状态监听
        sfh.addCallback(this);
        paint=new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.GREEN);
        setFocusable(true);

    }

    @Override
    //SurfaceView被创建后响应
    public void surfaceCreated(SurfaceHolder holder) {
        screenH=this.getWidth();
        screenW=this.getHeight();
        flag=true;
        th=new Thread(this);
        th.start();

    }

    @Override
    //SurfaceView状态发生变化时响应
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    //SurfaceView状态被摧毁时响应
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag=false;
    }

    //SurfaceView 是通过SurfaceHolder 来修改其数据，所以即时重写View 的onDraw(Canvas canvas)函数，在SurfaceView 启动时也不会执行到,因此这里自定义绘图函数
    public void myDraw()
    {
        try {
            canvas=sfh.lockCanvas();
            if(canvas!=null){
                // ————利用绘制矩形的方式刷屏
                // canvas.drawRect(0, 0, this.getWidth(), this.getHeight(),
                // paint);
                // ————利用填充画布，刷屏
                // canvas.drawColor(Color.BLACK);
                // ————利用填充画布指定的颜色分量，刷屏
                canvas.drawRGB(0,0,0);
                canvas.drawText("空白永不败北",textX,textY,paint);
            }
        }catch (Exception e){

        }finally {
            if(canvas!=null){
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }

    //重写触屏监听事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        textX = (int)event.getX();
        textY = (int)event.getY();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void logic() {
    }


    @Override
    public void run() {
        while(flag){
            long start=System.currentTimeMillis();
            myDraw();
            logic();
            long end=System.currentTimeMillis();
            try {
                if(end-start<50){
                    Thread.sleep(50-(end-start));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}