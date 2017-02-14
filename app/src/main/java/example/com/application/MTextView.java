package example.com.application;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by yinchuan on 2016/11/12.
 */

public class MTextView extends TextView {

    private int mViewWidth = 0;
    private int mTranslate = 0;
    private Matrix mGradientMatrix;
    private LinearGradient mLinearGradient;

    public MTextView(Context context) {
        super(context);
    }

    public MTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("MTextView", "onMeasure");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("MTextView", "onSizeChanged");
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                Paint mPaint = getPaint();
                mLinearGradient = new LinearGradient(
                        0,
                        0,
                        mViewWidth,
                        0,
                        new int[]{
                                Color.BLUE, 0xffffffff,
                                Color.BLUE
                        },
                        null,
                        Shader.TileMode.CLAMP
                );
                mPaint.setShader(mLinearGradient);
                mGradientMatrix= new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("MTextView", "onDraw");
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }


//        Paint mPaint1 = new Paint();
//        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
//        mPaint1.setStyle(Paint.Style.FILL);
//        Paint mPaint2 = new Paint();
//        mPaint2.setColor(Color.YELLOW);
//        mPaint2.setStyle(Paint.Style.FILL);
//
//        canvas.drawRect(
//                0,
//                0,
//                getMeasuredWidth(),
//                getMeasuredHeight(),
//                mPaint1
//        );
//
//        canvas.drawRect(
//                10,
//                10,
//                getMeasuredWidth() - 10,
//                getMeasuredHeight() - 10,
//                mPaint2
//        );
//
//        canvas.save();
//        canvas.translate(10, 0);
//        super.onDraw(canvas);
//        canvas.restore();
    }
}
