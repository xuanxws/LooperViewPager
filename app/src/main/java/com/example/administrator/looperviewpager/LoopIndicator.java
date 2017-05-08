package com.example.administrator.looperviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by xuws on 2017/4/26.
 */
public class LoopIndicator extends View {

    private int unselectedColor = Color.RED;

    private int selectedColor = Color.BLACK;

    private int ovalRadius;//小圆半径

    private int ovalWidth;//圆角矩形宽度

    private int ovalNums;//圆的个数

    private int ovalDivither;//圆之间的间距
    
    private int selectedIndex;//被选中的下标

    Paint paint;

    RectF rect;

    public LoopIndicator(Context context) {
        this(context, null);
    }

    public LoopIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.LoopIndicator);
        ovalRadius = typedArray.getDimensionPixelSize(R.styleable.LoopIndicator_ovalradius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        ovalWidth = typedArray.getDimensionPixelSize(R.styleable.LoopIndicator_ovalwidth,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
        ovalNums = typedArray.getInteger(R.styleable.LoopIndicator_ovalnums, 3);
        ovalDivither = typedArray.getDimensionPixelSize(R.styleable.LoopIndicator_ovaldivther, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        typedArray.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        rect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ovalNums > 1) {
            int count = ovalNums - 1;
            int width = count * ovalRadius * 2 + ovalWidth + count * ovalDivither;
            int height = count * ovalRadius * 2;
            setMeasuredDimension(width, height);
        } else {
            setMeasuredDimension(0,0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (ovalNums > 1) {
            int mLeft = 0;
            for (int i = 0; i < ovalNums; i++) {
                if (i != selectedIndex) {
                    paint.setColor(unselectedColor);
                    canvas.drawCircle(mLeft + ovalRadius, ovalRadius, ovalRadius, paint);
                    mLeft = mLeft + 2 * ovalRadius;
                } else {
                    paint.setColor(selectedColor);
                    rect.set(mLeft,0,mLeft + ovalWidth,ovalRadius*2);
                    canvas.drawRoundRect(rect,3,3,paint);
                    mLeft = mLeft + ovalWidth;
                }
                mLeft = mLeft + ovalDivither;
            }
        }
    }

    public void setOvalNums(int nums){
      ovalNums = nums;
        requestLayout();
        invalidate();
    }

    public void setSeclectedIndex(int seclectedIndex){
        this.selectedIndex = seclectedIndex;
        invalidate();
    }
}
