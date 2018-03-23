package com.example.maxim.picoto;


import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

public interface IImageView extends MvpView{
    void setImage(Bitmap bmp);
}
