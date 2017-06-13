package com.graduate.gaomingzhu.planequery.Adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by GaoMingzhu on 2017/4/19.
 * email:2713940331@qq.com
 */

public class MyPageAdapter extends PagerAdapter {
    private ArrayList<View> pageview;

    public MyPageAdapter(ArrayList<View> p){
        this.pageview=p;
    }
    @Override
    //获取当前窗体界面数
    public int getCount() {
        return pageview.size();
    }

    @Override
    //判断是否由对象生成界面
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    //使从ViewGroup中移出当前View
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(pageview.get(arg1));
    }

    //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
    public Object instantiateItem(View arg0, int arg1){
        ((ViewPager)arg0).addView(pageview.get(arg1));
        return pageview.get(arg1);
    }

}
