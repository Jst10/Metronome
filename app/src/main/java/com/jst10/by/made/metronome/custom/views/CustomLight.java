package com.jst10.by.made.metronome.custom.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jst10.by.made.metronome.R;

/**
 * Created by jst10 on 31.1.2016.
 */
public class CustomLight extends View {
    private static final int TYPE_MAIN = 0;
    private static final int TYPE_MINOR = 1;

    private int type;
    private int borderColor;
    private int activatedColor;
    private int deactivatedColor;

    private Paint activatedPaint;
    private Paint deactivatedPaint;

    public CustomLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomLight, 0, 0);
        type = TYPE_MAIN;
        try {
            type = a.getInteger(R.styleable.CustomLight_type, TYPE_MAIN);
        } finally {
            initColors();
            a.recycle();
        }
    }

    private void initColors() {
        if (type == TYPE_MAIN) {
            this.borderColor = getResources().getColor(R.color.light_border_1);
            this.deactivatedColor = getResources().getColor(R.color.light_deactivated_1);
            this.activatedColor = getResources().getColor(R.color.light_activated_1);
        } else if (type == TYPE_MINOR) {
            this.borderColor = getResources().getColor(R.color.light_border_2);
            this.deactivatedColor = getResources().getColor(R.color.light_deactivated_2);
            this.activatedColor = getResources().getColor(R.color.light_activated_2);
        } else {
            throw new IllegalAccessError();
        }

        if (activatedPaint == null || deactivatedPaint == null) {
            activatedPaint = new Paint();
            activatedPaint.setStyle(Paint.Style.FILL);
            activatedPaint.setAntiAlias(true);
            activatedPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

            deactivatedPaint = new Paint();
            deactivatedPaint.setStyle(Paint.Style.FILL);
            deactivatedPaint.setAntiAlias(true);
            deactivatedPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        }

        activatedPaint.setColor(this.activatedColor);
        deactivatedPaint.setColor(this.deactivatedColor);


    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint;
        if (isActivated()) {
            paint = activatedPaint;
        } else {
            paint = deactivatedPaint;
        }

        int x = getWidth();
        int y = getHeight();
        canvas.drawCircle(x/2, y/2, x/2, paint);
    }

    public void setType(int type) {
        this.type = type;
        initColors();
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setActivatedColor(int activatedColor) {
        this.activatedColor = activatedColor;
    }

    public void setDeactivatedColor(int deactivatedColor) {
        this.deactivatedColor = deactivatedColor;
    }
}
