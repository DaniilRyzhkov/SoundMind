package com.qualitasvita.soundmind.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.views.Step5;

import javax.inject.Inject;

@InjectViewState
public class Step5_ResponsePresenter extends MvpPresenter<Step5> {

    @Inject
    NewNoteInteractor newNoteInteractor;

    public Step5_ResponsePresenter() {
        App.getComponent().inject(this);
    }

    public void readResponseText(String text){
        newNoteInteractor.saveResponseText(text);
    }
}
