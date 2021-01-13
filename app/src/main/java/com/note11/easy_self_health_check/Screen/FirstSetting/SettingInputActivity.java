package com.note11.easy_self_health_check.Screen.FirstSetting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.Screen.MainScreen.SplashActivity;
import com.note11.easy_self_health_check.databinding.ActivitySettingInputBinding;
import com.note11.easy_self_health_check.model.PwModel;
import com.note11.easy_self_health_check.model.SettingModel;
import com.note11.easy_self_health_check.util.AlarmReceiver;
import com.note11.easy_self_health_check.util.MakeAlarm;
import com.note11.easy_self_health_check.util.PwCache;
import com.note11.easy_self_health_check.util.SettingCache;

import java.util.Calendar;

public class SettingInputActivity extends AppCompatActivity {

    private ActivitySettingInputBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_input);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_input);

        final String pw = getIntent().getStringExtra("pw");


        binding.switchSetting1Setting.setChecked(true);
        binding.setTime("7 : 45");
        binding.btn1Setting.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    SettingInputActivity.this, (timePicker, i, i1) -> {
                binding.setTime(i + " : " + i1 );
            }, 7, 45, false);
            timePickerDialog.show();
        });
        binding.btnSettingStart.setOnClickListener(v->endSet(pw));
    }

    private void endSet(String pw){
        PwCache.setPw(this, new PwModel(pw));
        SettingCache.setSetting(this, new SettingModel(binding.switchSetting1Setting.isChecked(), setAlarm()));
        Toast.makeText(this, "설정을 완료하셨습니다. 축하드립니다!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    private int setAlarm(){
        int h = Integer.parseInt(binding.getTime().split(" : ")[0]);
        int m = Integer.parseInt(binding.getTime().split(" : ")[1]);

        return MakeAlarm.makeAlarm(h, m, this);
    }
}