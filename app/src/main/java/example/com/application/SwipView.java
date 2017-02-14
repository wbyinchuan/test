package example.com.application;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yinchuan on 2016/11/21.
 */

public class SwipView extends View {

    private int length;
    private int mCircleXY;
    private float mRadius;

    private Paint mCirclePaint;
    private Paint mArcPaint;
    private RectF mArcRectF;

    public SwipView(Context context) {
        super(context);
    }

    public SwipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec)
        );
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(specSize, result);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(specSize, result);
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        length = getWidth();

        mArcRectF= new RectF(
                (float)(length*0.1),
                (float)(length*0.1),
                (float)(length*0.9),
                (float)(length*0.9)
        );
        mCircleXY = length / 2;
        mRadius = (float) (length * 0.5 * 0.5 / 2);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLUE);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mArcPaint= new Paint();
        mArcPaint.setColor(Color.BLUE);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制圆
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);

        //绘制弧线
        canvas.drawArc(mArcRectF, 270, 220, false, mArcPaint);
    }
}
