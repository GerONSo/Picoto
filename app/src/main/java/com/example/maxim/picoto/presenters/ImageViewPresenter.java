package com.example.maxim.picoto.presenters;


import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.maxim.picoto.R;
import com.example.maxim.picoto.interfaces.IImageView;
import com.example.maxim.picoto.utils.ImageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@InjectViewState
public class ImageViewPresenter extends MvpPresenter<IImageView> {


    private MainPresenter mainPresenter;
    private ImageView imageView;
    private Bitmap imageStyle;
    private File imageFile;
    private View imageFragmentView;

    public void setImage(Bitmap bmp) {              // Set image to ImageView by Bitmap
        getViewState().setImage(bmp);
    }

    public void setCroppedImage(Bitmap bmp) {       // Set cropped image to ImageView
        imageStyle = bmp;
        imageFile = mainPresenter.getTempPhotoFile();
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            ConstraintLayout layout = imageFragmentView.findViewById(R.id.image_view_fragment);
            Snackbar snackbar = Snackbar.make(layout, "Something went wrong :(", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        setImage(bmp);
    }

    public void setImage(File file) {               // Set image to ImageView by file
        File mainImage;
        mainImage = file;
        Point size = new Point();
        size.set(imageView.getWidth(), imageView.getHeight());
        int maxSide = Math.max(size.x, size.y);
        Bitmap bitmap = null;
        Log.d("mytag", maxSide + " " + maxSide);
        try {
            bitmap = ImageUtils.getScaledBitmap(mainImage, maxSide, maxSide);
        } catch (IOException e) {
            ConstraintLayout layout = imageFragmentView.findViewById(R.id.image_view_fragment);
            Snackbar snackbar = Snackbar.make(layout, "Something went wrong :(", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        imageFile = mainImage;
        imageStyle = bitmap;
        setImage(bitmap);
    }

    public void setCameraImage() {
        mainPresenter.getCameraImage(new MainPresenter.OnActivityResultListener() {         // Starting camera activity
            @Override
            public void onActivityResultListener(File file) {
                setImage(file);
            }
        });
    }

    public void openGallery() {                                                          // Starting gallery activity
        mainPresenter.openGallery(new MainPresenter.OnActivityResultListener() {
            @Override
            public void onActivityResultListener(File file) {
                setImage(file);
            }
        });
    }

    //  Just getters & setters lower

    public void setImageViewPresenter(MainPresenter mainPresenter) {
        mainPresenter.setImageViewPresenter(this);
        this.setMainPresenter(mainPresenter);
    }

    public void setMainPresenter(MainPresenter presenter) {
        mainPresenter = presenter;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Bitmap getImage() {
        //Log.d("style", String.valueOf(imageStyle.getWidth()));
        return imageStyle;
    }

    public void setProgressVisible() {
        getViewState().setProgressVisible();
    }

    public void setProgressGone() {
        getViewState().setProgressGone();
    }

    public void setLowOpacity() {
        getViewState().setLowOpacity();
    }

    public void setHighOpacity() {
        getViewState().setHighOpacity();
    }

    public void saveImage() {
        getViewState().saveImage();
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File file) {
        imageFile = file;
    }

    public void setImageFragmentView(View imageFragmentView) {
        this.imageFragmentView = imageFragmentView;
    }
}
