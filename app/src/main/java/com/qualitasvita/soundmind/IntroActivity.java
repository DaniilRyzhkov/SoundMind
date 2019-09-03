package com.qualitasvita.soundmind;

import android.graphics.Color;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qualitasvita.soundmind.adapters.IntroPagerAdapter;

/**
 * Активити представляет Intro приложения, запускается при первом запуске, а также через меню.
 * Часть кода закомментирована, введется тестирование
 */
public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout layoutDot;
    //private Button btnNext;
    private Button btnSkip;
    private ImageView btnStart;
    private int[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setStatusBarTransparent();

        viewPager = findViewById(R.id.view_pager_intro);
        layoutDot = findViewById(R.id.dot_layout);
        btnStart = findViewById(R.id.button_start);
        //btnNext = findViewById(R.id.btn_next_intro);
        btnSkip = findViewById(R.id.btn_skip_intro);

        /*btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem() + 1;
                if (currentPage < layouts.length) {
                    viewPager.setCurrentItem(currentPage);
                } else {
                    finish();
                }
            }
        });*/

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layouts = new int[]{R.layout.slide_1, R.layout.slide_2, R.layout.slide_3, R.layout.slide_4};
        IntroPagerAdapter pagerAdapter = new IntroPagerAdapter(layouts, getApplicationContext());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == layouts.length - 1) {
                    btnStart.setVisibility(View.VISIBLE);
                    btnSkip.setVisibility(View.GONE);
                    //btnNext.setText(R.string.button_start);
                    //btnNext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                } else {
                    btnStart.setVisibility(View.GONE);
                    btnSkip.setVisibility(View.VISIBLE);
                    //btnNext.setText(R.string.button_next);
                    //btnNext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                }
                setDotStatus(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        setDotStatus(0);
    }

    /**
     * Внизу экрана расположен layout, при отображении страницы:
     * - из layout удаляем все элементы
     * - создаем TextView с четыремя точками
     * - точку соответствущей номеру страницы окрашиваем в цвет Accent.
     * @param page номер текущего слайда
     */
    private void setDotStatus(int page) {
        layoutDot.removeAllViews();
        TextView[] tvDots = new TextView[layouts.length];
        for (int i = 0; i < tvDots.length; i++) {
            tvDots[i] = new TextView(this);
            tvDots[i].setText(Html.fromHtml("&#8226;"));
            tvDots[i].setTextSize(30);
            tvDots[i].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            layoutDot.addView(tvDots[i]);
        }
        if (tvDots.length > 0) {
            tvDots[page].setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        }
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
