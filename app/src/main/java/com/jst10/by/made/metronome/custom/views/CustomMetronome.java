package com.jst10.by.made.metronome.custom.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jst10.by.made.metronome.R;

/**
 * Created by jst10 on 9.2.2016.
 */
public class CustomMetronome extends View {
    private static final int DEFAULT_MAX_PROGRESS = 100;
    private static final int DEFAULT_BORDER_WIDTH = 0;


    private int mainColor;
    private int tickColor;

    private int progress;
    private int maxProgress;

    private Paint mainPaint;
    private Paint tickPaint;

    public CustomMetronome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomMetronome(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attributeSet) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomProgressBar, 0, 0);

        try {
            Resources resources = getResources();
            maxProgress = a.getInt(R.styleable.CustomMetronome_maxMetronomeProgress, DEFAULT_MAX_PROGRESS);

            mainColor = a.getColor(R.styleable.CustomMetronome_metronomeMainColor, resources.getColor(R.color.metronome_main_color));
            tickColor = a.getColor(R.styleable.CustomMetronome_metronomeTickColor, resources.getColor(R.color.metronome_tick_color));

        } finally {
            initPaints();
            a.recycle();
        }
    }

    private void initPaints() {
        if (mainPaint == null || tickPaint == null) {
            mainPaint = new Paint();
            mainPaint.setStyle(Paint.Style.FILL);
            mainPaint.setAntiAlias(true);
            mainPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

            tickPaint = new Paint();
            tickPaint.setStyle(Paint.Style.FILL);
            tickPaint.setAntiAlias(true);
            tickPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        }
        tickPaint.setStrokeWidth(10);
        mainPaint.setColor(this.mainColor);
        tickPaint.setColor(this.tickColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int width = getWidth();
        int height = getHeight();

        int tickLength = height;
        int canvasMiddle = width / 2;

        double startAngle = 0;
        double endAngle = 0;

        if (canvasMiddle < height) {
            startAngle = Math.acos(canvasMiddle / (double) height);
        } else {
            startAngle = 0.5;
        }
        endAngle = Math.PI - startAngle;


        double currentAngle = endAngle - ((progress / (double) maxProgress) * (endAngle - startAngle));

        int x = (int) (canvasMiddle + tickLength * Math.cos(currentAngle));
        int y = (int) (height - tickLength * Math.sin(currentAngle));

        canvas.drawLine(canvasMiddle, height, x, y, tickPaint);

    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
        invalidate();
    }

    public void setTickColor(int tickColor) {
        this.tickColor = tickColor;
        invalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

}
