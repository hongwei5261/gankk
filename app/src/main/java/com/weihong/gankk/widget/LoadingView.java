package com.weihong.gankk.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.weihong.gankk.R;
import com.weihong.gankk.util.CheckUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wei.hong on 2017/8/28.
 * <p>
 * 自定义数据加载View
 */

public class LoadingView extends View {
    // view的大小
    private float mCircleSize = 250;
    // 四周小的线条的长度
    private float mLineLength = 10;
    // 360度
    private static final int CIRCLE_ANGLE = 360;
    private static final int RGB = 255;
    // 每条线相隔的角度
    private int mLineAngle = 10;
    // 加载文字大小
    private float mTextSize = 10;
    // 加载文字颜色
    private int mTextColor = 10;
    // 加载文字
    private String mTextStr;
    Paint circlePaint = new Paint();
    Paint textPaint = new Paint();
    Rect rectText;
    // 是否正在运行
    boolean isRunning = false;
    Timer timer = new Timer();
    int baseLine;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        mCircleSize = typedArray.getDimension(R.styleable.LoadingView_circle_size, getResources().getDimension(R.dimen.circle_size));
        mLineLength = typedArray.getDimension(R.styleable.LoadingView_circle_line_lenght, getResources().getDimension(R.dimen.circle_line_length));
        mLineAngle = (int) typedArray.getDimension(R.styleable.LoadingView_circle_line_lenght, 10);
        mTextSize = typedArray.getDimension(R.styleable.LoadingView_load_text_size, getResources().getDimension(R.dimen.load_text_size));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTextColor = typedArray.getColor(R.styleable.LoadingView_load_text_color, getResources().getColor(R.color.load_text_color, getContext().getTheme()));
        } else {
            mTextColor = typedArray.getColor(R.styleable.LoadingView_load_text_color, getResources().getColor(R.color.load_text_color));
        }
        mTextStr = CheckUtil.isEmpty(typedArray.getString(R.styleable.LoadingView_load_text)) ? getResources().getString(R.string.load_text) : typedArray.getString(R.styleable.LoadingView_load_text);

        circlePaint.setAntiAlias(true);
        circlePaint.setColor(typedArray.getColor(R.styleable.LoadingView_circle_color, Color.BLUE));
        // 设置画笔的宽度
        circlePaint.setStrokeWidth(3);
        textPaint.setAntiAlias(true);
        rectText = new Rect(0, 0, (int) mCircleSize, (int) mCircleSize);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        baseLine = (int) ((rectText.bottom + rectText.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2);
        typedArray.recycle();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void start() {
        if (getVisibility() == View.VISIBLE) {
            if (isRunning) {
                return;
            }
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isRunning = true;
                    postInvalidate();
                }
            }, 300, 50);
        } else {
            if (isRunning) {
                isRunning = false;
                timer.cancel();
            }
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLines(canvas);
        drawText(canvas);
        if (!isRunning) {
            start();
        }
    }

    int target = 0;
    boolean changed = false;

    private void drawLines(Canvas canvas) {
        canvas.save();
        canvas.translate(mCircleSize / 2, mCircleSize / 2);
        for (int i = target; i < CIRCLE_ANGLE / mLineAngle + target; i++) {
            circlePaint.setAlpha(RGB - RGB * (i % (CIRCLE_ANGLE / mLineAngle)) * mLineAngle / CIRCLE_ANGLE);
            canvas.drawLine(mCircleSize / 2 - mLineLength, 0, mCircleSize / 2, 0, circlePaint);
            canvas.rotate(mLineAngle);
        }
        target++;
        if (target >= CIRCLE_ANGLE / mLineAngle) {
            target = 0;
            changed = !changed;
        }
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        textPaint.setStrokeWidth(3);
        textPaint.setTextSize(mTextSize);
        if (changed) {
            textPaint.setARGB(RGB, (RGB * target * mLineAngle) / CIRCLE_ANGLE, RGB - RGB * target * mLineAngle / CIRCLE_ANGLE, 0);
        } else {
            textPaint.setARGB(RGB, RGB - RGB * target * mLineAngle / CIRCLE_ANGLE, RGB * target * mLineAngle / CIRCLE_ANGLE, 0);
        }

        textPaint.getTextBounds(mTextStr, 0, mTextStr.length(), rectText);
        float y = mCircleSize / 2f + rectText.height() / 2f - rectText.bottom;
        // 设置水平居中
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mTextStr, mCircleSize / 2, y, textPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        setMeasuredDimension((int) mCircleSize, (int) mCircleSize);
    }

    private int measureWidth(int measureSpec) {
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        float measureWidth = mCircleSize;
        if (measureMode == MeasureSpec.AT_MOST) {
            measureWidth = Math.min(mCircleSize, measureSize);
        } else if (measureMode == MeasureSpec.EXACTLY) {
            measureWidth = Math.min(mCircleSize, measureSize);
        }
        return (int) measureWidth;
    }

    private int measureHeight(int measureSpec) {
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        float measureHeight = mCircleSize;
        if (measureMode == MeasureSpec.AT_MOST) {
            measureHeight = Math.min(mCircleSize, measureSize);
        } else if (measureMode == MeasureSpec.EXACTLY) {
            measureHeight = Math.min(mCircleSize, measureSize);
        }
        return (int) measureHeight;
    }
}
