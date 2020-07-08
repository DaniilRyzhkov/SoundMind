package com.qualitasvita.soundmind.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.adapters.IntroPagerAdapter;
import com.rd.PageIndicatorView;

/**
 * Активити представляет Intro приложения, запускается при первом запуске, а также через меню.
 * Часть кода закомментирована, введется тестирование
 */
public class IntroActivity extends AppCompatActivity {

    private Button btnSkip;
    private int[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setStatusBarTransparent();
        layouts = new int[]{R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3, R.drawable.slide_4};

        // Viewpager initiate
        ViewPager2 viewPager = findViewById(R.id.viewpager_intro);
        viewPager.setAdapter(new IntroPagerAdapter(layouts));

        // PageIndicator initiate
        PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView_intro);
        pageIndicatorView.setCount(4);

        // Skip intro too (by "Skip" button)
        btnSkip = findViewById(R.id.btn_skip_intro);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
                if (position == layouts.length - 1) {
                    btnSkip.setText(R.string.button_start);
                    btnSkip.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                } else {
                    btnSkip.setText(R.string.button_skip);
                    btnSkip.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                }
            }
        });
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
