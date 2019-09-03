package com.qualitasvita.soundmind;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qualitasvita.soundmind.adapters.EmotionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Активити получает повторную информацию об эмоциях и мыслях, а также конечный результат, отправляет обратно в NewNoteActivity
 */
public class S6_ResultActivity extends AppCompatActivity {

    Button btnSaveResult;
    ListView resultList;
    private static List<Answer> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        MainActivity.showHomeButtonOnActionBar(getSupportActionBar());

        setResultArray();
        resultList = findViewById(R.id.resultList);

        EmotionAdapter resultAdapter = new EmotionAdapter(this, R.layout.item_of_list_emotion, result);
        resultList.setAdapter(resultAdapter);

        btnSaveResult = findViewById(R.id.btnSaveResult);
        btnSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultText1 = ((TextView) findViewById(R.id.resultRead)).getText().toString();
                String resultText2 = NewNoteActivity.toStringFromAnswer(result);
                String resultText = resultText1 + "\n" + resultText2;
                Intent intent = new Intent();
                intent.putExtra(NewNoteActivity.EXTRA_RESULT_TEXT, resultText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * Метод складывает списки эмоций и мыслей в один,
     * предварительно метод getList() проверяет соответствие типов.
     */
    private void setResultArray() {
        ArrayList<Answer> emotions = NewNoteActivity.getList(getIntent().getSerializableExtra(NewNoteActivity.EXTRA_EMOTION_LIST));
        ArrayList<Answer> thoughts = NewNoteActivity.getList(getIntent().getSerializableExtra(NewNoteActivity.EXTRA_THOUGHT_LIST));
        result = new ArrayList<>();
        result.addAll(emotions);
        result.addAll(thoughts);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(S6_ResultActivity.this);
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
