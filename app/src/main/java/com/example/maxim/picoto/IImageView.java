package com.example.maxim.picoto;


import android.graphics.Bitmap;
import android.graphics.Point;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface IImageView extends MvpView{
    @StateStrategyType(AddToEndSingleStrategy.class)
    void setImage(Bitmap bmp);

}
