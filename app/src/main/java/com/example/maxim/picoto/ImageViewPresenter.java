package com.example.maxim.picoto;


import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class ImageViewPresenter extends MvpPresenter<IImageView>{

    public void setImage(Bitmap bmp){
        getViewState().setImage(bmp);
    }
}
