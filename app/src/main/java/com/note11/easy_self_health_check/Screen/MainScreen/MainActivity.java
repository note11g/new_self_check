package com.note11.easy_self_health_check.Screen.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jraska.falcon.Falcon;
import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.databinding.ActivityMainBinding;
import com.note11.easy_self_health_check.model.ScModel;
import com.note11.easy_self_health_check.util.PwCache;
import com.note11.easy_self_health_check.util.ScCache;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private WebView mWebView;
    private WebSettings mWebSettings;
    private ConstraintLayout mConstraint;
    private boolean isEnd = false;
    private String js = "";
    private final String js1 = "document.getElementsByClassName('btn')[0].click();";
    private final String js2 = "document.getElementById('survey_q1a1').click();" +
            "document.getElementById('survey_q2a1').click();" +
            "document.getElementById('survey_q3a1').click();" +
            "document.getElementById('btnConfirm').click();";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        final String PW = PwCache.getPw(this).getPw();
        js = "javascript:" +
                "document.getElementsByClassName('input_text_common')[0].value='" + PW + "';" +
                "document.getElementById('btnConfirm').click();";

        mWebView = binding.webMain;
        setWeb();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//don't user touch before login

        mConstraint = binding.constraintLayoutMain;

        binding.btnMainTrue.setOnClickListener(v -> getB(true));
        binding.btnMainFalse.setOnClickListener(v -> getB(false));
        binding.btnMainSetting.setOnClickListener(v -> goToSetting());
        binding.btnMainSc.setOnClickListener(v -> viewScreenShot());
    }

    private void getB(boolean isSubclinical) {
        if (!mWebView.getUrl().equals("https://hcs.eduro.go.kr/#/survey"))
            mWebView.loadUrl("javascript:" + js1);//안눌렸을 경우 설문버튼

        if (isSubclinical)
            new Handler().postDelayed(() -> mWebView.loadUrl("javascript:" + js2), 200);//설문 문항

        mConstraint.setVisibility(View.INVISIBLE);
        isEnd = true;
    }

    private void setWeb() {
        mWebView.setWebViewClient(new WebViewClient() {
                                      @Override
                                      public void onPageFinished(WebView view, String url) {
                                          super.onPageFinished(view, url);
                                          if (url.equals("https://hcs.eduro.go.kr/#/relogin")) {
                                              new Handler().postDelayed(() -> {
                                                  view.evaluateJavascript(js, s -> {});//로그인
                                              }, 10);
                                          } else if (url.equals("https://hcs.eduro.go.kr/#/main")) {
                                              new Handler().postDelayed(() -> {
                                                  view.evaluateJavascript(js1, s -> { });//로그인
                                              }, 1000);
                                          } else if (url.equals("https://hcs.eduro.go.kr/#/survey")) {
                                              binding.loadingScreen.setVisibility(View.INVISIBLE);
                                              getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);//user can touch after login
                                              binding.LoadingBar.setVisibility(View.GONE);
                                          }
                                      }
                                  }
        );
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setDomStorageEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        mWebView.loadUrl("https://hcs.eduro.go.kr/#/relogin");
    }

    private void goToSetting() {
        startActivity(new Intent(MainActivity.this, SettingActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void getScreenShot() {
        Bitmap bitmap = Falcon.takeScreenshotBitmap(MainActivity.this);
        try {
            if (bitmap != null) {
                try {
                    ScCache.clear(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                String temp = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

                ScCache.setSc(this, new ScModel(temp, new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분", Locale.KOREAN).format(new Date())));
                isEnd = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "오류로 인해 캡쳐되지 않았습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void viewScreenShot() {
        startActivity(new Intent(this, ScreenShotViewActivity.class));
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isEnd) getScreenShot();
    }
}