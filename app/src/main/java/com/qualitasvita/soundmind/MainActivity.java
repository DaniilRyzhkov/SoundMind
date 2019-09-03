
package com.qualitasvita.soundmind;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qualitasvita.soundmind.adapters.MainAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Главное активити, категория LAUNCHER
 *
 * @author Daniil Ryzhkov
 */
public class MainActivity extends AppCompatActivity {

    private static ArrayList<Note> notes = new ArrayList<>(); // БД в виде списка
    private static ArrayList<String> items; //Список заголовков пунктов в списке записей
    private RecyclerView listNote; //Список пунктов записей
    private static final int REQUEST_CODE_NEW_NOTE = 0;
    public static final int REQUEST_CODE_COMPLETED_NOTE = 9;
    //public static final int REQUEST_CODE_INTRO = 8;
    private static final String REF_KEY = "IntroSlider";
    private static final String REF_FLAG = "FirstTimeStartFlag";
    private static final String TAG = "sound_mind";
    public static final String EXTRA_NOTE_POSITION = "com.qualitasvita.soundmind.note_position";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFirstTimeStartApp()) {
            setFirstTimeStartStatus(false, getApplicationContext());
            startActivity(new Intent(this, WelcomeActivity.class));
        }

        setContentView(R.layout.activity_main);
        loadData();
        listNote = findViewById(R.id.listNote);
        //deleteData(); //Резервное форматирование базы данных

        FloatingActionButton floatingActionButton = findViewById(R.id.fabNewNote);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_NOTE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTransparentBackground();

        items = createItemList(notes); //Формирование списка заголовков пунктов списка записей
        //MainAdapter mainAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        MainAdapter mainAdapter = new MainAdapter();
        listNote.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        listNote.setHasFixedSize(true);
        listNote.setAdapter(mainAdapter);
        mainAdapter.setList(notes);


        /*listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openCompletedNote(position);
            }
        });*/
        if (BuildConfig.DEBUG) Log.d(TAG, "onResume() completed");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_NEW_NOTE) {
            if (resultCode == RESULT_OK) {
                Bundle arguments = data.getExtras();
                if (arguments != null) {
                    Note newNote = (Note) arguments.getSerializable(Note.class.getSimpleName());
                    notes.add(0, newNote);
                    saveData();
                    Toast.makeText(this, R.string.toast_saved, Toast.LENGTH_SHORT).show();
                    openCompletedNote(0);
                }
            }
        }
        if (requestCode == REQUEST_CODE_COMPLETED_NOTE) {
            if (resultCode == RESULT_OK) {
                boolean deleteThisNote = data.getBooleanExtra(CompletedNoteActivity.EXTRA_DELETE_THIS_NOTE, false);
                if (deleteThisNote) {
                    int position = data.getIntExtra(EXTRA_NOTE_POSITION, -1);
                    if (position >= 0) {
                        notes.remove(position);
                        saveData();
                        Toast.makeText(this, R.string.toast_deleted, Toast.LENGTH_SHORT).show();
                    } else {
                        if (BuildConfig.DEBUG)
                            Log.d(TAG, "wrong value: data.getExtra(EXTRA_NOTE_POSITION) < 0");
                    }
                }
            }
        }
        /*if (requestCode == REQUEST_CODE_INTRO) {
            setFirstTimeStartStatus(false, getApplicationContext());
        }*/
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
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Метод открывает CompletedNoteActivity.
     * Через Extra передается соответствующий объект Note
     * и его позиция(используется при необходимости удалить запись)
     *
     * @param position значение позиции в notes и соответственно в ListView listNote
     */
    public void openCompletedNote(int position) {
        Intent intent = new Intent(this, CompletedNoteActivity.class);
        Note note = notes.get(position);
        intent.putExtra(Note.class.getSimpleName(), note);
        intent.putExtra(EXTRA_NOTE_POSITION, position);
        startActivityForResult(intent, REQUEST_CODE_COMPLETED_NOTE);
    }

    // временный метод, будет заменен адаптером
    private ArrayList<String> createItemList(ArrayList<Note> notes) {
        items = new ArrayList<>();
        for (Note note : notes) {
            String title;
            if (note.getSituationText() == null) {
                title = "";
            } else {
                title = note.getSituationText();
                if (title.length() > 27) {
                    title = title.substring(0, 27).trim() + "...";
                }
            }
            title = note.getDate() + "\n" + title;
            items.add(title);
        }
        if (BuildConfig.DEBUG) Log.d(TAG, "createItemList() completed");
        return items;
    }

    /**
     * Метод вызывается один раз при запуске приложения.
     * Метод запрашивает у базы данных значения семи столбцов,
     * после чего вызывается конструктор Note(), полученный объект добавляется в список notes.
     * На протяжении работы приложения все запросы к данным будут осуществяться через этот список.
     */
    private void loadData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor noteCursor = sqLiteDatabase.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        if (noteCursor.moveToFirst()) {
            int dateIndex = noteCursor.getColumnIndex(DatabaseHelper.COLUMN_DATE);
            int situationTextIndex = noteCursor.getColumnIndex(DatabaseHelper.COLUMN_SITUATION);
            int emotionTextIndex = noteCursor.getColumnIndex(DatabaseHelper.COLUMN_EMOTION_TEXT);
            int thoughtTextIndex = noteCursor.getColumnIndex(DatabaseHelper.COLUMN_THOUGHT_TEXT);
            int actionTextIndex = noteCursor.getColumnIndex(DatabaseHelper.COLUMN_ACTION_TEXT);
            int responseTextIndex = noteCursor.getColumnIndex(DatabaseHelper.COLUMN_RESPONSE_TEXT);
            int resultTextIndex = noteCursor.getColumnIndex(DatabaseHelper.COLUMN_RESULT_TEXT);
            if (notes.isEmpty()) {
                do {
                    notes.add(new Note(
                            noteCursor.getString(dateIndex),
                            noteCursor.getString(situationTextIndex),
                            noteCursor.getString(emotionTextIndex),
                            noteCursor.getString(thoughtTextIndex),
                            noteCursor.getString(actionTextIndex),
                            noteCursor.getString(responseTextIndex),
                            noteCursor.getString(resultTextIndex)));
                } while (noteCursor.moveToNext());
            }
        }
        noteCursor.close();
        databaseHelper.close();
        if (BuildConfig.DEBUG) Log.d(TAG, "loadData() completed");
    }

    /**
     * Метод вызывается при изменении списка notes(добавление записи/удаление записи)
     * На данный момент происходит перезапись всей базы данных,
     * в дальнейшем это будет исправлено на апгрейд базы.
     */
    private void saveData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseHelper.TABLE, null, null);
        ContentValues contentValues;
        for (Note note : notes) {
            contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.COLUMN_DATE, note.getDate());
            contentValues.put(DatabaseHelper.COLUMN_SITUATION, note.getSituationText());
            contentValues.put(DatabaseHelper.COLUMN_EMOTION_TEXT, note.getEmotionText());
            contentValues.put(DatabaseHelper.COLUMN_THOUGHT_TEXT, note.getThoughtText());
            contentValues.put(DatabaseHelper.COLUMN_ACTION_TEXT, note.getActionText());
            contentValues.put(DatabaseHelper.COLUMN_RESPONSE_TEXT, note.getResponseText());
            contentValues.put(DatabaseHelper.COLUMN_RESULT_TEXT, note.getResultText());
            sqLiteDatabase.insert(DatabaseHelper.TABLE, null, contentValues);
        }
        databaseHelper.close();
        if (BuildConfig.DEBUG) Log.d(TAG, "saveData() completed");
    }

    /**
     * Метод отображает стрелку назад на ActionBar.
     * Вызывается во многих других активити.
     *
     * @param actionBar соответствует getSupportActionBar()
     */
    public static void showHomeButtonOnActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Метод используется для однократного запуска WelcomeActivity(Splash) и IntroActivity
     *
     * @return true при первом запуске, false при повторном.
     */
    private boolean isFirstTimeStartApp() {
        SharedPreferences ref = getApplicationContext().getSharedPreferences(REF_KEY, MODE_PRIVATE);
        return ref.getBoolean(REF_FLAG, true);
    }

    /**
     * Метод вызывается во время первого запуска приложения в MainActivity после закрытия IntroActivity.
     * Если приложение закрыть до закрытия IntroActivity, то значение соответственно не изменится.
     *
     * @param status  false для блокировки
     * @param context getApplicationContext()
     */
    public void setFirstTimeStartStatus(boolean status, Context context) {
        SharedPreferences ref = context.getSharedPreferences(REF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean(REF_FLAG, status);
        editor.apply();
        if (BuildConfig.DEBUG) Log.d(TAG, "setFirstTimeStartStatus(" + status + ") completed");
    }

    /**
     * Метод изменяет прозрачность фонового рисунка с 1.0 до 0.2 в зависимости от
     * количества сделанных записей.
     * Также изменяется прозрачность сообщений с подсказками
     */
    private void setTransparentBackground() {
        float transparent_back;
        float transparent_hint;
        if (notes.size() < 6) {
            transparent_back = (1 - notes.size() / 10F) - 0.2F; // шаг 0.1
        } else transparent_back = 0.2F;
        if (notes.size() < 2) {
            transparent_hint = 1 - notes.size() / 1.5F; // шаг 0.67
        } else transparent_hint = 0F;
        findViewById(R.id.background_monster).setAlpha(transparent_back);
        findViewById(R.id.hint_help).setAlpha(transparent_hint);
        findViewById(R.id.hint_create).setAlpha(transparent_hint);
    }

   /* //Метод. Резервное форматирование базы
    private void deleteData() {
        DatabaseHelper db = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseHelper.TABLE, null, null);
        MainActivity.notes = null;
        db.close();
        Log.d(TAG, "deleteData() completed");
    }*/

}
