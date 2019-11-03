package com.qualitasvita.soundmind.di;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.qualitasvita.soundmind.SettingsActivity;

/**
 * DI class to create DaggerAppComponent
 */
public class App extends Application {

    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // DI component
        component = buildComponent();

        // DayNight mode
        SharedPreferences settings = this.getSharedPreferences(SettingsActivity.PREF_SETTINGS, MODE_PRIVATE);
        boolean status = settings.getBoolean(SettingsActivity.PREF_DARK_MODE, false);
        if (status) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
