package com.example.maxim.picoto;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
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

    public static ImageViewFragment newInstance(){
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
        Bitmap openGallery=null;
        if(savedInstanceState==null){

            openGallery= BitmapFactory.decodeResource(getResources(),R.drawable.no_image);
            presenter.setImage(openGallery);
            mainPresenter.setImageViewPresenter(presenter);
            presenter.setMainPresenter(mainPresenter);
        }

        imageView=view.findViewById(R.id.image_view);
        progressBar=view.findViewById(R.id.progress_bar);
        cameraButton=view.findViewById(R.id.camera_button);
        presenter.setImageView(imageView);

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
    }

}
