package com.qualitasvita.soundmind.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.views.NewNoteView;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

@InjectViewState
public class NewNotePresenter extends MvpPresenter<NewNoteView> {

    @Inject
    NewNoteInteractor newNoteInteractor;
    private final Disposable disposable;

    public NewNotePresenter() {
        App.getComponent().inject(this);
        disposable = newNoteInteractor.getNoteSubject()
                .subscribe(value -> getViewState().saveNote(value));
    }

    public void positiveChecked(String positive) {
        newNoteInteractor.savePositive(positive);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }

    public void saveNote() {
        newNoteInteractor.saveNote();
    }

    public void reset() {
        newNoteInteractor.reset();
    }
}
