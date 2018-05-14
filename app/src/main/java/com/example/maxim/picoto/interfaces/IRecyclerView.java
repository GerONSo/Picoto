package com.example.maxim.picoto.interfaces;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by maxim on 23.03.18.
 */

public interface IRecyclerView extends MvpView{
    @StateStrategyType(SkipStrategy.class)
    void redrawRecyclerView();
}
