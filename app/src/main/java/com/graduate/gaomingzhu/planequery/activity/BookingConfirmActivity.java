package com.graduate.gaomingzhu.planequery.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.model.Flight;
import com.graduate.gaomingzhu.planequery.model.Orders;
import com.graduate.gaomingzhu.planequery.model.User;

import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by GaoMingzhu on 2017/4/25.
 * email:2713940331@qq.com
 */

public class BookingConfirmActivity extends Activity implements View.OnClickListener {
    private Flight f;

    //航班信息显示
    private TextView flightName;
    private TextView startPlace;
    private TextView endPlace;
    private TextView startTime;
    private TextView endTime;
    private TextView ticketPrice;
    private TextView ticketRemaining;
    private TextView passenger;
    private Button bookingButton;
    private Button cancel;
    private TextView backView;

    private User user = BmobUser.getCurrentUser(User.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        init();
        Intent i = getIntent();
        f = (Flight) i.getSerializableExtra("flight");
        flightName.setText(f.getFlight());
        startPlace.setText(f.getStartCity());
        endPlace.setText(f.getEndCity());
        startTime.setText(f.getStartDate().getDate().toString());
        endTime.setText(f.getEndDate().getDate().toString());
        ticketRemaining.setText(f.getRemaining() + "张");
        ticketPrice.setText(f.getPrice().toString() + "元");
        passenger.setText(user.getUsername());

        //Toast.makeText(BookingConfirmActivity.this,""+f.getObjectId(),Toast.LENGTH_SHORT).show();
    }

    private void init() {
        flightName = (TextView) findViewById(R.id.fligthName);
        startPlace = (TextView) findViewById(R.id.startPlace);
        endPlace = (TextView) findViewById(R.id.endPlace);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        ticketPrice = (TextView) findViewById(R.id.ticketPrice);
        ticketRemaining = (TextView) findViewById(R.id.ticketRemaining);
        passenger = (TextView) findViewById(R.id.passenger);
        bookingButton = (Button) findViewById(R.id.booking_button);
        bookingButton.setOnClickListener(this);
        backView = (TextView) findViewById(R.id.back_main);
        backView.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancel_booking_bt);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.booking_button:
                if (f.getRemaining() > 0) {
                    f.setRemaining(f.getRemaining() - 1);
                    f.update(f.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            Log.e("余票数减1", f.getObjectId() + "  " + f.getRemaining());
                        }
                    });
                    Orders o = new Orders();
                    //Log.e("flight id",f.getObjectId());
                    o.setFlight(f);
                    o.setUser(user);
                    o.setState(false);
                    o.setSeat(f.getRemaining());
                    //Toast.makeText(BookingConfirmActivity.this,""+new Date(System.currentTimeMillis()),Toast.LENGTH_SHORT).show();
                    o.setDate(new BmobDate(new Date(System.currentTimeMillis())));
                    o.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(BookingConfirmActivity.this, "预订成功", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(BookingConfirmActivity.this);
                                //设置对话框图标，可以使用自己的图片，Android本身也提供了一些图标供我们使用
                                builder.setIcon(android.R.drawable.ic_dialog_alert);
                                //设置对话框标题
                                builder.setTitle(R.string.prompt);
                                //设置对话框内的文本
                                builder.setMessage("查看订单详情？");
                                //设置确定按钮，并给按钮设置一个点击侦听，注意这个OnClickListener使用的是DialogInterface类里的一个内部接口
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 执行点击确定按钮的业务逻辑
                                        Intent i = new Intent();
                                        i.setClass(BookingConfirmActivity.this, QueryOrderActivity.class);
                                        dialog.dismiss();
                                        startActivity(i);
                                        finish();
                                    }
                                });
                                //设置取消按钮
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 执行点击取消按钮的业务逻辑
                                        dialog.dismiss();
                                    }
                                });
                                //使用builder创建出对话框对象
                                AlertDialog dialog = builder.create();
                                //显示对话框
                                dialog.show();
                            } else {
                                Log.e("订票失败", e.getErrorCode() + " " + e.getMessage());
                            }

                        }
                    });

                } else {
                    Toast.makeText(BookingConfirmActivity.this, "当前余票不足，无法预订", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back_main:
                Intent intent = new Intent();
                intent.setClass(BookingConfirmActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.cancel_booking_bt:
                Intent intent1 = new Intent();
                intent1.setClass(BookingConfirmActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
