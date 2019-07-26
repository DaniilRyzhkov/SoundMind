package com.qualitasvita.soundmind;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
/**
 * Активити получает информацию об эмоциях, отправляет обратно в NewNoteActivity
 */
public class S2_EmotionActivity extends AppCompatActivity {

    Button btnSaveEmotion;
    ListView emotionsList;
    private static List<Answer> emotions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);
        MainActivity.showHomeButtonOnActionBar(getSupportActionBar());
        setEmotionArray();

        emotionsList = findViewById(R.id.emotionList);
        EmotionAdapter emotionAdapter = new EmotionAdapter(this, R.layout.item_of_list_emotion, emotions);
        emotionsList.setAdapter(emotionAdapter);

        btnSaveEmotion = findViewById(R.id.btnSaveEmotion);
        btnSaveEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emotionText = NewNoteActivity.toStringFromAnswer(emotions);
                Intent intent = new Intent();
                intent.putExtra(NewNoteActivity.EXTRA_EMOTION_TEXT, emotionText);
                intent.putExtra(NewNoteActivity.EXTRA_EMOTION_LIST, NewNoteActivity.createResultList(emotions));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setEmotionArray() {
        emotions = new ArrayList<>();
        emotions.add(new Answer(getResources().getString(R.string.emotion_anxiety)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_fear)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_anger)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_oppression)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_shame)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_guilt)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_frustration)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_jealousy)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_hurt)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_insult)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_sadness)));
        emotions.add(new Answer(getResources().getString(R.string.emotion_apathy)));
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
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(S2_EmotionActivity.this);
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
