package example.com.application;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;

/**
 * Created by yinchuan on 2017/2/13.
 */

public class PassView extends EditText {

    private Paint mPaint;
    private Paint mPaintContent;
    private Paint mPaintArc;

    private int maxLengthSize;
    private int mPadding = 1;
    private int roundRadius;

    private int curLength = 0;
    private float circleRadius;
    private float circleScale = 0;
    private boolean addCircle = true;
    private PassAnimation passAnimation;

    private IPassTextCallBack passTextCallBack;

    public PassView(Context context) {
        super(context);
        initPaint();
    }

    public PassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public void setPassTextCallBack(IPassTextCallBack passTextCallBack) {
        this.passTextCallBack = passTextCallBack;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaintArc = new Paint();
        mPaintArc.setAntiAlias(true);
        mPaintArc.setStyle(Paint.Style.FILL);

        mPaintContent = new Paint();
        mPaintContent.setAntiAlias(true);
        mPaintContent.setColor(Color.WHITE);
        mPaintContent.setStyle(Paint.Style.FILL);

        maxLengthSize = getMaxLengthSize();
        roundRadius = dp2px(6);
        circleRadius = dp2px(10);
        passAnimation = new PassAnimation();
        passAnimation.setDuration(200);
        passAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (curLength == 6 && passTextCallBack != null) {
                    passTextCallBack.completeInput();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private int dp2px(int i) {
        float scale = getContext().getResources().getDisplayMetrics().density;

        return (int) (i * scale + 0.5);
    }

    private int getMaxLengthSize() {
        int length = 0;
        InputFilter[] inputFilters = getFilters();
        for (InputFilter inputFilter : inputFilters) {

            if (inputFilter instanceof InputFilter.LengthFilter) {
                InputFilter.LengthFilter lengthFilter = (InputFilter.LengthFilter) inputFilter;
                length = lengthFilter.getMax();
                Log.e("Instance_Length", length + "");
            }

//            Class<?> c = inputFilter.getClass();
//            if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
//                Field[] f = c.getDeclaredFields();
//                for (Field field : f) {
//                    if (field.getName().equals("mMax")) {
//                        field.setAccessible(true);
//                        try {
//                            length = field.getInt(inputFilter);
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
        }
        return length;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //白色背景
        RectF rectF = new RectF(mPadding, mPadding, getMeasuredWidth() - mPadding, getMeasuredHeight() - mPadding);
        canvas.drawRoundRect(rectF, roundRadius, roundRadius, mPaintContent);
        //线框
        canvas.drawRoundRect(rectF, roundRadius, roundRadius, mPaint);

        float cy = getMeasuredHeight() / 2;

        float half = getMeasuredWidth() / maxLengthSize / 2;
        mPaint.setStrokeWidth(0.5f);

        for (int i=1;i<maxLengthSize;i++) {
            float x = getMeasuredWidth() / maxLengthSize * i;
            canvas.drawLine(x, 0, x, getMeasuredHeight(), mPaint);
        }
        if (addCircle) {
            for (int i = 0; i < curLength - 1; i++) {
                float x = getMeasuredWidth() / maxLengthSize * i + half;
                canvas.drawCircle(x, cy, circleRadius, mPaintArc);
            }
            canvas.drawCircle(
                    getMeasuredWidth() / maxLengthSize * (curLength - 1) + half,
                    cy, circleRadius * circleScale, mPaintArc);
        } else {
            for (int i = 0; i < curLength; i++) {
                float x = getMeasuredWidth() / maxLengthSize * i + half;
                canvas.drawCircle(x, cy, circleRadius, mPaintArc);
            }
            canvas.drawCircle(
                    getMeasuredWidth() / maxLengthSize * (curLength) + half,
                    cy, circleRadius * (1 - circleScale), mPaintArc);
        }



    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        int length = getText().length();
        if (length > curLength) {
            addCircle = true;
        } else if (length < curLength) {
            addCircle = false;
        }

        clearAnimation();
        if (passAnimation != null) {
            startAnimation(passAnimation);
        }
        curLength = length;

    }

    private class PassAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            circleScale = interpolatedTime;
            postInvalidate();
        }
    }

    interface IPassTextCallBack {
        void completeInput();
    }
}
