package com.note11.easy_self_health_check.Screen.FirstSetting;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.databinding.ActivityAlarmNotDozeBinding;


public class AlarmNotDozeActivity extends AppCompatActivity {

    private ActivityAlarmNotDozeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_not_doze);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm_not_doze);

        binding.btnFirstStart.setOnClickListener(view -> {
            startActivity(new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS));
            Toast.makeText(this, "설정을 마치면 앱으로 돌아와주세요!", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
    @Override
    public void onBackPressed() {
        return;
    }
}