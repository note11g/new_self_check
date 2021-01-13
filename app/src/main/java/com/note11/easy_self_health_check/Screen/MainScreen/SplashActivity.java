package com.note11.easy_self_health_check.Screen.MainScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;

import com.note11.easy_self_health_check.Screen.FirstSetting.FirstActivity;
import com.note11.easy_self_health_check.util.PwCache;
import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.util.SettingCache;

import java.util.concurrent.Executor;

public class SplashActivity extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (PwCache.getPw(this) == null)
            firstAccess();
        else {
            if (SettingCache.getSetting(this).isFinger())
                bioAuth();
            else
                passwordPopUp();
        }
    }

    private void bioAuth() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(SplashActivity.this,
                executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode,
                                                      @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        passwordPopUp();
                    }

                    @Override
                    public void onAuthenticationSucceeded(
                            @NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        goHome();
                    }
                });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("본인인증")
                .setNegativeButtonText("비밀번호 사용")
                .setConfirmationRequired(false)
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void goHome() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
        finish();
    }

    private void firstAccess() {
        startActivity(new Intent(this, FirstActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void passwordPopUp() {
        Intent in = new Intent(this, PasswordAuthActivity.class);
        startActivityForResult(in, 100);
        overridePendingTransition(R.anim.fade_in_fast, R.anim.fade_out_fast);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                goHome();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }
}