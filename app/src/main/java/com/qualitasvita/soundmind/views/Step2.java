package com.qualitasvita.soundmind.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface Step2 extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showSeekBar(Integer itemPosition);
    @StateStrategyType(AddToEndStrategy.class)
    void hideSeekBar();
    @StateStrategyType(AddToEndStrategy.class)
    void refreshList(int position);
    @StateStrategyType(AddToEndStrategy.class)
    void setList();

}
