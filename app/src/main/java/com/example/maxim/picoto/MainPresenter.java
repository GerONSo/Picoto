package com.example.maxim.picoto;


import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;

import static com.example.maxim.picoto.MainActivity.FILES_DIR;

@InjectViewState
public class MainPresenter extends MvpPresenter<IMainView>{

    interface OnActivityResultListener{
        void onActivityResultListener(File file);
    }

    private OnActivityResultListener callback;
    private File file;
    private RecyclerViewPresenter recyclerViewPresenter;
    private ImageViewPresenter imageViewPresenter;

    public void setRecyclerViewPresenter(RecyclerViewPresenter recyclerViewPresenter) {
        this.recyclerViewPresenter = recyclerViewPresenter;
    }

    public void setImageViewPresenter(ImageViewPresenter imageViewPresenter) {
        this.imageViewPresenter = imageViewPresenter;
    }

    public void createFragment(){
        getViewState().createFragment();
    }

    public void getCameraImage(OnActivityResultListener listener){
        callback=listener;
        file=getTempPhotoFile();
        getViewState().requestImageFromCamera(file);
    }

    public void onCameraImageReady(){
        callback.onActivityResultListener(file);
    }

    private File getTempPhotoFile(){
        File filesDir=FILES_DIR;
        return FileUtils.getNewImageFile(filesDir, "tmp_", ".jpg");
    }

    public Bitmap getImage(){
        return imageViewPresenter.getImage();
    }

    public void setImage(Bitmap image){
        imageViewPresenter.setImage(image);
    }

    public void setProgressVisible(){
        imageViewPresenter.setProgressVisible();
    }
    public void setProgressGone(){
        imageViewPresenter.setProgressGone();
    }
    public void setLowOpacity(){
        imageViewPresenter.setLowOpacity();
    }
    public void setHighOpacity(){
        imageViewPresenter.setHighOpacity();
    }
}
