package com.example.maxim.picoto;


import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;

public interface IMainView extends MvpView{
    @StateStrategyType(OneExecutionStateStrategy.class)
    void createFragment();
    @StateStrategyType(SkipStrategy.class)
    void requestImageFromCamera(File fileName);
    @StateStrategyType(SkipStrategy.class)
    void getTempPhotoFile();
    @StateStrategyType(SingleStateStrategy.class)
    void setImage(Bitmap image);
}
