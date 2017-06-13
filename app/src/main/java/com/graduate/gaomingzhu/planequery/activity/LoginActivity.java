package com.graduate.gaomingzhu.planequery.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.model.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String applicationID = "186cb5e10ce8e30965b95c7674fc5505";

    private Button login;
    private Button register;
    private Button forgetPassword;
    private EditText etusername;
    private EditText etpassword;
    private ProgressDialog progressDialog;
    private User user;

    private Button managerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, applicationID);
        setContentView(R.layout.activity_login);
        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);
        forgetPassword = (Button) findViewById(R.id.forgetPassword);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        managerLogin= (Button) findViewById(R.id.managerLogin);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        managerLogin.setOnClickListener(this);

        user = BmobUser.getCurrentUser(User.class);
        if (user != null) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login:
                String username = etusername.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                if (username == null || username.isEmpty()) {
                    etusername.requestFocus();
                    etusername.setError(new StringBuffer(
                            getString(R.string.username_is_null)));
                    return;
                }
                if (password == null || password.isEmpty()) {
                    etpassword.requestFocus();
                    etpassword.setError(new StringBuffer(
                            getString(R.string.password_is_null)));
                    return;
                }
                final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "请稍等...", "正在登录...");
                user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        dialog.dismiss();
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            /*区分管理员和普通用户*/
                           /* if(user.getIsManager()==true){
                                Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, ManagerLoginActivity.class);
                                startActivity(i);
                                finish();
                            }*/

                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                            Log.e("login_fail", e.toString());
                        }
                    }
                });
                break;
            case R.id.register:
                intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.forgetPassword:
                intent = new Intent();
                intent.setClass(LoginActivity.this, ResetPwdActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.managerLogin:
                intent=new Intent();
                intent.setClass(LoginActivity.this,ManagerLoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
