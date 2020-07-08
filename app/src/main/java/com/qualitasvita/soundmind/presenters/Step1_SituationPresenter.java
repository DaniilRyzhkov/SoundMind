package com.qualitasvita.soundmind.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.views.Step1;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

@InjectViewState
public class Step1_SituationPresenter extends MvpPresenter<Step1> {

    @Inject
    NewNoteInteractor newNoteInteractor;
    private final Disposable disposable;

    public Step1_SituationPresenter() {
        App.getComponent().inject(this);
        disposable = newNoteInteractor.getSituationSubject()
                .subscribe(value -> getViewState().readSituationText(value));
    }

    // Передаем набранный текст в Интерактор
    public void saveSituationText(String text) {
        newNoteInteractor.saveSituationText(text);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }
}
