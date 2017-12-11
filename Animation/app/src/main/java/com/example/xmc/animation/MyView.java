package com.example.xmc.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by xmc on 2017/12/8.
 */

public class MyView extends View {
    public static final float RADIUS = 70f;
    private Point currentPoint;
    private Paint mPaint;// 绘图画笔

    // 构造方法(初始化画笔)
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    // 复写onDraw()从而实现绘制逻辑
    // 绘制逻辑:先在初始点画圆,通过监听当前坐标值(currentPoint)的变化,每次变化都调用onDraw()重新绘制圆,从而实现圆的平移动画效果

    @Override
    protected void onDraw(Canvas canvas) {
        // 如果当前点坐标为空(即第一次)

        //如果currentPoint对象不等于空，那么就调用drawCircle()方法在currentPoint的坐标位置画出一个半径为50的圆，
        // 如果currentPoint对象是空，那么就调用startAnimation()方法来启动动画。
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            // 创建一个点对象(坐标是(70,70))
            // 在该点画一个圆:圆心 = (70,70),半径 = 70
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, RADIUS, mPaint);

            //将属性动画作用到View里
            // 步骤1:创建初始动画时的对象点  & 结束动画时的对象点
            Point startPoint = new Point(RADIUS, RADIUS);//起始
            Point endPoint = new Point(500, 500);//结束，随便设的

            // 步骤2:创建动画对象 & 设置初始值 和 结束值
            ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvalutor(), startPoint, endPoint);

            valueAnimator.setDuration(5000);

            valueAnimator.setRepeatCount(1);
            //重复播放次数
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            // 设置动画时长

            //监听器
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentPoint = (Point) animation.getAnimatedValue();

                    invalidate();

                    // 调用invalidate()后,就会刷新View,即才能看到重新绘制的界面,即onDraw()会被重新调用一次
                    // 所以坐标值每改变一次,就会调用onDraw()一次
                }
            });
            valueAnimator.start();


        } else {
            //画圆
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, RADIUS, mPaint);
        }
    }
}
