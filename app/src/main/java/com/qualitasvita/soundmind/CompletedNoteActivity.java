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
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Активити отображает готовую запись.
 * Есть возможность удалить запись или отправить в виде сообщения.
 */
public class CompletedNoteActivity extends AppCompatActivity {

    private static String dateText;
    private static String situationText;
    private static String emotionText;
    private static String thoughtText;
    private static String actionText;
    private static String responseText;
    private static String resultText;
    private static int position;
    private static boolean deleteThisNote;
    public static final String EXTRA_DELETE_THIS_NOTE = "com.qualitasvita.soundmind.delete_this_note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_note);
        MainActivity.showHomeButtonOnActionBar(getSupportActionBar());
        deleteThisNote = false;

        // Извлечь из экстра объект Note и его позицию
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            Note note = (Note) arguments.getSerializable(Note.class.getSimpleName());
            if (note != null) {
                dateText = note.getDate();
                situationText = note.getSituationText();
                emotionText = note.getEmotionText();
                thoughtText = note.getThoughtText();
                actionText = note.getActionText();
                responseText = note.getResponseText();
                resultText = note.getResultText();

                ((TextView) findViewById(R.id.dateRead)).setText(dateText);
                ((TextView) findViewById(R.id.situationRead)).setText(situationText);
                ((TextView) findViewById(R.id.emotionRead)).setText(emotionText);
                ((TextView) findViewById(R.id.thoughtRead)).setText(thoughtText);
                ((TextView) findViewById(R.id.actionRead)).setText(actionText);
                ((TextView) findViewById(R.id.responseRead)).setText(responseText);
                ((TextView) findViewById(R.id.resultRead)).setText(resultText);
            }
        }
        position = getIntent().getIntExtra(MainActivity.EXTRA_NOTE_POSITION, -1);

        // Отправка данных с помощью ActionSend
        ImageButton sendReportButton = findViewById(R.id.fabSendReport);
        sendReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.report_theme);
                emailIntent.putExtra(Intent.EXTRA_TEXT, messageBuilder());
                //CompletedNoteActivity.this.startActivity(Intent.createChooser(emailIntent, "Отправка сообщения..."));
                CompletedNoteActivity.this.startActivity(emailIntent);
            }
        });

    }

    /**
     * Формирование сообщения для отправки
     */
    private String messageBuilder() {
        StringBuilder text = new StringBuilder();
        text.append(dateText);
        text.append("\n");
        text.append(this.getString(R.string.label_situation));
        text.append("\n");
        text.append(situationText);
        text.append("\n\n");
        text.append(this.getString(R.string.label_emotions));
        text.append("\n");
        text.append(emotionText);
        text.append("\n\n");
        text.append(this.getString(R.string.label_thoughts));
        text.append("\n");
        text.append(thoughtText);
        text.append("\n\n");
        text.append(this.getString(R.string.label_action));
        text.append("\n");
        text.append(actionText);
        text.append("\n\n");
        text.append(this.getString(R.string.label_response));
        text.append("\n");
        text.append(responseText);
        text.append("\n\n");
        text.append(this.getString(R.string.label_result));
        text.append("\n");
        text.append(resultText);
        return text.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_completed_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Нажатие на кнопку "Удалить"
            case R.id.delete_this_note:
                AlertDialog.Builder builder = new AlertDialog.Builder(CompletedNoteActivity.this);
                builder.setMessage(R.string.alert_delete);
                builder.setCancelable(true);

                // Подготовка к удалению текущей записи. Отправка ключа и позиции в главную активити
                builder.setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteThisNote = true;
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_DELETE_THIS_NOTE, deleteThisNote);
                        intent.putExtra(MainActivity.EXTRA_NOTE_POSITION, position);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

                // Отмена удаления
                builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;

            // Нажатие на стрелку назад слева на тулбаре
            case android.R.id.home:
                finish();
                break;

            // Нажатие на кнопку помощь
            case R.id.help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
