package com.example.maxim.picoto;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.io.IOException;

@InjectViewState
public class ImageViewPresenter extends MvpPresenter<IImageView>{



    private MainPresenter mainPresenter;
    private ImageView imageView;


    public void setImage(Bitmap bmp){
        getViewState().setImage(bmp);
    }


    public void setImage(File file) {
        File mainImage;
        mainImage = file;
        Point size = new Point();
        size.set(imageView.getWidth(),imageView.getHeight());
        int maxSide = Math.max(size.x, size.y);
        Bitmap bitmap = null;
        Log.d("mytag", maxSide+" "+maxSide);
        try {
            bitmap = ImageUtils.getScaledBitmap(mainImage, maxSide, maxSide);
        } catch (IOException e) {

        }
        setImage(bitmap);
    }

    public void setCameraImage(){
        //Log.d("tag", String.valueOf(mainPresenter));
        mainPresenter.getCameraImage(new MainPresenter.OnActivityResultListener() {
            @Override
            public void onActivityResultListener(File file) {
                setImage(file);
            }
        });

    }

    public void setMainPresenter(MainPresenter mainPresenter) {

        this.mainPresenter = mainPresenter;
        mainPresenter.setImageViewPresenter(this);

    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Bitmap getImage(){
        return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }

    public void setProgressVisible(){
        getViewState().setProgressVisible();
    }

    public void setProgressGone(){
        getViewState().setProgressGone();
    }
    public void setLowOpacity(){
        getViewState().setLowOpacity();
    }
    public void setHighOpacity(){
        getViewState().setHighOpacity();
    }
}
