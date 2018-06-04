package com.example.maxim.picoto.interfaces;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.maxim.picoto.presenters.MainPresenter;

public interface IRecyclerView extends MvpView{
    @StateStrategyType(SkipStrategy.class)
    void redrawRecyclerView();
}
