package com.example.xmc.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by xmc on 2017/12/18.
 */

public class CurveActivity extends AppCompatActivity {
    ImageView iv;
    Button mButton;
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve);
        iv = (ImageView) findViewById(R.id.iv);
        mButton = (Button) findViewById(R.id.btn_pull);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relative);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView img = getClickDot(v, 30);
                mRelativeLayout.addView(img);

                ObjectAnimator animator1 = ObjectAnimator.ofFloat(img, "x", getWindowManager().getDefaultDisplay().getWidth());
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(img, "y", getWindowManager().getDefaultDisplay().getHeight());
                animator2.setInterpolator(new AccelerateInterpolator(3.0f));
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(800);
                animatorSet.playTogether(animator1, animator2);
                animatorSet.start();

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // 小球运行结束后从父容器中移除
                        mRelativeLayout.removeView(img);
                    }
                });

            }
        });
        parabola();
    }

    /**
     * 抛物线动画
     * 通过evaluator，fraction从0---1;
     */
    public void parabola() {

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setObjectValues(new PointF(0, 0));
        //valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {

            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                /**x方向200px/s ，则y方向0.5 * 200 * t**/
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                iv.setX(point.x);
                iv.setY(point.y);

            }
        });
    }

    private ImageView getClickDot(View v, int size) {
        ImageView dotIv = new ImageView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(size, size);
        dotIv.setX(v.getX() + v.getWidth() / 2 - size / 2);
        dotIv.setY(v.getY() + v.getHeight() / 2 - size / 2);
        dotIv.setImageResource(R.mipmap.ic_launcher);
        return dotIv;
    }

}
