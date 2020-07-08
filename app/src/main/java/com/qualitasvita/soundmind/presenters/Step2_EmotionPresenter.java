package com.qualitasvita.soundmind.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.qualitasvita.soundmind.di.App;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.model.Answer;
import com.qualitasvita.soundmind.views.Step2;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.Disposable;

@InjectViewState
public class Step2_EmotionPresenter extends MvpPresenter<Step2> {

    @Inject
    NewNoteInteractor newNoteInteractor;
    private final Disposable disposable;

    public Step2_EmotionPresenter() {
        App.getComponent().inject(this);
        disposable = newNoteInteractor.getEmotionAdapterSubject()
                .subscribe(value -> getViewState().showSeekBar(value));
    }

    public void saveEmotions(ArrayList<Answer> list, int currentPosition){
        newNoteInteractor.saveEmotionsArray(list);
        getViewState().hideSeekBar();
        getViewState().refreshList(currentPosition);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }

    public ArrayList<Answer> getEmotions(){
        return newNoteInteractor.getEmotions();
    }
}
