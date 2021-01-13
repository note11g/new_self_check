package com.note11.easy_self_health_check.Screen.FirstSetting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.databinding.ActivityFirstBinding;

public class FirstActivity extends AppCompatActivity {

    private ActivityFirstBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_first);

        startActivity(new Intent(this, AlarmNotDozeActivity.class));
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);

        binding.btnFirstStart.setOnClickListener(v->nextStep());
    }

    private void nextStep(){
        startActivity(new Intent(this, LoginCheckActivity.class));
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        finish();
    }
}