package com.example.assessment_2.activity;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.example.assessment_2.R;

public class WebViewActivity extends Activity {

    WebView webView;
    private String linkUrl;
    private WebViewClient mWebviewclient;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = findViewById(R.id.webView);

        url = getIntent().getStringExtra("URL");

        initWebView();
    }


    private void initWebView() {
//        webView.loadUrl("https://kawasaki.com.au/");
//        webView.loadUrl("https://www.baidu.com/");
        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        initWebViewClient();
    }


    private void initWebViewClient() {
        mWebviewclient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        };
        webView.setWebViewClient(mWebviewclient);
    }
}
