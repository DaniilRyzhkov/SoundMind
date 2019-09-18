package com.qualitasvita.soundmind;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.qualitasvita.soundmind.adapters.ThoughtAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Активити получает информацию об мыслях, отправляет обратно в NewNoteActivity
 */
public class S3_ThoughtActivity extends AppCompatActivity {

    Button btnSaveThought;
    RecyclerView thoughtsList;
    public static List<Answer> thoughts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought);
        MainActivity.showHomeButtonOnActionBar(getSupportActionBar());

        setThoughtArray();
        thoughtsList = findViewById(R.id.thoughtList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(S3_ThoughtActivity.this);

        ThoughtAdapter thoughtAdapter = new ThoughtAdapter(thoughts, linearLayoutManager); // обнулить в ондестрой?
        thoughtsList.setLayoutManager(linearLayoutManager);
        thoughtsList.setHasFixedSize(false);
        thoughtsList.setAdapter(thoughtAdapter);
        linearLayoutManager.setSmoothScrollbarEnabled(true);

        btnSaveThought = findViewById(R.id.btnSaveThought);
        btnSaveThought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correctKey = true; // Проверка наличия текста и выставленного уровня
                for (Answer thought : thoughts) {
                    if (!thought.getText().equals("") && thought.getLevel() == 0) { // Если текст введен, а уровень не выставлен
                        Toast.makeText(S3_ThoughtActivity.this, // Показать напоминание
                                getResources().getString(R.string.toast_forgot_to_set_the_level), Toast.LENGTH_LONG).show();
                        correctKey = false;
                        break;
                    }
                }
                if (correctKey) {
                    if (thoughts.get(0).getLevel() == 0) // Если у первой записи не выставлен уровень
                        thoughts.remove(0); // Значит она пустая, удалить
                    thoughts = invertList(thoughts);
                    String thoughtText = NewNoteActivity.toStringFromAnswer(thoughts);
                    Intent intent = new Intent();
                    intent.putExtra(NewNoteActivity.EXTRA_THOUGHT_TEXT, thoughtText);
                    intent.putExtra(NewNoteActivity.EXTRA_THOUGHT_LIST, NewNoteActivity.createResultList(thoughts));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    /**
     * Метод меняет расположение записей в списке в обратном порядке
     *
     * @param thoughts целевой список
     * @return список с обратным порядком
     */
    private List<Answer> invertList(List<Answer> thoughts) {
        List<Answer> invertThoughts = new ArrayList<>();
        for (Answer thought : thoughts) {
            invertThoughts.add(0, thought);
        }
        return invertThoughts;
    }

    private void setThoughtArray() {
        thoughts = new ArrayList<>();
        thoughts.add(new Answer());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(S3_ThoughtActivity.this);
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