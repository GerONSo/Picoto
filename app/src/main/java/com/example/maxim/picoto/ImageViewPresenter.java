package com.example.maxim.picoto;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class ImageViewPresenter extends MvpPresenter<IImageView>{




    public void setImage(Bitmap bmp){
        getViewState().setImage(bmp);
    }


}
