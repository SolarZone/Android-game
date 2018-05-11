package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2018/5/5.
 */

public class ClipRect extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder sfh;
    private Paint paint;
    private int textX=30,textY=30;
    private Canvas canvas;

    public ClipRect(Context context){
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
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                //通过图片资源生成一张Bitmap 位图
                Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.p1);

                canvas.save();
                canvas.drawText("原图：", 20, 20, paint);
                canvas.drawBitmap(bmp, 20, 30,paint);
                canvas.restore();

                canvas.save();
                canvas.drawText("通过坐标，设置矩形可视区域：", 20, 320, paint);
                /**
                 * 为画布设置矩形可视区域
                 * 第一、二个参数为可视区域的左上角
                 * 第三、四个参数为可视区域的右下角
                 */
                canvas.clipRect(20,330,bmp.getWidth()+20,bmp.getHeight()/2+330);
                canvas.drawBitmap(bmp, 20, 330,paint);
                canvas.restore();

                canvas.save();
                canvas.drawText("利用 Path 来设置可视区域的形状,这里为圆形可视区域：", 20, 620, paint);
                Path path = new Path();
                path.addCircle(20+bmp.getWidth()/2, 630+bmp.getHeight()/2, bmp.getWidth()/2, Path.Direction.CCW);
                /**
                 * 为画布设置可视区域
                 * 参数：Path实例
                 * 利用Paht 可以为位图设置任何需要的可视区域，这里是设置一个圆形可视区域。
                 */
                canvas.clipPath(path);
                canvas.drawBitmap(bmp, 20, 630, paint);
                canvas.restore();

                canvas.save();
                canvas.drawText("利用 Region 来对画布设置可视区域：", 20, 920, paint);
                Region region = new Region();
                //不显示交集区域
                canvas.clipRect(20,930,120,1030,Region.Op.UNION);
                canvas.clipRect(50,930,100,1080,Region.Op.XOR);
                canvas.drawBitmap(bmp, 20, 930, paint);
                canvas.restore();
            }
        } catch (Exception e) {
        } finally {
            if (canvas != null) {
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }




}
