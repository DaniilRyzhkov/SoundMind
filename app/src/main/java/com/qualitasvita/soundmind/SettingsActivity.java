package com.qualitasvita.soundmind;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    ImageView background;
    Switch solidBackground;
    Switch darkMode;

    public static final String PREF_SETTINGS = "settings";
    public static final String PREF_SOLID_BACKGROUND = "solid_background";
    public static final String PREF_DARK_MODE = "dark_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        MainActivity.showHomeButtonOnActionBar(getSupportActionBar());

        background = findViewById(R.id.background_settings_test);
        solidBackground = findViewById(R.id.switch_solid_background);
        darkMode = findViewById(R.id.switch_dark_mode);

        setSwitchStatus();

        solidBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setPrefSolidBackground(b);
                if(b){
                    background.setVisibility(View.GONE);
                } else {
                    background.setVisibility(View.VISIBLE);
                }
            }
        });

        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setPrefDarkMode(b);
                if (b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    private void setPrefSolidBackground(boolean status) {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREF_SOLID_BACKGROUND, status);
        editor.apply();
    }

    private void setPrefDarkMode(boolean status) {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREF_DARK_MODE, status);
        editor.apply();
    }

    private void setSwitchStatus(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        solidBackground.setChecked(settings.getBoolean(PREF_SOLID_BACKGROUND, false));
        darkMode.setChecked(settings.getBoolean(PREF_DARK_MODE,false));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

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
