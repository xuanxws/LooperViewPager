# LooperViewPager

  一个简单的无限Banner轮播
  
## 效果图展示
 ![Mou icon](https://github.com/xuanxws/LooperViewPager/blob/master/app/src/main/res/mipmap-hdpi/ic_launcher.png)

## 使用方法

   looperPagerAdapter = new LooperPagerAdapter(mContext);
   
   looperPagerView.setBounds(screenWidth, screenWidth / 3);
   
   looperPagerView.setAdapter(looperPagerAdapter);
   
   looperPagerAdapter.setDatas(imageList);
   
   looperPagerAdapter.notifyDataSetChanged();
   
   looperPagerView.notifyChanged();
   
   looperPagerView.startLooping(3000);
   
## 使用点注意

 * 1 你可以根据自己的需要重新写一个indicator（指示器），只需要在looperPagerView里面做些相应的改动即可。
 
 * 2 可以自由地设置广告图片宽高的比例。
 
   looperPagerView.setBounds(screenWidth, screenWidth / 3);
   
 * 3 已经把指示器、图片轮播、viewpager等逻辑封装在了looperPagerView里面，可以直接在xml里面使用
 
       <com.example.administrator.looperviewpager.LooperPagerView
     
        android:id="@+id/looperPagerView"
        
        android:layout_width="match_parent"
        
        android:layout_marginTop="20dp"
        
        android:layout_height="wrap_content"
        
        />
        
 ## 关键代码
  
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
