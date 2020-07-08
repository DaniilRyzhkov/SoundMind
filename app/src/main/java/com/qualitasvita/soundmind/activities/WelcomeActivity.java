package com.qualitasvita.soundmind.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.qualitasvita.soundmind.R;

/**
 * Активити отображает заставку перед IntroActivity, запускается только при первом запуске.
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setStatusBarTransparent();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        findViewById(R.id.tv_welcome_title).startAnimation(animation);
        findViewById(R.id.iv_welcome_logo).startAnimation(animation);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(WelcomeActivity.this, IntroActivity.class));
            finish();
        }, SPLASH_TIME_OUT);
    }

    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
