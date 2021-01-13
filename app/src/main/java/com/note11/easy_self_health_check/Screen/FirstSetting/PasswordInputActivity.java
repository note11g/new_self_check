package com.note11.easy_self_health_check.Screen.FirstSetting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.databinding.ActivityPasswordInputBinding;

public class PasswordInputActivity extends AppCompatActivity {

    private ActivityPasswordInputBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_input);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_input);

        binding.setPw("");
        binding.btnSecondStart.setOnClickListener(v->goToNext());
    }

    private void goToNext() {
        if(binding.getPw().length() < 4){
            Toast.makeText(this, "비밀번호를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SettingInputActivity.class);
        intent.putExtra("pw", binding.getPw());
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        finish();
    }
}