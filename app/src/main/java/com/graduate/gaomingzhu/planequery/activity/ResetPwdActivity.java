package com.graduate.gaomingzhu.planequery.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.util.Test;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ResetPwdActivity extends Activity {
    private EditText email;
    private Button confirm;
    private Button return_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        email=(EditText)findViewById(R.id.resetpwd_email);
        confirm=(Button)findViewById(R.id.confirm);
        return_login = (Button) findViewById(R.id.return_login);
        return_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(ResetPwdActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailString=email.getText().toString().trim();
                if(emailString!=null&& Test.testEmail(emailString)){
                    BmobUser.resetPasswordByEmail(emailString, new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ResetPwdActivity.this, "重置密码请求成功，请到" + emailString + "邮箱进行密码重置操作", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ResetPwdActivity.this, "重置密码请求失败", Toast.LENGTH_SHORT).show();
                                Log.e("重置密码失败",e.getErrorCode()+" "+e.getMessage());
                            }
                        }
                    });
                }else{
                    email.requestFocus();
                    email.setError(getString(R.string.email_is_wrong));
                    return;
                }
            }
        });
    }
}
