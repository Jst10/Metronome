package com.jst10.by.made.metronome.custom.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jst10.by.made.metronome.R;

/**
 * Created by jst10 on 8.2.2016.
 */
public class CustomProgressBar extends View {
    private static final int DEFAULT_MAX_PROGRESS = 100;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int ORIENTATION_HORIZONTAL = 0;
    private static final int ORIENTATION_VERTICAL = 1;

    private static final int DIRECTION_POSITIVE = 0;
    private static final int DIRECTION_NEGATIVE = 1;

    private int orientation;
    private int direction;
    private int progressColor;
    private int noProgressColor;
    private int borderColor;
    private int borderWidth;
    private int progress;
    private int maxProgress;

    private Paint progressPaint;
    private Paint noProgressPaint;
    private Paint borderPaint;

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attributeSet) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomProgressBar, 0, 0);
        orientation = ORIENTATION_HORIZONTAL;
        direction = DIRECTION_POSITIVE;

        try {
            Resources resources = getResources();
            orientation = a.getInt(R.styleable.CustomProgressBar_orientation, ORIENTATION_HORIZONTAL);
            direction = a.getInt(R.styleable.CustomProgressBar_direction, DIRECTION_POSITIVE);
            maxProgress = a.getInt(R.styleable.CustomProgressBar_maxProgress, DEFAULT_MAX_PROGRESS);
            borderWidth = a.getDimensionPixelSize(R.styleable.CustomProgressBar_allBorderWidth, DEFAULT_BORDER_WIDTH);

            progressColor = a.getColor(R.styleable.CustomProgressBar_progressColor, resources.getColor(R.color.progress_bar_progress));
            noProgressColor = a.getColor(R.styleable.CustomProgressBar_noProgressColor, resources.getColor(R.color.progress_bar_no_progress));
            borderColor = a.getColor(R.styleable.CustomProgressBar_borderColor, resources.getColor(R.color.progress_bar_border));

        } finally {
            initPaints();
            a.recycle();
        }
    }

    private void initPaints() {
        if (progressPaint == null || noProgressPaint == null || borderPaint == null) {
            progressPaint = new Paint();
            progressPaint.setStyle(Paint.Style.FILL);
            progressPaint.setAntiAlias(true);
            progressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

            noProgressPaint = new Paint();
            noProgressPaint.setStyle(Paint.Style.FILL);
            noProgressPaint.setAntiAlias(true);
            noProgressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

            borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setAntiAlias(true);
            borderPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            borderPaint.setStrokeWidth(borderWidth);
        }

        progressPaint.setColor(this.progressColor);
        noProgressPaint.setColor(this.noProgressColor);
        borderPaint.setColor(this.borderColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int width = getWidth();
        int height = getHeight();

        if (orientation == ORIENTATION_HORIZONTAL) {
            int progressSize=(int)((progress/(double)maxProgress)*width);
            int noProgressSize=width-progressSize;

            if (direction == DIRECTION_NEGATIVE) {
                canvas.drawRect(0,0,noProgressSize,height,noProgressPaint);
                canvas.drawRect(noProgressSize,0,width,height,progressPaint);
            } else if (direction == DIRECTION_POSITIVE) {
                canvas.drawRect(0,0,progressSize,height,progressPaint);
                canvas.drawRect(progressSize,0,width,height,noProgressPaint);
            } else {
                throw new IllegalAccessError();
            }
        } else if (orientation == ORIENTATION_VERTICAL) {
            int progressSize=(int)((progress/(double)maxProgress)*height);
            int noProgressSize=height-progressSize;

            if (direction == DIRECTION_NEGATIVE) {
                canvas.drawRect(0,0,width,progressSize,progressPaint);
                canvas.drawRect(0,progressSize,width,height,noProgressPaint);
            } else if (direction == DIRECTION_POSITIVE) {
                canvas.drawRect(0,0,width,noProgressSize,noProgressPaint);
                canvas.drawRect(0,noProgressSize,width,height,progressPaint);
            } else {
                throw new IllegalAccessError();
            }
        } else {
            throw new IllegalAccessError();
        }

        if(borderWidth>0) {
            canvas.drawRect(0, 0, width, height, borderPaint);
        }


    }

    public void reverseDirection(boolean resetProgress) {
        if (direction == DIRECTION_NEGATIVE) {
            this.direction = DIRECTION_POSITIVE;
        } else if (direction == DIRECTION_POSITIVE) {
            this.direction = DIRECTION_NEGATIVE;
        } else {
            throw new IllegalAccessError();
        }

        if (resetProgress) {
            progress = 0;
        }
        invalidate();
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        invalidate();
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        initPaints();
        invalidate();
    }

    public void setNoProgressColor(int noProgressColor) {
        this.noProgressColor = noProgressColor;
        initPaints();
        invalidate();
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        initPaints();
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        initPaints();
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

    public void setDirection(int direction) {
        this.direction = direction;
        invalidate();
    }
}
