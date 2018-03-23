package com.example.maxim.picoto;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;


public class ImageViewFragment extends MvpAppCompatFragment implements IImageView {

    @InjectPresenter
    ImageViewPresenter presenter;

    ImageView imageView;
    ProgressBar progressBar;
    FloatingActionButton cameraButton;

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
        if(savedInstanceState==null){
            Bitmap openGallery= BitmapFactory.decodeResource(getResources(),R.drawable.no_image);
            presenter.setImage(openGallery);
        }
        imageView=view.findViewById(R.id.image_view);
        progressBar=view.findViewById(R.id.progress_bar);
        cameraButton=view.findViewById(R.id.camera_button);
    }

    @Override
    public void setImage(Bitmap bmp) {
        imageView.setImageBitmap(bmp);
    }


}
