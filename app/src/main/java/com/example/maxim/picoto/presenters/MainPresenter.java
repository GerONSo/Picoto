package com.example.maxim.picoto.presenters;


import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.maxim.picoto.utils.FileUtils;
import com.example.maxim.picoto.interfaces.IMainView;

import java.io.File;

import static com.example.maxim.picoto.MainActivity.FILES_DIR;

@InjectViewState
public class MainPresenter extends MvpPresenter<IMainView> {

    interface OnActivityResultListener {
        void onActivityResultListener(File file);
    }

    private OnActivityResultListener callback;
    private File file;
    private RecyclerViewPresenter recyclerViewPresenter;
    private ImageViewPresenter imageViewPresenter;



    public void createFragment() {
        getViewState().createFragment();
    }

    public void getCameraImage(OnActivityResultListener listener) {    // Start camera activity
        callback = listener;
        file = getTempPhotoFile();
        getViewState().requestImageFromCamera(file);
    }

    public void onCameraImageReady() {                  // Handle camera activity result
        callback.onActivityResultListener(file);
    }

    public void onGalleryImageReady() {                 // Handle gallery activity result
        callback.onActivityResultListener(file);
    }

    private File getTempPhotoFile() {                          // Create Temp File
        File filesDir = FILES_DIR;
        return FileUtils.getNewImageFile(filesDir, "tmp_", ".jpg");
    }

    public void openGallery(OnActivityResultListener callback) {    // Start gallery activity
        this.callback = callback;
        file = getTempPhotoFile();
        getViewState().requestImageFromGallery(file);
    }

    //  Just getters & setters lower

    public Bitmap getImage() {
        return imageViewPresenter.getImage();
    }

    public void setImage(Bitmap image) {
        imageViewPresenter.setImage(image);
    }


    public void setProgressVisible() {
        imageViewPresenter.setProgressVisible();
    }

    public void setProgressGone() {
        imageViewPresenter.setProgressGone();
    }

    public void setLowOpacity() {
        imageViewPresenter.setLowOpacity();
    }

    public void setHighOpacity() {
        imageViewPresenter.setHighOpacity();
    }

    public void saveImage() {
        imageViewPresenter.saveImage();
    }

    public File getImageFile() {
        return imageViewPresenter.getImageFile();
    }

    public void setRecyclerViewPresenter(RecyclerViewPresenter recyclerViewPresenter) {
        this.recyclerViewPresenter = recyclerViewPresenter;
    }

    public void setImageViewPresenter(ImageViewPresenter imageViewPresenter) {
        this.imageViewPresenter = imageViewPresenter;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setCroppedImage(Bitmap bmp) {
        imageViewPresenter.setCroppedImage(bmp);
    }
}
