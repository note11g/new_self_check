package com.note11.easy_self_health_check.Screen.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.databinding.ActivityScreenShotViewBinding;
import com.note11.easy_self_health_check.util.ScCache;

public class ScreenShotViewActivity extends AppCompatActivity {

    private ActivityScreenShotViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot_view);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_screen_shot_view);

        binding.txtScExplain.setVisibility(View.INVISIBLE);

        try {
            String strBtm = ScCache.getSc(this).getScreenShot();
            String dateInfo = "최종 자가진단 시간 : " + ScCache.getSc(this).getLastTime();
            if (strBtm != null) {
                binding.imgSc.setImageBitmap(stringToBitMap(strBtm));
                binding.setDate(dateInfo);
            }
        } catch (Exception e) {
            binding.txtScExplain.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }

    }

    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}