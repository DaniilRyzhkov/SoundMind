package com.qualitasvita.soundmind.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.model.Answer;
import com.qualitasvita.soundmind.views.Step6;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

@InjectViewState
public class Step6_ResultPresenter extends MvpPresenter<Step6> {

    @Inject
    NewNoteInteractor newNoteInteractor;
    private final Disposable disposable;


    public Step6_ResultPresenter() {
        App.getComponent().inject(this);
        // подписываемся на источник чего:
        disposable = newNoteInteractor.getResultSubject()
                .subscribe(value -> getViewState().setList(value));
    }

    public void saveNote(){
        newNoteInteractor.saveNote();
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }

    public ArrayList<Answer> getResults(){
        return newNoteInteractor.getResults();
    }
}
