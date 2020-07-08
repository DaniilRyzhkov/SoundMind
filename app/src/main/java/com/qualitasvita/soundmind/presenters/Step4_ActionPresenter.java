package com.qualitasvita.soundmind.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.views.Step4;

import javax.inject.Inject;

@InjectViewState
public class Step4_ActionPresenter extends MvpPresenter<Step4> {

    @Inject
    NewNoteInteractor newNoteInteractor;

    public Step4_ActionPresenter() {
        App.getComponent().inject(this);
    }

    public void readActionText(String actionText) {
        newNoteInteractor.saveActionText(actionText);
    }
}
