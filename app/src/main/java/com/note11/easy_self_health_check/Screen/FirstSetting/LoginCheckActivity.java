package com.note11.easy_self_health_check.Screen.FirstSetting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.databinding.ActivityLoginCheckBinding;

public class LoginCheckActivity extends AppCompatActivity {

    private ActivityLoginCheckBinding binding;
    private WebView mWebView;
    private WebSettings mWebSettings;
    private final String js1 = "document.getElementById('btnConfirm2').click();";
    private final String js2 = "document.getElementsByClassName('searchBtn')[0].click()";
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_check);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_check);
        mWebView = binding.webLoginCheck;

        Toast.makeText(this, "정보 입력을 진행해주세요", Toast.LENGTH_LONG).show();
        setWebView();
    }

    private void setWebView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals("https://hcs.eduro.go.kr/#/loginHome")) {
                    view.evaluateJavascript(js1, s -> {
                    });
                } else if (url.equals("https://hcs.eduro.go.kr/#/loginWithUserInfo")) {
                        if(isFirst) {
                            view.evaluateJavascript(js2, s -> {});
                            isFirst = false;
                        }
                }else if (url.equals("https://hcs.eduro.go.kr/#/main")){
                    goToNext();
                }
            }
        });
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setBuiltInZoomControls(false);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setDomStorageEnabled(true);
        mWebView.loadUrl("https://hcs.eduro.go.kr/");
    }

    private void goToNext() {
        startActivity(new Intent(this, PasswordInputActivity.class));
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        finish();
    }
}