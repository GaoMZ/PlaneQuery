package com.graduate.gaomingzhu.planequery.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduate.gaomingzhu.planequery.Adapter.AlreadyGoAdapter;
import com.graduate.gaomingzhu.planequery.Adapter.MyPageAdapter;
import com.graduate.gaomingzhu.planequery.Adapter.NotGoAdapter;
import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.model.Flight;
import com.graduate.gaomingzhu.planequery.model.Orders;
import com.graduate.gaomingzhu.planequery.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by GaoMingzhu on 2017/4/19.
 * email:2713940331@qq.com
 */

public class QueryOrderActivity extends Activity implements View.OnClickListener {
    private List<Orders> list;

    private User user = BmobUser.getCurrentUser(User.class);
    private ViewPager viewPager;

    private ArrayList<View> pageview;

    private AlreadyGoAdapter adapter;
    private NotGoAdapter ordersAdapter;

    private ImageView btn_title_back;
    private ListView not;
    private ListView go;

    private Button notGoLayout;
    private Button goLayout;
    // 滚动条图片
    private ImageView scrollbar;
    // 滚动条初始偏移量
    private int offset = 0;
    // 当前页编号
    private int currIndex = 0;
    // 滚动条宽度
    private int bmpW;
    //一倍滚动量
    private int one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        // Toast.makeText(QueryOrderActivity.this,user.getUsername()+"***",Toast.LENGTH_SHORT).show();
        init();
        QueryOrder();
    }

    private void QueryOrder() {

    }

    private void init() {
        // orderList= (ListView) findViewById(R.id.orderList);
        //  orderList.setOnItemSelectedListener();
        btn_title_back = (ImageView) findViewById(R.id.btn_title_back);
        btn_title_back.setOnClickListener(this);
        // 查找布局文件用LayoutInflater.inflate
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.not_go_list, null);
        not = (ListView) view1.findViewById(R.id.notGolist);
        View view2 = inflater.inflate(R.layout.already_go_list, null);
        go = (ListView) view2.findViewById(R.id.alreadyGoList);

        notGoLayout = (Button) findViewById(R.id.notGoLayout);
        goLayout = (Button) findViewById(R.id.goLayout);
        scrollbar = (ImageView) findViewById(R.id.scrollbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        notGoLayout.setOnClickListener(QueryOrderActivity.this);
        goLayout.setOnClickListener(QueryOrderActivity.this);

        pageview = new ArrayList<View>();
        //添加想要切换的界面
        pageview.add(not);
        pageview.add(go);
        //数据适配器
        MyPageAdapter myPageAdapter = new MyPageAdapter(pageview);
        /*PagerAdapter myPageAdapter = new PagerAdapter(){

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0==arg1;
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
        };*/
        //绑定适配器
        viewPager.setAdapter(myPageAdapter);
        //设置viewPager的初始界面为第一个界面
        viewPager.setCurrentItem(0);
        //添加切换界面的监听器
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        // 获取滚动条的宽度
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.bg_login).getWidth();
        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;
        //计算出滚动条初始的偏移量
        offset = (screenW / 2 - bmpW) / 2;
        //计算出切换一个界面时，滚动条的位移量
        one = offset * 2 + bmpW;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        //将滚动条的初始位置设置成与左边界间隔一个offset
        scrollbar.setImageMatrix(matrix);

        registerForContextMenu(go);
        registerForContextMenu(not);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v == not) {
            menu.add(0, 2, 0, "退票");
        } else if (v == go) {
            menu.add(0, 1, 0, "删除");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
                View view = go.getChildAt(menuInfo.position);
                TextView id = (TextView) view.findViewById(R.id.orderNum);

                Orders orders = new Orders();
                orders.setObjectId(id.getText().toString());
                orders.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("删除已出行订单", "删除成功");
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.i("删除已出行订单", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
            case 2:
                AdapterView.AdapterContextMenuInfo menuInfo2 = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
                View view2 = not.getChildAt(menuInfo2.position);
                TextView flightNumber = (TextView) view2.findViewById(R.id.flightNumber);
                //先查询所选航班
                BmobQuery<Flight> query = new BmobQuery<Flight>();
                query.getObject(flightNumber.getText().toString(), new QueryListener<Flight>() {
                    @Override
                    public void done(final Flight f, BmobException e) {
                        Log.e("f1.getRemaining()", f.getRemaining() + "");
                        f.setRemaining(f.getRemaining() + 1);
                        Log.e("f2.getRemaining()", f.getRemaining() + "");
                        //flight.setRemaining(flight.getRemaining()+1);
                        f.update(f.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("退票", "更新航班信息成功");
                                    Log.e("f3.getRemaining()", f.getRemaining() + "");
                                    ordersAdapter.notifyDataSetChanged();
                                } else {
                                    Log.i("退票", "更新航班信息失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                        //ordersAdapter.notifyDataSetChanged();
                    }
                });

                TextView orderNumber = (TextView) view2.findViewById(R.id.orderNumber);
                Orders orders2 = new Orders();
                orders2.setObjectId(orderNumber.getText().toString());
                orders2.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("删除已出行订单", "删除成功");

                            ordersAdapter.notifyDataSetChanged();
                        } else {
                            Log.i("删除已出行订单", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;

        }
        return super.onContextItemSelected(item);
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    /**
                     * TranslateAnimation的四个属性分别为
                     * float fromXDelta 动画开始的点离当前View X坐标上的差值
                     * float toXDelta 动画结束的点离当前View X坐标上的差值
                     * float fromYDelta 动画开始的点离当前View Y坐标上的差值
                     * float toYDelta 动画开始的点离当前View Y坐标上的差值
                     **/
                    animation = new TranslateAnimation(one, 0, 0, 0);
                    // notGo();
                    notGoLayout.performClick();
                    break;
                case 1:
                    animation = new TranslateAnimation(offset, one, 0, 0);
                    //HadGone();
                    goLayout.performClick();
                    break;
            }
            //arg0为切换到的页的编码
            currIndex = arg0;
            // 将此属性设置为true可以使得图片停在动画结束时的位置
            animation.setFillAfter(true);
            //动画持续时间，单位为毫秒
            animation.setDuration(200);
            //滚动条开始动画
            scrollbar.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notGoLayout:
                //点击"未出行订单“时切换到第一页
                viewPager.setCurrentItem(0);
                // Toast.makeText(QueryOrderActivity.this,"page1",Toast.LENGTH_SHORT).show();
                notGo();

                break;
            case R.id.goLayout:
                //点击“已出行订单”时切换的第二页
                viewPager.setCurrentItem(1);
                // Toast.makeText(QueryOrderActivity.this,"page2",Toast.LENGTH_SHORT).show();
                HadGone();
                break;
            case R.id.btn_title_back:
                Intent intent = new Intent();
                intent.setClass(QueryOrderActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void notGo() {
        BmobQuery<Orders> query = new BmobQuery<Orders>();
        query.addWhereEqualTo("user", new BmobPointer(user));
        //query.addWhereEqualTo("state", false);
        //查询出发时间在当前时间之后的订单
        // query.addWhereGreaterThanOrEqualTo("flight.startDate",new BmobDate(new Date()));
        query.include("user,flight");
        query.setLimit(1000);
        query.findObjects(new FindListener<Orders>() {
            @Override
            public void done(List<Orders> list, BmobException e) {
                if (e == null) {
                    List<Orders> list1 = new ArrayList<Orders>();
                    Long time = new Date().getTime();
                    for (Orders order : list) {
                        if (BmobDate.getTimeStamp(order.getFlight().getStartDate().getDate()) > time) {
                            list1.add(order);
                        }
                    }
                    ordersAdapter = new NotGoAdapter(QueryOrderActivity.this, R.layout.not_go_item, list1);
                    not.setAdapter(ordersAdapter);
                    Toast.makeText(QueryOrderActivity.this, "查询成功：共" + list1.size() + "条数据。", Toast
                            .LENGTH_SHORT).show();
                    /* for (Orders orders : list) {

                    }*/
                } else {

                    Log.e("订单查询", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void HadGone() {
        BmobQuery<Orders> query = new BmobQuery<Orders>();
        query.addWhereEqualTo("user", new BmobPointer(user));
        //query.addWhereEqualTo("state", true);
        //查询出发时间在当前时间之前的订单
        // query.addWhereLessThan("flight.startDate",new BmobDate(new Date()));
        query.include("user,flight");
        query.setLimit(1000);
        query.findObjects(new FindListener<Orders>() {
            @Override
            public void done(final List<Orders> list, BmobException e) {
                if (e == null) {
                    List<Orders> list1 = new ArrayList<Orders>();
                    Long time = new Date().getTime();
                    //  Log.e("当前时间",time+"");
                    for (Orders order : list) {
                        if (BmobDate.getTimeStamp(order.getFlight().getStartDate().getDate()) <= time) {
                            list1.add(order);
                        }
                    }
                    Log.e("list1大小", list1.size() + "");
                    adapter = new AlreadyGoAdapter(QueryOrderActivity.this, R.layout.already_go_item, list1);
                    go.setAdapter(adapter);
                    Toast.makeText(QueryOrderActivity.this, "查询成功：共" + list1.size() + "条数据。", Toast
                            .LENGTH_SHORT).show();

                } else {

                    Log.e("订单查询", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
