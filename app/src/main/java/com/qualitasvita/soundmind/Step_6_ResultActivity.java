package com.qualitasvita.soundmind;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.qualitasvita.soundmind.adapters.EmotionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Активити получает повторную информацию об эмоциях и мыслях, а также конечный результат, отправляет обратно в NewNoteActivity
 */
public class Step_6_ResultActivity extends AppCompatActivity {

    Button btnSaveResult, btnAddPositive;
    ListView resultList;
    private static List<Answer> result;
    private static ArrayList<Answer> positiveEmotions;

    private static final int REQUEST_CODE_POSITIVE = 7;
    public static final String EXTRA_POSITIVE = "com.qualitasvita.soundmind.positive";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_6_result);
        MainActivity.showHomeButtonOnActionBar(getSupportActionBar());

        setResultArray();
        setPositiveEmotionArray();
        resultList = findViewById(R.id.resultList);

        EmotionAdapter resultAdapter = new EmotionAdapter(this, R.layout.item_of_list_emotion, result);
        resultList.setAdapter(resultAdapter);

        btnSaveResult = findViewById(R.id.btnSaveResult);
        btnSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultText1 = ((TextView) findViewById(R.id.resultRead)).getText().toString();
                String resultText2 = NewNoteActivity.toStringFromAnswer(result);
                String resultText3 = getPositiveString(positiveEmotions);
                String resultText = resultText1 + "\n\n" + resultText2 + resultText3;
                Intent intent = new Intent();
                intent.putExtra(NewNoteActivity.EXTRA_RESULT_TEXT, resultText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnAddPositive = findViewById(R.id.btnAddPositiveEmotion);
        btnAddPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Step_6_ResultActivity.this, PositiveEmotionActivity.class);
                intent.putExtra(EXTRA_POSITIVE, positiveEmotions);
                startActivityForResult(intent, REQUEST_CODE_POSITIVE);
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

    /**
     * Инициализация списка положительных эмоций
     */
    private void setPositiveEmotionArray() {
        positiveEmotions = new ArrayList<>();
        positiveEmotions.add(new Answer(getResources().getString(R.string.positive_emotion_serenity)));
        positiveEmotions.add(new Answer(getResources().getString(R.string.positive_emotion_gratitude)));
        positiveEmotions.add(new Answer(getResources().getString(R.string.positive_emotion_inspiration)));
        positiveEmotions.add(new Answer(getResources().getString(R.string.positive_emotion_hope)));
        positiveEmotions.add(new Answer(getResources().getString(R.string.positive_emotion_optimism)));
        positiveEmotions.add(new Answer(getResources().getString(R.string.positive_emotion_joy)));
        positiveEmotions.add(new Answer(getResources().getString(R.string.positive_emotion_calm)));
        positiveEmotions.add(new Answer(getResources().getString(R.string.positive_emotion_satisfaction)));
        positiveEmotions.add(new Answer(getResources().getString(R.string.positive_emotion_enthusiasm)));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Step_6_ResultActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_POSITIVE && resultCode == RESULT_OK) {
            positiveEmotions = NewNoteActivity.getList(data.getSerializableExtra(EXTRA_POSITIVE));
        }
    }

    /**
     * Преобразование списка эмоций в строку, включив только выбранные эмоции
     *
     * @param emotions целевой список
     * @return готовая строка
     */
    private String getPositiveString(List<Answer> emotions) {
        StringBuilder text = new StringBuilder();
        for (Answer emotion : emotions) {
            if (emotion.getLevel() > 0) {
                text.append(emotion.getText());
                text.append("\n");
            }
        }
        String str = text.toString();
        // Может стоит использовать не одну строку, а массив строк?
        if (str.length() > 0) return "\n\n" + str.substring(0, str.length() - 1);
        else return "";
    }
}
