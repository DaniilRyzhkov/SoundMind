package com.qualitasvita.soundmind.di;

import com.qualitasvita.soundmind.adapters.EmotionsAdapter;
import com.qualitasvita.soundmind.adapters.IntroPagerAdapter;
import com.qualitasvita.soundmind.adapters.ResultAdapter;
import com.qualitasvita.soundmind.adapters.ThoughtAdapter;
import com.qualitasvita.soundmind.interactor.NewNoteInteractor;
import com.qualitasvita.soundmind.presenters.MainPresenter;
import com.qualitasvita.soundmind.presenters.NewNotePresenter;
import com.qualitasvita.soundmind.presenters.Step1_SituationPresenter;
import com.qualitasvita.soundmind.presenters.Step2_EmotionPresenter;
import com.qualitasvita.soundmind.presenters.Step4_ActionPresenter;
import com.qualitasvita.soundmind.presenters.Step5_ResponsePresenter;
import com.qualitasvita.soundmind.presenters.Step6_ResultPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, InteractorModule.class, DatabaseModule.class})
@Singleton
public interface AppComponent {

    void inject(MainPresenter mainPresenter);

    void inject(IntroPagerAdapter introPagerAdapter);

    void inject(NewNotePresenter newNotePresenter);


    void inject(Step1_SituationPresenter step1_situationPresenter);

    void inject(Step2_EmotionPresenter step2_emotionPresenter);

    void inject(EmotionsAdapter emotionsAdapter);

    void inject(ThoughtAdapter thoughtAdapter);

    void inject(Step4_ActionPresenter step4_actionPresenter);

    void inject(Step5_ResponsePresenter step5_responsePresenter);

    void inject(ResultAdapter resultAdapter);

    void inject(Step6_ResultPresenter step6_resultPresenter);

    void inject(NewNoteInteractor newNoteInteractor);

}
