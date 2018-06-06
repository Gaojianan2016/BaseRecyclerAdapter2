package com.gjn.baserecycleradapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * SideBar
 * Author: gjn.
 * Time: 2018/2/27.
 */

public class SideBar extends View {

    public static String[] py = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    private int choose = -1;
    private Paint paint;

    private int NormalColor = Color.BLACK;
    private int SelectColor = Color.WHITE;

    private TextView showView;
    private int dealyTime = 500;

    private ChooseLetterListener onChooseLetterListener;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(30);
    }

    public SideBar setNormalColor(int normalColor) {
        NormalColor = normalColor;
        invalidate();
        return this;
    }

    public SideBar setSelectColor(int selectColor) {
        SelectColor = selectColor;
        invalidate();
        return this;
    }

    public SideBar setShowView(TextView showView) {
        this.showView = showView;
        showView.setVisibility(GONE);
        return this;
    }

    public SideBar setDealyTime(int dealyTime) {
        this.dealyTime = dealyTime;
        return this;
    }

    public SideBar setOnChooseLetterListener(ChooseLetterListener onChooseLetterListener) {
        this.onChooseLetterListener = onChooseLetterListener;
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();
        int size = h / py.length;

        for (int i = 0; i < py.length; i++) {
            if (i == choose){
                paint.setColor(SelectColor);
            }else{
                paint.setColor(NormalColor);
            }

            float xPos = w / 2 - paint.measureText(py[i]) / 2;
            float yPos = size * i + size;

            canvas.drawText(py[i],xPos,yPos,paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();
        int oldChoose = choose;
        int newChoose = (int) (y / getHeight() * py.length);

        if (newChoose >= py.length) {
            newChoose = py.length - 1;
        }
        if (newChoose < 0) {
            newChoose = 0;
        }

        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                choose = -1;
                invalidate();
                if (showView != null) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showView.setVisibility(GONE);
                        }
                    },dealyTime);
                }
                break;
            default:
                if (newChoose != oldChoose){
                    if (onChooseLetterListener != null) {
                        onChooseLetterListener.OnChooseLetterListener(py[newChoose]);
                    }
                    if (showView != null) {
                        showView.setText(py[newChoose]);
                        showView.setVisibility(VISIBLE);
                    }
                    choose = newChoose;
                    invalidate();
                }
                break;
        }
        return true;
    }

    public interface ChooseLetterListener {
        void OnChooseLetterListener(String py);
    }
}
