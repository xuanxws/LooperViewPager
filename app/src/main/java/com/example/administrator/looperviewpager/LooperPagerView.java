package com.example.administrator.looperviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

/**
 * Created by xuws on 2017/4/26.
 */
public class LooperPagerView extends LinearLayout {

    LoopViewPager loopViewPager;
    private FrameLayout mLayout;
    private LoopIndicator loopIndicator;
    private int mTimes;//循环时间
    MainHandler mainHandler;
    public LooperPagerView(Context context) {
        this(context, null);
    }

    public LooperPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        mainHandler = new MainHandler(this);
        mLayout = new FrameLayout(getContext());
        setOrientation(VERTICAL);
        initViewPager();
        initIndicator();
        addView(mLayout);
    }

    private void initIndicator(){
        loopIndicator = new LoopIndicator(getContext());
        mLayout.addView(loopIndicator);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) loopIndicator.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL;
        layoutParams.bottomMargin = 2;
    }

    private void initViewPager(){
        loopViewPager = new LoopViewPager(getContext());
        loopViewPager.addPagerListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                loopIndicator.setSeclectedIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        loopViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        stopLooping();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        stopLooping();
                        break;
                    case MotionEvent.ACTION_UP:
                        startLooping(mTimes);
                        break;
                }
                return false;
            }
        });
        mLayout.addView(loopViewPager);
    }

    /**
     * 数据改变时通知改变调用方法
     */
    public void notifyChanged() {
        if (null != loopIndicator && null != loopViewPager) {
            loopIndicator.setOvalNums(loopViewPager.getCount());
            loopViewPager.setCurrentItem(1,false);
        }
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        if (null != loopViewPager) {
            loopViewPager.setAdapter(pagerAdapter);
            if (null != loopIndicator) {
                loopIndicator.setOvalNums(loopViewPager.getCount());
            }
            loopViewPager.setCurrentItem(1);
        }
    }

    /**
     * 设置宽高
     * @param width
     * @param height
     */
    public void setBounds(int width,int height) {
        if (null != loopViewPager) {
          FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) loopViewPager.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            loopViewPager.setLayoutParams(layoutParams);
        }
    }
    /**
     * 停止循环
     */
    public void stopLooping(){
        mainHandler.removeMessages(0);
    }
    /**
     * 开始循环
     * @param time
     */
    public void startLooping(int time) {
        if (time > 0) {
            mTimes = time;
        }
         mainHandler.removeMessages(0);
        if (null != loopViewPager && mTimes != 0 && loopViewPager.getCount() > 1) {
          mainHandler.sendEmptyMessageDelayed(0,mTimes);
        }
    }
    private static class MainHandler extends Handler {

        WeakReference<LooperPagerView> mRef;
        public MainHandler(LooperPagerView view){
            super(Looper.getMainLooper());
           mRef = new WeakReference<LooperPagerView>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LooperPagerView view = mRef.get();
            if (null != view) {
                LoopViewPager pager = view.loopViewPager;
                switch (msg.what){
                    case 0:
                        if (null != pager) {
                           pager.setCurrentItem(pager.getCurrentItem() + 1);
                           view.startLooping(-1);
                        }
                        break;
                }
            }
        }
    }

}
