package com.weihong.gankk.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.weihong.gankk.R;

/**
 * Created by wei.hong on 2017/8/28.
 */

public class WeatherView extends View {
    // view的大小
    private int HEIGHT = 600;
    private int WIDTH = 600;
    // 360度
    private static final int CIRCLE_ANGLE = 360;
    // 当前绘制到的角度
    int currentAngle = 0;
    Bitmap mSunBitmap;
    Bitmap mSunBitmap2;
    ValueAnimator valueAnimator;

    public WeatherView(Context context) {
        super(context);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mSunBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sun);
        mSunBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.sun);
        setBackgroundColor(Color.GRAY);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void start() {
        valueAnimator = ValueAnimator.ofInt(0, CIRCLE_ANGLE);
        valueAnimator.setDuration(4000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSun(canvas);
    }

    Matrix matrix = new Matrix();
    Matrix matrix2 = new Matrix();

    private void drawSun(Canvas canvas) {
        matrix.reset();
        matrix.postTranslate((WIDTH - mSunBitmap.getWidth()) / 2, (HEIGHT - mSunBitmap.getHeight()) / 2);
        matrix.postRotate(currentAngle, WIDTH / 2, HEIGHT / 2);

        matrix2.reset();
        int temp = CIRCLE_ANGLE * mSunBitmap2.getWidth() / (mSunBitmap2.getWidth() + HEIGHT);
        if (temp > currentAngle) {
            matrix2.postTranslate(-(mSunBitmap2.getWidth() - mSunBitmap2.getWidth() * currentAngle / temp), HEIGHT * 0.75f);
        } else {
            matrix2.postTranslate(WIDTH * (currentAngle - temp) / (CIRCLE_ANGLE - temp), HEIGHT * 0.75f);
        }
        canvas.drawBitmap(mSunBitmap, matrix, null);
        canvas.drawBitmap(mSunBitmap2, matrix2, null);
        Log.d("weather", "currentAngle:" + currentAngle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        HEIGHT = heightMeasureSize;
        WIDTH = widthMeasureSize;
        setMeasuredDimension(WIDTH, HEIGHT);
    }

    private int measureWidth(int measureSpec) {
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        int measureWidth = HEIGHT;
        if (measureMode == MeasureSpec.AT_MOST) {
            measureWidth = HEIGHT;//measureSize;
        } else if (measureMode == MeasureSpec.EXACTLY) {
            measureWidth = HEIGHT;//measureSize;
        }
        return measureWidth;
    }

    private int measureHeight(int measureSpec) {
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        int measureHeight = HEIGHT;
        if (measureMode == MeasureSpec.AT_MOST) {
            measureHeight = HEIGHT;//measureSize;
        } else if (measureMode == MeasureSpec.EXACTLY) {
            measureHeight = HEIGHT;//measureSize;
        }
        return measureHeight;
    }
}
