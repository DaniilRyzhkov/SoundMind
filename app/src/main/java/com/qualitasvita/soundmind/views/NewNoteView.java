package com.qualitasvita.soundmind.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.qualitasvita.soundmind.model.Note;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface NewNoteView extends MvpView {

    void saveNote(Note note);
}