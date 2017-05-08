package com.example.administrator.looperviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuws on 2017/4/27.
 */
public class LooperPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Integer> mImageViews;
    private SparseArray<ImageView> mViews;

    public LooperPagerAdapter(Context context) {
        this.mContext = context;
        mImageViews = new ArrayList<>();
        mViews = new SparseArray<>();
    }

    @Override
    public int getCount() {
        int count = mImageViews.size();
        if (mImageViews.size() > 1) {
            return count + 2;
        } else {
            return count;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position;
        int count = mImageViews.size();
        if (count > 0) {
            if (position == 0) {
                realPosition = count - 1;
            }else if (position == getCount() - 1) {
                realPosition = 0;
            } else {
                realPosition = position - 1;
            }
        }
        int Drawable = mImageViews.get(realPosition);

        if (null == mViews.get(position)) {
            onCreatView(position,Drawable);
        }
        ImageView view = mViews.get(position);
        view.setImageResource(Drawable);
        container.removeView(view);
        container.addView(view);
        return view;
    }

    public void setDatas(ArrayList<Integer> mList) {
        mImageViews.clear();
        mImageViews.addAll(mList);
    }
    /**
     * 创建一个ImageView作为View的显示
     */
    private void onCreatView(int position,int Drawable){
       ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mViews.put(position, imageView);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (null != mViews.get(position)) {
            container.removeView(mViews.get(position));
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
