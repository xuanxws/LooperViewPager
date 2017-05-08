package com.example.administrator.looperviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by xuws on 2017/4/26.
 */
public class LoopViewPager extends ViewPager {

    private int prePosition;
    private ArrayList<OnPageChangeListener> pageChangeListeners = new ArrayList<>();
    public LoopViewPager(Context context) {
        this(context,null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
        addOnPageChangeListener(mOnPagerChangeListener);
    }

    OnPageChangeListener mOnPagerChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (null != getAdapter() && getAdapter().getCount() > 1) {
               int count = getAdapter().getCount();
                if (position == 1 && prePosition == count - 1) {
                   prePosition = position;
                    return;
                }else if (position == count - 2 && prePosition == 0) {
                    prePosition = position;
                    return;
                }
                callBackIndicatotPosition(toRealPosition(position));
                prePosition = position;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            PagerAdapter adapter = getAdapter();
            if (adapter != null && adapter.getCount() > 1) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int count = adapter.getCount();
                    if (getCurrentItem() == 0) {
                        setCurrentItem(count - 2,false);
                    } else if (getCurrentItem() == count - 1) {
                        setCurrentItem(1,false);
                    }
                }
            }
        }
    };

    /**
     * 计算出指示器正确的显示位置
     */
    private int toRealPosition(int position) {
        if (getAdapter() != null && getAdapter().getCount() > 1) {
            int count = getAdapter().getCount();
            if (position == count - 1) {
               return 0;
            }else if (position == 0) {
                return count - 3;
            }else{
                return position - 1;
            }
        }
        return 0;
    }

    public void addPagerListener(OnPageChangeListener onPageChangeListener) {
        if (null != onPageChangeListener) {
            pageChangeListeners.add(onPageChangeListener);
        }
    }
    /**
     * 用于回调指示器的位置
     * @param position
     */
    private void callBackIndicatotPosition(int position) {
        if (null != pageChangeListeners && position >= 0) {
            for (OnPageChangeListener temp : pageChangeListeners) {
                temp.onPageSelected(position);
            }
        }
    }

    /**
     * 获取正真的数量
     */
    public int getCount() {
        if (null != getAdapter()) {
            int count = getAdapter().getCount();
            if (count > 1) {
                return count - 2;
            } else {
               return count;
            }
        }
        return 0;
    }


}
