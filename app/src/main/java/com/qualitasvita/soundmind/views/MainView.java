package com.qualitasvita.soundmind.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.qualitasvita.soundmind.model.Note;

import java.util.ArrayList;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    //void startToWork();

    void refreshList(ArrayList<Note> notes);

    void refreshBackgroundTransparent(ArrayList<Note> notes);

    void refreshBackgroundVisibility(boolean status);

    void startCompletedNoteActivity(Note note);

    void startWelcomeActivity();

}
