package com.example.maxim.picoto;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.io.File;
import java.util.List;

public class MainActivity extends MvpAppCompatActivity implements IMainView{

    @InjectPresenter
    MainPresenter presenter;

    RecyclerViewFragment recyclerViewFragment;
    ImageViewFragment imageViewFragment;

    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_GALLERY = 2;
    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 777;
    public static File FILES_DIR;
    private static final String CONTENT_AUTHORITY = "com.example.maxim.picoto.fileprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            FILES_DIR=getFilesDir();
            presenter.createFragment();
        }
    }


    @Override
    public void createFragment() {
        imageViewFragment=ImageViewFragment.newInstance();
        recyclerViewFragment=RecyclerViewFragment.newInstance();
        imageViewFragment.setMainPresenter(presenter);
        recyclerViewFragment.setMainPresenter(presenter);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.image_frame,imageViewFragment)
                .add(R.id.recycler_frame,recyclerViewFragment)
                .commit();


    }

    @Override
    public void requestImageFromCamera(File file) {

        Uri contentUri = FileProvider.getUriForFile(this, CONTENT_AUTHORITY, file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    presenter.onCameraImageReady();
                    break;
            }
        }
    }


}
