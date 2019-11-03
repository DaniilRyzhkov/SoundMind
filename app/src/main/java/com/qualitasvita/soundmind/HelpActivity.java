package com.qualitasvita.soundmind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Активити с инструкциями и подсказками
 */
public class HelpActivity extends AppCompatActivity {

    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        MainActivity.showHomeButtonOnActionBar(getSupportActionBar());

        background = findViewById(R.id.background_take_book);

        findViewById(R.id.btnOpenMistakesActivity_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, MistakesActivity.class));
            }
        });


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
