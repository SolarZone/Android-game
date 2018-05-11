package com.example.administrator.myapplication;




import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class robotView extends SurfaceView implements Callback,Runnable {
    private SurfaceHolder sfh;
    private Canvas canvas;
    private Paint paint;
    private boolean flag;
    private Thread th;
    //机器人位图
    private Bitmap bmpRobot ;
    //机器人的方向常量
    private final int DIR_LEFT =0;
    private final int DIR_RIGHT=1;
    //机器人当前的方向
    private int dir = DIR_RIGHT;
    //动作帧下标
    private int currentFrame;
    //机器人的X,Y位置
    private int robot_x=100,robot_y=100;
    //处理按键卡现象
    private boolean isUp, isDown, isLeft, isRight;
    public robotView(Context context) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);
        bmpRobot = BitmapFactory.decodeResource(this.getResources(), R.drawable.robot);
    }
    /**
     * SurfaceView视图创建，响应此函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        flag = true;
        //实例线程
        th = new Thread(this);
        //启动线程
        th.start();
    }

    /**
     * SurfaceView视图状态发生改变，响应此函数
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }
    /**
     * SurfaceView视图消亡时，响应此函数
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }

    /**
     * 绘制函数
     */
    private void myDraw()
    {
        try {
            canvas = sfh.lockCanvas();
            if(canvas!=null)
            {
                canvas.drawColor(Color.BLACK);
                drawFrame(currentFrame,canvas,paint);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if(canvas!=null)
            {
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }
    /**
     *
     * @param currentFrame 绘制帧

     *             每帧的高
     * @param canvas
     *             画布实例
     * @param paint
     *              画笔实例
     */
    private void drawFrame(int currentFrame,Canvas canvas,Paint paint)
    {
        //每帧的宽
        int frameW = bmpRobot.getWidth() / 6;
        //每帧的高
        int frameH = bmpRobot.getHeight() / 2;
        //得到位图的列数
        int col = bmpRobot.getWidth() / frameW;
        //得到当前帧相对于位图的X坐标
        int x = currentFrame % col * frameW;
        //得到当前帧相对于位图的Y坐标
        int y = currentFrame / col * frameH;
        canvas.save();
        //设置一个宽高与机器人每帧相同大小的可视区域
        canvas.clipRect(robot_x, robot_y, robot_x + bmpRobot.getWidth() / 6, robot_y + bmpRobot.getHeight() / 2);
        if (dir == DIR_LEFT) {//如果是向左侧移动
            //镜像操作 - 反转 - 改变机器人动画的朝向
            canvas.scale(-1, 1, robot_x - x + bmpRobot.getWidth() / 2, robot_y - y + bmpRobot.getHeight() / 2);
        }
        canvas.drawBitmap(bmpRobot, robot_x - x, robot_y - y, paint);
        canvas.restore();
    }
    /**
     * 游戏逻辑
     */
    private void logic() {
        //控制机器人位移方向
        if (isUp) {
            robot_y -= 3;
        }
        if (isDown) {
            robot_y += 3;
        }
        if (isLeft) {
            robot_x -= 3;
        }
        if (isRight) {
            robot_x += 3;
        }
        //动作帧数的循环控制，让其动作帧不断重复播放
        currentFrame++;
        if (currentFrame >= 12) {
            currentFrame = 0;
        }
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 40) {
                    Thread.sleep(40 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 按键事件监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            isUp = true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            isDown = true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            isLeft = true;
            dir = DIR_LEFT;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            isRight = true;
            dir = DIR_RIGHT;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            isUp = false;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            isDown = false;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            isLeft = false;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            isRight = false;
        }
        return super.onKeyUp(keyCode, event);
    }


}