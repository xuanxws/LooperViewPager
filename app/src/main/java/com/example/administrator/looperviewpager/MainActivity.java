package com.example.administrator.looperviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.math.MathContext;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LooperPagerView looperPagerView;
    private ArrayList<Integer> imageList = new ArrayList<>();
    LooperPagerAdapter looperPagerAdapter;
    MainActivity mContext;
    private int screenWidth;//屏幕宽度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        looperPagerView = (LooperPagerView) findViewById(R.id.looperPagerView);
        initView();
    }

    private void getScreenWidthDip(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    private void initView(){
        getScreenWidthDip();
        imageList.add(R.drawable.banner_1);
        imageList.add(R.drawable.banner_2);
        imageList.add(R.drawable.banner_3);
        imageList.add(R.drawable.banner_4);

        looperPagerAdapter = new LooperPagerAdapter(mContext);
        looperPagerView.setAdapter(looperPagerAdapter);
        looperPagerAdapter = new LooperPagerAdapter(mContext);
        looperPagerView.setBackgroundColor(Color.BLUE);
        looperPagerView.setBounds(screenWidth, screenWidth / 3);
        looperPagerView.setAdapter(looperPagerAdapter);
        looperPagerAdapter.setDatas(imageList);
        looperPagerAdapter.notifyDataSetChanged();
        looperPagerView.notifyChanged();
        looperPagerView.startLooping(3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
