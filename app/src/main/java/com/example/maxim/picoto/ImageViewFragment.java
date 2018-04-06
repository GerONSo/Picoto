package com.example.maxim.picoto;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class ImageViewFragment extends MvpAppCompatFragment implements IImageView {

    @InjectPresenter
    ImageViewPresenter presenter;


    private MainPresenter mainPresenter;


    private ImageView imageView;
    private ProgressBar progressBar;
    private FloatingActionButton cameraButton;

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    public ImageViewFragment() {
        // Required empty public constructor
    }

    public static ImageViewFragment newInstance() {
        return new ImageViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bitmap openGallery = null;
        if (savedInstanceState == null) {
            presenter.setMainPresenter(mainPresenter);
            openGallery = BitmapFactory.decodeResource(getResources(), R.drawable.no_image);
            presenter.setImage(openGallery);


        }

        imageView = view.findViewById(R.id.image_view);
        progressBar = view.findViewById(R.id.progress_bar);
        cameraButton = view.findViewById(R.id.camera_button);
        presenter.setImageView(imageView);
        //progressBar.setVisibility(View.VISIBLE);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setCameraImage();
            }
        });
    }

    @Override
    public void setImage(Bitmap bmp) {
        imageView.setImageBitmap(bmp);
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
        this.mainPresenter.setImageViewPresenter(presenter);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setProgressVisible() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setProgressGone() {
        progressBar.setVisibility(View.GONE);
    }

    public void setLowOpacity() {
        imageView.setAlpha((float) 1.0);
    }

    public void setHighOpacity() {
        imageView.setAlpha((float) 0.5);
    }

    public void saveImage() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Picoto");
        if (!dir.isDirectory()) {
            if (dir.mkdir())
                Log.d("dir", dir.getAbsolutePath());
            else
                Log.d("!dir", dir.getAbsolutePath());
        }
        Bitmap image = ((BitmapDrawable) getImageView().getDrawable()).getBitmap();
        File imageFile = FileUtils.getNewImageFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Picoto/"), "pic_", ".jpg");
        try {
            if (!imageFile.isFile())
                imageFile.createNewFile();
            Log.d("save image", imageFile.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
