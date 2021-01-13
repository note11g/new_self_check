package com.note11.easy_self_health_check.Screen.MainScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.databinding.ActivityPasswordAuthBinding;
import com.note11.easy_self_health_check.util.PwCache;

public class PasswordAuthActivity extends AppCompatActivity {

    private ActivityPasswordAuthBinding binding;
    private String passwordCache = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_password_auth);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_auth);
        numberPad();
    }

    private void numberPad() {
        binding.buttonNum0Pw.setOnClickListener(view -> clickNum(0));
        binding.buttonNum1Pw.setOnClickListener(view -> clickNum(1));
        binding.buttonNum2Pw.setOnClickListener(view -> clickNum(2));
        binding.buttonNum3Pw.setOnClickListener(view -> clickNum(3));
        binding.buttonNum4Pw.setOnClickListener(view -> clickNum(4));
        binding.buttonNum5Pw.setOnClickListener(view -> clickNum(5));
        binding.buttonNum6Pw.setOnClickListener(view -> clickNum(6));
        binding.buttonNum7Pw.setOnClickListener(view -> clickNum(7));
        binding.buttonNum8Pw.setOnClickListener(view -> clickNum(8));
        binding.buttonNum9Pw.setOnClickListener(view -> clickNum(9));
        binding.buttonCancel.setOnClickListener(view -> delPw());
    }

    private void clickNum(int num) {
        passwordCache += num;
        binding.setPw(passwordCache);
        if (passwordCache.length() == 2)//2글자로 확인
           pwEndCheck();
    }

    private void delPw() {
        if (passwordCache.length() > 0)
            passwordCache = passwordCache.substring(0, passwordCache.length() - 1);
        binding.setPw(passwordCache);
    }

    private void pwEndCheck() {
        if (passwordCache.equals(PwCache.getPw(this).getPw().substring(0,2)))//pwcache 앞 두자리와 비교
            succeed();
        else {
            Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
            passwordCache = "";
            binding.setPw("");
        }
    }

    private void succeed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}