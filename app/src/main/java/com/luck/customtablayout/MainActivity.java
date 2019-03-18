package com.luck.customtablayout;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] mTitle = new String[]{"页码1", "页码2", "页码3"};
    private ArrayList<Fragment> mFragments;
    private SimpleFragmentPagerAdapter mVpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.vp);
        setViewPager();
    }


    private void setViewPager() {
        mFragments = new ArrayList<>();
        for (String aMTitle : mTitle) {
            TestFragment fragment = TestFragment.newInstance(aMTitle);
            mFragments.add(fragment);
        }
        mVpAdapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager(), mFragments, mTitle);
        mViewPager.setAdapter(mVpAdapter);//给ViewPager设置适配器
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.removeAllTabs();
        mVpAdapter.notifyDataSetChanged();
        mViewPager.setOffscreenPageLimit(2);
        //添加自定义TabItem
        handleTabItem();
        //设置tabLayout的选中监听
        mTabLayout.addOnTabSelectedListener(new MyTabSelectedListener());
    }

    //修改tablayout的item
    private void handleTabItem() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            // 更新前,先remove原来的customView,否则Badge无法更新
            View customView = tab.getCustomView();
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(customView);
                }
            }
            View tabItemView = mVpAdapter.getTabItemView(i);
            ImageView iv = tabItemView.findViewById(R.id.iv_tab_selected);
            TextView tv = tabItemView.findViewById(R.id.tv_tab_title);
            tabItemView.setTag(i);
            tabItemView.setOnClickListener(mTabOnClickListener);
            if (mTabLayout.getSelectedTabPosition() == i) {
                iv.setVisibility(View.VISIBLE);
                tv.setTextColor(Color.parseColor("#2c2c2c"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                TextPaint tp = tv.getPaint();
                tp.setFakeBoldText(true);
            } else {
                iv.setVisibility(View.GONE);
                tv.setTextColor(Color.parseColor("#565656"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                TextPaint tp = tv.getPaint();
                tp.setFakeBoldText(false);
            }
            // 更新CustomView
            tab.setCustomView(tabItemView);
        }
    }

    //tablayout的选中监听
    class MyTabSelectedListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            View customView = tab.getCustomView();
            if (customView == null) {
                return;
            }
            customView.findViewById(R.id.iv_tab_selected).setVisibility(View.VISIBLE);
            TextView textView = customView.findViewById(R.id.tv_tab_title);

            textView.setTextColor(Color.parseColor("#2c2c2c"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            TextPaint tp = textView.getPaint();
            tp.setFakeBoldText(true);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            View customView = tab.getCustomView();
            TextView textView = customView.findViewById(R.id.tv_tab_title);
            textView.setTextColor(Color.parseColor("#565656"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            customView.findViewById(R.id.iv_tab_selected).setVisibility(View.GONE);
            TextPaint tp = textView.getPaint();
            tp.setFakeBoldText(false);

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    //tabLayout的点击监听
    private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            TabLayout.Tab tab = mTabLayout.getTabAt(pos);
            if (tab != null) {
                tab.select();
            }
        }
    };
}
