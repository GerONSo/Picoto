package com.example.maxim.picoto.interfaces;


import android.graphics.Bitmap;
import android.graphics.Point;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface IImageView extends MvpView{
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setImage(Bitmap bmp);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setProgressVisible();
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setProgressGone();
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setLowOpacity();
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setHighOpacity();
    @StateStrategyType(AddToEndSingleStrategy.class)
    void saveImage();
}
