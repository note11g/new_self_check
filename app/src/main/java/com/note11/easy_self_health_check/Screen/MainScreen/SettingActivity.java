package com.note11.easy_self_health_check.Screen.MainScreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.databinding.ActivitySettingBinding;
import com.note11.easy_self_health_check.model.SettingModel;
import com.note11.easy_self_health_check.util.AlarmReceiver;
import com.note11.easy_self_health_check.util.MakeAlarm;
import com.note11.easy_self_health_check.util.SettingCache;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        binding.switchSetting1Setting.setChecked(SettingCache.getSetting(this).isFinger());

        int getT = SettingCache.getSetting(this).getTime();

        binding.setTime(getT / 100 + " : " + getT % 100);
        binding.btn1Setting.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    SettingActivity.this, (timePicker, i, i1) -> {
                endSet(setAlarm(i, i1));
                binding.setTime(i + " : " + i1);
            }, getT / 100, getT % 100, false);
            timePickerDialog.show();
        });
        binding.switchSetting1Setting.setOnCheckedChangeListener((buttonView, isChecked) -> endSet(isChecked));
        binding.btnSettingContact.setOnClickListener(v -> contact());

        try {
            binding.setVersion(this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void endSet(boolean finger) {
        final int time = SettingCache.getSetting(this).getTime();
        SettingCache.clear(this);
        SettingCache.setSetting(this, new SettingModel(finger, time));
        Toast.makeText(this, "설정이 변경되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void endSet(int time) {
        SettingCache.clear(this);
        SettingCache.setSetting(this,
                new SettingModel(binding.switchSetting1Setting.isChecked(), time)
        );
        Toast.makeText(this, "설정이 변경되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private int setAlarm(int h, int m) {
        return MakeAlarm.makeAlarm(h, m, this);
    }

    public void contact() {
        Intent contactIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://qr.kakao.com/talk/9eT.cKmmDgcvP8V5RlK6EE7nKJQ-"));
        startActivity(contactIntent);
    }
}