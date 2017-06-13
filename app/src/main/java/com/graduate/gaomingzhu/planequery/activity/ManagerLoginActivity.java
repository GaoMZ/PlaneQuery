package com.graduate.gaomingzhu.planequery.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.graduate.gaomingzhu.planequery.R;

/**
 * Created by GaoMingzhu on 2017/4/24.
 * email:2713940331@qq.com
 */

public class ManagerLoginActivity extends Activity implements View.OnClickListener {
    private WebView webView;
    private ImageView m_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_page);
        init();
        webView.loadUrl("http://www.bmob.cn/login");
        webView.setWebViewClient(new HelloWebViewClient ());
    }

    private void init() {
        webView= (WebView) findViewById(R.id.managerDB);
        m_back= (ImageView) findViewById(R.id.m_back);
        m_back.setOnClickListener(this);
    }
    @Override
    //设置回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //调用goBack()返回WebView的上一页面
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(ManagerLoginActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
