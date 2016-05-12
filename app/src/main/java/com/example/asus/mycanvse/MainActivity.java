package com.example.asus.mycanvse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   private Button mButton1,mButton2,mButton3,mButton4,mButton5,mButton6;
    private ImageView mImagView;
    private PathEffect[] pathEffect=new PathEffect[7];
    private int[] mColer;

    private Paint mPaint;
    private Path mPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1=(Button)findViewById(R.id.color_but);
        mButton2=(Button)findViewById(R.id.lin_but);
        mButton3=(Button)findViewById(R.id.cycle_but);
        mButton4=(Button)findViewById(R.id.img_but);
        mButton5=(Button)findViewById(R.id.dial_but);
        mButton6=(Button)findViewById(R.id.panit_but);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mImagView=(ImageView)findViewById(R.id.img_view);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.color_but:
                drawColor();
                break;
            case R.id.lin_but:
                drawLine();
                break;
            case R.id.img_but:
                drawImage();
                break;
            case R.id.cycle_but:
                drawRound();
                break;
            case R.id.dial_but:
                getDial();
                    break;
            case R.id.panit_but:
                drawPath();

                break;
        }
    }

    private void drawPath() {
        Bitmap bitmap=Bitmap.createBitmap(mImagView.getWidth(),mImagView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPath=new Path();

        mPath.moveTo(0,0);
        for(int i=1;i<15;i++){
            //随机15个点并串联
            mPath.lineTo(i*40, (float) (Math.random()*60));
        }
        mColer = new int[]{
                Color.BLACK,Color.BLUE,Color.CYAN,Color.DKGRAY,Color.GRAY,Color.GREEN,
                Color.RED};
        pathEffect[0]=null;
        //绘制圆角路径
        pathEffect[1]=new CornerPathEffect(10);
        //虚线  3是虚线一段的长度，5是虚线间的距离，6
        pathEffect[2]=new DashPathEffect(new float[]{3,5,6,1},0);
        pathEffect[3]=new DiscretePathEffect(3,5);
        Path path=new Path();
        path.addRect(0,0,8,8, Path.Direction.CCW);
        pathEffect[4]=new PathDashPathEffect(path,12,0, PathDashPathEffect.Style.ROTATE);
        //两种图像的叠加
        pathEffect[5]=new ComposePathEffect(pathEffect[3],pathEffect[4]);
        //两种图像的混合
        pathEffect[6]=new SumPathEffect(pathEffect[2],pathEffect[5]);
        canvas.translate(7,7);
        for(int i=0;i<pathEffect.length;i++){
            mPaint.setPathEffect(pathEffect[i]);
            mPaint.setColor(mColer[i]);
            canvas.drawPath(mPath,mPaint);
            canvas.translate(0,40);
        }
        mImagView.setImageBitmap(bitmap);
    }


    private void getDial() {
        Bitmap bitmap=Bitmap.createBitmap(mImagView.getWidth(),mImagView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        //设置抗锯齿
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mImagView.getWidth()/2,mImagView.getHeight()/2,mImagView.getWidth()/2,paint);
        canvas.save();
        for (int i=1;i<=12;i++) {
            //画布围绕圆心旋转
            canvas.rotate(360/12,mImagView.getWidth()/2,mImagView.getHeight()/2);
            canvas.drawLine(mImagView.getWidth()/2,20,mImagView.getWidth()/2,40,paint);
            canvas.drawText(i+"",mImagView.getWidth()/2,60,paint);
        }
        canvas.restore();
        mImagView.setImageBitmap(bitmap);
    }

    private void drawImage() {
        Bitmap bitmap=Bitmap.createBitmap(mImagView.getWidth(),mImagView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher),0,0,null);
        mImagView.setImageBitmap(bitmap);
    }

    private void drawColor() {
        Bitmap bitmap=Bitmap.createBitmap(mImagView.getWidth(),mImagView.getHeight(), Bitmap.Config.ARGB_8888);
        //在bitmap上创建一个画布
        Canvas canvas=new Canvas(bitmap);
        //给画布添加颜色
        canvas.drawColor(Color.GREEN);
        mImagView.setImageBitmap(bitmap);
    }

    private void drawRound() {
        Bitmap bitmap=Bitmap.createBitmap(mImagView.getWidth(),mImagView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置画笔的style
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mImagView.getWidth()/2,mImagView.getHeight()/2,50,paint);
        mImagView.setImageBitmap(bitmap);
    }

    private void drawLine() {
        Bitmap bitmap=Bitmap.createBitmap(mImagView.getWidth(),mImagView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        canvas.drawLine(0,0,mImagView.getWidth(),mImagView.getHeight(),paint);
        mImagView.setImageBitmap(bitmap);
    }
}
