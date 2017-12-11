package com.example.xmc.animation;

import android.animation.TypeEvaluator;

/**
 * 自定义Evalutor
 * Created by xmc on 2017/12/8.
 */

public class PointEvalutor implements TypeEvaluator {
    //对象过渡的逻辑
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        // 将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        //fraction 完成度。 计算得到当前x,y
        float x =startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

        //计算后的坐标封装到一个新的对象后返回
        Point point = new Point(x, y);
        return point;

    }
}
