package com.graduate.gaomingzhu.planequery.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.model.User;
import com.graduate.gaomingzhu.planequery.util.Test;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by GaoMingzhu on 2017/4/19.
 * email:2713940331@qq.com
 */

public class MyInfoActivity extends Activity implements View.OnClickListener {
    private User user = BmobUser.getCurrentUser(User.class);
    private TextView back_main;
    private TextView tv_name;
    private EditText tv_age;
    private EditText tv_sex;
    private EditText tv_telephone;
    private EditText tv_email;
    private EditText tv_IDCard;
    private Button modify_info;
    private Button exit_app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        init();
        queryUserInfo();
        showmMyInfo();
    }

    private void queryUserInfo() {

    }

    private void showmMyInfo() {
        tv_name.setText(user.getUsername());
        if (user.getSex() != null) {
            tv_age.setText(user.getAge() + "");
        }
        if (user.getEmail() != null) {
            tv_email.setText(user.getEmail());
        }
        if (user.getSex() != null) {
            tv_sex.setText((user.getSex() == true) ? "男" : "女");
        }
        if (user.getMobilePhoneNumber() != null) {
            tv_telephone.setText(user.getMobilePhoneNumber());
        }
        if (user.getIdCard() != null) {
            tv_IDCard.setText(user.getIdCard());
        }

    }

    private void init() {

        back_main = (TextView) findViewById(R.id.back_main);
        back_main.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_age = (EditText) findViewById(R.id.tv_age);
        tv_sex = (EditText) findViewById(R.id.tv_sex);
        tv_email = (EditText) findViewById(R.id.tv_email);
        tv_telephone = (EditText) findViewById(R.id.tv_telephone);
        exit_app = (Button) findViewById(R.id.exit_app);
        modify_info = (Button) findViewById(R.id.modify_info);
        exit_app.setOnClickListener(this);
        modify_info.setOnClickListener(this);
        tv_IDCard = (EditText) findViewById(R.id.tv_IDCard);

        tv_age.setEnabled(false);
        tv_email.setEnabled(false);
        tv_IDCard.setEnabled(false);
        tv_sex.setEnabled(false);
        tv_telephone.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_main:
                Intent intent = new Intent();
                intent.setClass(MyInfoActivity.this, MainActivity.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.exit_app:
               /* BmobUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser();*/
                new AlertDialog.Builder(MyInfoActivity.this)
                        .setMessage(R.string.sure_exit)
                        .setTitle(R.string.prompt)
                        .setPositiveButton(R.string.confirm_button,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                        BmobUser.logOut();   //清除缓存用户对象
                                        Intent intent = new Intent(
                                                MyInfoActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                new AlertDialog.Builder(MyInfoActivity.this)
                        .setMessage(R.string.sure_exit)
                        .setTitle(R.string.prompt)
                        .setPositiveButton(R.string.confirm_button,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                        BmobUser.logOut();   //清除缓存用户对象
                                        Intent intent = new Intent(
                                                MyInfoActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                }).show();

                break;
            case R.id.modify_info:
                if (modify_info.getText().equals("修改信息")) {
                    modify_info.setText("保存修改");
                    tv_age.setEnabled(true);
                    tv_email.setEnabled(true);
                    tv_IDCard.setEnabled(true);
                    tv_sex.setEnabled(true);
                    tv_telephone.setEnabled(true);
                    tv_age.requestFocus();
                } else {
                    //输入的性别不符合要求
                    if (!(TextUtils.equals(tv_sex.getText().toString(), "男") || TextUtils.equals(tv_sex
                            .getText().toString(), "女"))) {
                        tv_sex.setError("请输入男或女");
                        tv_sex.requestFocus();
                        return;
                    } else if (!Test.testPhone(tv_telephone.getText().toString())) {
                        tv_telephone.setError("手机号输入错误");
                        tv_telephone.requestFocus();
                        return;
                    } else if (!Test.testEmail(tv_email.getText().toString())) {
                        tv_email.setError("电子邮箱输入错误");
                        tv_email.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(tv_IDCard.getText().toString())) {
                        tv_IDCard.setError("身份证输入错误");
                        tv_IDCard.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(tv_age.getText().toString())) {
                        tv_age.setError("年龄输入错误");
                        tv_age.requestFocus();
                        return;
                    }
                    User newUser = new User();
                    newUser.setAge(Integer.parseInt(tv_age.getText().toString()));
                    newUser.setIdCard(tv_IDCard.getText().toString());
                    if (TextUtils.equals(tv_sex.getText().toString(), "男")) {
                        newUser.setSex(true);
                    } else {
                        newUser.setSex(false);
                    }
                    newUser.setEmail(tv_email.getText().toString());
                    newUser.setMobilePhoneNumber(tv_telephone.getText().toString());
                    newUser.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(MyInfoActivity.this, "个人信息更新成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(MyInfoActivity.this, "个人信息更新失败，请重试", Toast.LENGTH_SHORT)
                                        .show();
                                Log.e("个人信息更新失败", e.getErrorCode() + " " + e.getMessage());
                            }
                            modify_info.setText("修改信息");
                            tv_age.setEnabled(false);
                            tv_email.setEnabled(false);
                            tv_IDCard.setEnabled(false);
                            tv_sex.setEnabled(false);
                            tv_telephone.setEnabled(false);
                        }
                    });
                }
                break;
            default:
                break;
        }
    }
}
