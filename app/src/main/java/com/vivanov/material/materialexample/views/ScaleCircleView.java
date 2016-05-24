package com.vivanov.material.materialexample.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ScaleCircleView extends View {

    private static final long DURATION = 150;

    private long startTime = 0;
    private int size = 0;
    private float coords[];
    private Paint paint = new Paint();
    private Runnable after;

    public void startAnimation(int sizeParent, float[] coords, int color, Runnable after) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(color);
        this.after = after;
        this.size = sizeParent;
        this.coords = coords;
        startTime = System.currentTimeMillis();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (startTime == 0) {
            return;
        }

        long spendTime = System.currentTimeMillis() - startTime;
        if (spendTime >= DURATION) {
            spendTime = DURATION;
            after.run();
        } else {
            invalidate();
        }
        float rad = (float) size * (float) (spendTime) / (float) DURATION;
        canvas.drawCircle(coords[0], coords[1], rad, paint);
    }

    public ScaleCircleView(Context context) {
        super(context);
    }

    public ScaleCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
