package com.qualitasvita.soundmind.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.qualitasvita.soundmind.R;

/**
 * Активити меню настроек
 */
public class SettingsActivity extends AppCompatActivity {

    ImageView background; // Фоновый рисунок, используется для наглядного изменения функции "однотонный фон"
    Switch solidBackground; // Переключатель "однотонный фон"
    Switch darkMode; // Переключатель "Темная тема"

    public static final String PREF_SETTINGS = "settings";
    public static final String PREF_SOLID_BACKGROUND = "solid_background";
    public static final String PREF_DARK_MODE = "dark_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_settings);
        setSupportActionBar(bottomAppBar);

        background = findViewById(R.id.background_settings_test);
        solidBackground = findViewById(R.id.switch_solid_background);
        darkMode = findViewById(R.id.switch_dark_mode);

        // Установить переключатели в сохраненное положение
        setSwitchStatus();

        // Слушатель на переключатель "Однотонный фон"
        solidBackground.setOnCheckedChangeListener((compoundButton, b) -> {
            // Сохранение состояние в SharedPreferences
            setPrefSolidBackground(b);
            // Изменение вида на текущей активити
            if(b){
                background.setVisibility(View.GONE);
            } else {
                background.setVisibility(View.VISIBLE);
            }
        });

        // Слушатель на переключатель "Темная тема"
        darkMode.setOnCheckedChangeListener((compoundButton, b) -> {
            // Сохранение состояния в SharedPreferences
            setPrefDarkMode(b);
            // Установка выбраннного состояния mode DayNight
            if (b){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    /**
     * Сохранение выбранной настройки(положения) переключателя режима "Однотонный фон" в SharedPreferences.
     * Этот режим позволяет сделать фоновые рисунки невидимыми во всем приложении.
     * @param status состояние переключателя, true - убрать рисунки, false - показать.
     */
    private void setPrefSolidBackground(boolean status) {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREF_SOLID_BACKGROUND, status);
        editor.apply();
    }

    /**
     * Сохранение выбранной настройки(положения) переключателя режима "Тёмная тема" в SharedPreferences.
     * Этот режим позволяет включить тёмную тему(Night mode)
     * @param status состояние переключателя, true - night, false - day.
     */
    private void setPrefDarkMode(boolean status) {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREF_DARK_MODE, status);
        editor.apply();
    }

    /**
     * Устнавливает переключатель в сохраненное ранее положение, используется при создании активити
     * в методе OnCreate().
     */
    private void setSwitchStatus(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        solidBackground.setChecked(settings.getBoolean(PREF_SOLID_BACKGROUND, false));
        darkMode.setChecked(settings.getBoolean(PREF_DARK_MODE,false));
    }

    // закрытие активити при нажатии на стрелочку назад
    // Активити заменить на фрагмент, метод оставить в активити-контейнере
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Установить видимость фонового рисунка, в завивсимости от настроек.
     * На данный момент каждая активити с рисунком в методе OnResume() проверяет это состоние,
     * чтобы избежать перезапуска приложения при изменении настроек.
     */
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = this.getSharedPreferences(SettingsActivity.PREF_SETTINGS, MODE_PRIVATE);
        boolean status = settings.getBoolean(SettingsActivity.PREF_SOLID_BACKGROUND, false);
        if (status) {
            background.setVisibility(View.GONE);
        } else {
            background.setVisibility(View.VISIBLE);
        }
    }
}
