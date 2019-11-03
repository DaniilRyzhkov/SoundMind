package com.qualitasvita.soundmind;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
/**
 * Активити получает информацию об анализе события, отправляет обратно в NewNoteActivity
 */
public class Step_5_ResponseActivity extends AppCompatActivity {

    Button btnSaveResponse;
    Button btnMistakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_5_response);
        MainActivity.showHomeButtonOnActionBar(getSupportActionBar());

        btnSaveResponse = findViewById(R.id.btnSaveResponse);
        btnMistakes = findViewById(R.id.btnOpenMistakesActivity);

        btnSaveResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String responseText = ((TextView) findViewById(R.id.response)).getText().toString();
                Intent intent = new Intent();
                intent.putExtra(NewNoteActivity.EXTRA_RESPONSE_TEXT, responseText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnMistakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Step_5_ResponseActivity.this, MistakesActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_completed_note) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu_completed_note);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.info:
                startActivity(new Intent(this, InfoActivity.class));
                break;
            case R.id.intro:
                startActivity(new Intent(this, IntroActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(Step_5_ResponseActivity.this);
                builder.setMessage(R.string.alert_back);
                builder.setCancelable(true);

                // Отменить ввод данных, закрыть окно
                builder.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                // Продолжить ввод
                builder.setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
