package com.qualitasvita.soundmind.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.qualitasvita.soundmind.R;
import com.qualitasvita.soundmind.adapters.MainAdapter;
import com.qualitasvita.soundmind.model.Note;
import com.qualitasvita.soundmind.presenters.MainPresenter;
import com.qualitasvita.soundmind.views.MainView;

import java.util.ArrayList;

/**
 * Главное активити, категория LAUNCHER
 *
 *
 * @author Daniil Ryzhkov
 */
public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    private static final int REQUEST_CODE_NEW_NOTE = 0;
    public static final int REQUEST_CODE_COMPLETED_NOTE = 9;
    public static final String EXTRA_NOTE_POSITION = "com.qualitasvita.soundmind.note_position";

    ImageView background;
    //TextView hintHelp, hintCreate;
    RecyclerView listNote;
    BottomAppBar bottomAppBar;

    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bottomMain);
        setSupportActionBar(bottomAppBar);

        // Подготовка к работе
        mainPresenter.checkFirstStart();
        mainPresenter.loadData();

        // Определение view
        background = findViewById(R.id.background_mind_fullness);
        //hintHelp = findViewById(R.id.hint_help);
        //hintCreate = findViewById(R.id.hint_create);
        listNote = findViewById(R.id.listNote);

        // Отобразить актуальный список записей
        mainAdapter = new MainAdapter();
        listNote.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        listNote.setHasFixedSize(true);
        listNote.setAdapter(mainAdapter);
    }

    /**
     * Обновление данных, через обращение к mainPresenter
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Обновить список записей
        mainPresenter.refreshList();
        // Обновить прозрачность фонового рисунка в зависимости от количества сделанных записей
        mainPresenter.refreshBackgroundTransparent();
        // Обновить видимость фонового рисунка в зависимости от установленных настроек
        mainPresenter.refreshBackgroundVisibility();
        // Отобразить BottomAppBar
        bottomAppBar.performShow();
    }

    /**
     * Метод вызывается mainPresenter
     *
     * @param notes список записей
     */
    @Override
    public void refreshList(ArrayList<Note> notes) {
        listNote.setAdapter(mainAdapter);
        mainAdapter.setList(notes);
    }

    /**
     * Метод вызывается mainPresenter
     * Изменить прозрачность фонового рисунка с 0.8 до 0.2 в зависимости от
     * количества сделанных записей
     * Изменить видимость сообщений с подсказками
     */
    @Override
    public void refreshBackgroundTransparent(ArrayList<Note> notes) {
        float transparent_back;
        if (notes.size() < 6) {
            transparent_back = (1 - notes.size() / 10F) - 0.2F; // шаг 0.1
        } else transparent_back = 0.2F;
        /*if (notes.size() < 1) {
            hintHelp.setVisibility(View.VISIBLE);
            hintCreate.setVisibility(View.VISIBLE);
        } else {
            hintHelp.setVisibility(View.GONE);
            hintCreate.setVisibility(View.GONE);
        }*/
        background.setAlpha(transparent_back);
    }

    /**
     * Метод вызывается mainPresenter
     *
     * @param status SettingsActivity.PREF_SOLID_BACKGROUND
     */
    @Override
    public void refreshBackgroundVisibility(boolean status) {
        if (status) {
            background.setVisibility(View.GONE);
        } else {
            background.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Сохранить запись, открыть запись
        if (requestCode == REQUEST_CODE_NEW_NOTE) {
            if (resultCode == RESULT_OK) {
                Bundle arguments = data.getExtras();
                if (arguments != null) {
                    Note newNote = (Note) arguments.getSerializable(Note.class.getSimpleName());
                    mainPresenter.addNote(newNote);
                    mainPresenter.saveData();
                    Toast.makeText(this, R.string.toast_saved, Toast.LENGTH_SHORT).show();
                    mainPresenter.startCompletedNoteActivity();
                }
            }
        }
        // Удалить запись
        if (requestCode == REQUEST_CODE_COMPLETED_NOTE) {
            if (resultCode == RESULT_OK) {
                boolean deleteThisNote = data.getBooleanExtra(CompletedNoteActivity.EXTRA_DELETE_THIS_NOTE, false);
                if (deleteThisNote) {
                    int position = data.getIntExtra(EXTRA_NOTE_POSITION, -1);
                    if (position >= 0) {
                        mainPresenter.removeNote(position);
                        mainPresenter.saveData();
                        Toast.makeText(this, R.string.toast_deleted, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * Нажатие на FloatingActionButton "Новая запись", параметр "onClick"
     *
     * @param view id/fabNewNote
     */
    public void startNewNoteActivity(View view) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NEW_NOTE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_main) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu_main);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.info:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            /*case R.id.intro:
                startActivity(new Intent(this, IntroActivity.class));
                break;*/
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void startWelcomeActivity() {
        startActivity(new Intent(this, WelcomeActivity.class));
    }

    @Override
    public void startCompletedNoteActivity(Note note) {
        Intent intent = new Intent(this, CompletedNoteActivity.class);
        intent.putExtra(Note.class.getSimpleName(), note);
        intent.putExtra(EXTRA_NOTE_POSITION, 0);
        startActivityForResult(intent, REQUEST_CODE_COMPLETED_NOTE);
    }
}
