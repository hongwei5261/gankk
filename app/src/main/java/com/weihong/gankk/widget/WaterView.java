package com.weihong.gankk.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by wei.hong on 2017/8/31.
 */

public class WaterView extends View {

    int len;
    float[] firstWaterLines;
    float[] secondWaterLines;
    Paint paintWater = new Paint();

    public WaterView(Context context) {
        super(context);
    }

    public WaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paintWater.setAntiAlias(true);
        paintWater.setAlpha(150);

        valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setDuration(36000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
    }

    public WaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    ValueAnimator valueAnimator;
    int move;

    public void start() {
        if (valueAnimator != null) {
            valueAnimator.start();
        }
    }

    public void stop() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initWaterLines();
        clipCircle(canvas);
        drawWater(canvas);
    }

    private void initWaterLines() {
        // y = Asin(wx+b)+h ，这个公式里：w影响周期，A影响振幅，h影响y位置，b为初相；
        float mCycleFactorW = (float) (2 * Math.PI / len);
        for (int i = 0; i < len; i++) {
            firstWaterLines[i] = (float) (15 * Math.sin(0.02 * i + move));
            secondWaterLines[i] = (float) (10 * Math.sin(0.02 * i + 10 + move));
        }
    }

    private void clipCircle(Canvas canvas) {
        Path path = new Path();
        path.reset();
        path.addCircle(len / 2, len / 2, len / 2, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.REPLACE);
    }

    private void drawWater(Canvas canvas) {
        canvas.save();

        canvas.translate(0, len / 2);
        for (int i = 0; i < len; i++) {
            canvas.drawLine(i, firstWaterLines[i], i, len / 2, paintWater);
            canvas.drawLine(i, secondWaterLines[i], i, len / 2, paintWater);
        }

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        len = Math.min(w, h);
        firstWaterLines = new float[len];
        secondWaterLines = new float[len];
        setMeasuredDimension(len, len);
    }
}
