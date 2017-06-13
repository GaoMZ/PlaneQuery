package com.graduate.gaomingzhu.planequery.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.model.User;
import com.graduate.gaomingzhu.planequery.util.Test;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText etusername;
    private EditText etpassword;
    private EditText confirmPassword;
    private EditText etemail;
    private EditText ettelephone;
    private EditText IdCard;
    private Button registerButton;
    private Button back_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etusername = (EditText) findViewById(R.id.et_name);
        etpassword = (EditText) findViewById(R.id.et_password);
        confirmPassword = (EditText) findViewById(R.id.et_comfirmpassword);
        etemail = (EditText) findViewById(R.id.et_email);
        ettelephone = (EditText) findViewById(R.id.et_telephone);
        registerButton = (Button) findViewById(R.id.register);
        back_login = (Button) findViewById(R.id.back_login);
        IdCard = (EditText) findViewById(R.id.et_IDCARD);
        registerButton.setOnClickListener(this);
        back_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                String username = etusername.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                String password2 = confirmPassword.getText().toString().trim();
                String email = etemail.getText().toString().trim();
                String telephone = ettelephone.getText().toString().trim();
                String idcard=IdCard.getText().toString().trim();
                if (username == null || username.isEmpty()) {
                    etusername.requestFocus();
                    etusername.setError(getString(R.string.username_is_null));
                    return;
                } else if (password == null || password.isEmpty()) {
                    etpassword.requestFocus();
                    etpassword.setError(getString(R.string.password_is_null));
                    return;
                } else if (!password.equals(password2)) {
                    confirmPassword.setText("");
                    etpassword.requestFocus();
                    etpassword
                            .setError(getString(R.string.password_is_different));
                    return;
                } else if (email == null || Test.testEmail(email) == false) {
                    etemail.requestFocus();
                    etemail
                            .setError(getString(R.string.email_is_wrong));
                    return;
                } else if (telephone == null || Test.testPhone(telephone) == false) {
                    ettelephone.requestFocus();
                    ettelephone
                            .setError(getString(R.string.telephone_is_wrong));
                    return;
                }else if(TextUtils.isEmpty(idcard)){
                    IdCard.requestFocus();
                    IdCard.setError("请输入身份证号");
                    return;
                }
                final ProgressDialog dialog = ProgressDialog.show(RegisterActivity.this, getString(R.string
                        .register_upload_message), getString(R.string.register_wait));
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setMobilePhoneNumber(telephone);
                user.setIdCard(idcard);
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        dialog.dismiss();
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_success),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_fail) + " " +
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("注册失败", e.getErrorCode() + " " + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.back_login:
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
