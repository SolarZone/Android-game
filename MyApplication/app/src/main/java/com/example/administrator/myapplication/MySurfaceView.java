package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder sfh;
    private Paint paint;
    private int textX=30,textY=30;

    public MySurfaceView(Context context){
        super(context);
        //实例SufaceView
        sfh=this.getHolder();
        //为SufaceView添加状态监听
        sfh.addCallback(this);
        paint=new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.GREEN);

    }

    @Override
    //SurfaceView被创建后响应
    public void surfaceCreated(SurfaceHolder holder) {
        myDraw();
    }

    @Override
    //SurfaceView状态发生变化时响应
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    //SurfaceView状态被摧毁时响应
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    //SurfaceView 是通过SurfaceHolder 来修改其数据，所以即时重写View 的onDraw(Canvas canvas)函数，在SurfaceView 启动时也不会执行到,因此这里自定义绘图函数
    public void myDraw()
    {
        //获取SurfaceView 的Canvas 对象，
        //同时对获取的Canvas 画布进行加锁，防止SurfaceView 在绘制过程中被修改、摧毁等发生的状态改变
        //另外一个lockCanvas(Rect rect)函数，其中传入一个Rect矩形类的实例，用于得到一个自定义大小的画布
        Canvas canvas = sfh.lockCanvas();
        //填充背景色,即刷屏,每次在画布绘图前都对画布进行一次整体的覆盖
        canvas.drawColor(Color.BLACK);
        //绘制内容
        canvas.drawText("This is a Text !", textX, textY, paint);
        //解锁画布和提交
        sfh.unlockCanvasAndPost(canvas);
    }

    //重写触屏监听事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        textX = (int)event.getX();
        textY = (int)event.getY();
        myDraw();
        return super.onTouchEvent(event);
    }

}