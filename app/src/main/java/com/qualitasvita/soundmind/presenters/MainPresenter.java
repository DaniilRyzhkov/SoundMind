package com.qualitasvita.soundmind.presenters;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qualitasvita.soundmind.activities.SettingsActivity;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.model.Note;
import com.qualitasvita.soundmind.repository.DatabaseHelper;
import com.qualitasvita.soundmind.views.MainView;

import java.util.ArrayList;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    Context context;

    @Inject
    NewNoteInteractor newNoteInteractor;

    public MainPresenter(){
        App.getComponent().inject(this);
    }

    private static ArrayList<Note> notes = new ArrayList<>(); // БД в виде списка
    private static final String REF_KEY = "IntroSlider";
    private static final String REF_FLAG = "FirstTimeStartFlag";

    /**
     * Метод вызывается один раз при запуске приложения.
     * Метод запрашивает у базы данных значения семи столбцов,
     * после чего вызывается конструктор Note(), полученный объект добавляется в список notes.
     * На протяжении работы приложения все запросы к данным будут осуществяться через этот список.
     */
    public void loadData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
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
    }

    /**
     * Переписать на room!
     * Метод вызывается при изменении списка notes(добавление записи/удаление записи)
     * На данный момент происходит перезапись всей базы данных,
     * в дальнейшем это будет исправлено на апгрейд базы.
     */
    public void saveData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
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
    }

    public void addNote(Note newNote) {
        notes.add(0, newNote);
    }

    public void removeNote(int position) {
        notes.remove(position);
    }

    public void refreshList() {
        getViewState().refreshList(notes);
    }

    public void refreshBackgroundVisibility() {
        SharedPreferences settings = context.getSharedPreferences(SettingsActivity.PREF_SETTINGS, MODE_PRIVATE);
        boolean status = settings.getBoolean(SettingsActivity.PREF_SOLID_BACKGROUND, false);
        getViewState().refreshBackgroundVisibility(status);
    }

    public void refreshBackgroundTransparent() {
        getViewState().refreshBackgroundTransparent(notes);
    }

    public void startCompletedNoteActivity() {
        getViewState().startCompletedNoteActivity(notes.get(0));
    }

    /**
     * Метод используется для однократного запуска WelcomeActivity(Splash) и IntroActivity
     */
    public void checkFirstStart() {
        SharedPreferences ref = context.getSharedPreferences(REF_KEY, MODE_PRIVATE);
        if (ref.getBoolean(REF_FLAG, true)) {
            SharedPreferences.Editor editor = ref.edit();
            editor.putBoolean(REF_FLAG, false);
            editor.apply();

            // Показать туториал
            getViewState().startWelcomeActivity();
        }
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
