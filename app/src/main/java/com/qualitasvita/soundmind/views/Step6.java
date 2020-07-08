package com.qualitasvita.soundmind.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.qualitasvita.soundmind.model.Answer;

import java.util.ArrayList;

public interface Step6 extends MvpView {
    @StateStrategyType(AddToEndStrategy.class)
    void setList(ArrayList<Answer> list);
}
