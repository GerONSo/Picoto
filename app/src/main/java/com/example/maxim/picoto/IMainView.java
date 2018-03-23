package com.example.maxim.picoto;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface IMainView extends MvpView{
    @StateStrategyType(OneExecutionStateStrategy.class)
    void createFragment()  ;
}
