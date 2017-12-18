package com.example.xmc.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button mButton;
    Button mButton2;
    MyView2 mMyView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn);
        mButton2 = (Button) findViewById(R.id.btn2);
        mMyView2 = (MyView2) findViewById(R.id.my_view2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);


            }
        });
        mMyView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CurveActivity.class);
                startActivity(intent);
            }
        });


        /**
         * 步骤：j
         * 1.设置动画初始值，结束值
         * 2.设置播放的各种属性
         * 3.通过动画的更新监视器，手动将改变的值赋值给对象的属性值
         *
         */
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mButton.getLayoutParams().width, 500);
        //ofInt(a,b);从a，平滑到b。也可以设置多个数值。a-b-c;
        valueAnimator.setDuration(2000);
        //运行时长
        valueAnimator.setStartDelay(100);
        //延迟播放时间
        valueAnimator.setRepeatCount(1);
        //重复播放次数
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        //重复播放的，动画模式。RESTART 正序，REVERSE倒叙
        //监听器
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue = (int) valueAnimator.getAnimatedValue();
                //改变后的值

                //赋值给对象的属性值
                mButton.getLayoutParams().width = currentValue;

                //刷新视图，即重新绘制
                mButton.requestLayout();

            }
        });


        valueAnimator.start();
        //启动动画


        // 2. 参数设置：参数说明如下
        // Object object：需要操作的对象
        // String property：需要操作的对象的属性
        // float ....values：动画初始值 & 结束值（不固定长度）

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mButton2, "alpha", 1f, 0f, 1f);
        objectAnimator.setRepeatCount(-1);
        // 动画作用的对象的属性是透明度alpha
        // 动画效果是:常规 - 全透明 - 常规
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mButton2, "rotation", 0f, 360f);
        rotate.setRepeatCount(-1);

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(objectAnimator).with(rotate);
        animSet.setDuration(5000);
        animSet.start();


        ObjectAnimator animator = ObjectAnimator.ofObject(mMyView2, "color", new ColorEvalutor(), "#0000FF", "#FF0000");
        animator.setDuration(5000);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(-1);
        animator.start();


    }
}
