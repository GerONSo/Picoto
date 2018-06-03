package com.example.maxim.picoto;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.example.maxim.picoto.interfaces.IMainView;
import com.example.maxim.picoto.presenters.MainPresenter;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends MvpAppCompatActivity implements IMainView {

    @InjectPresenter(type = PresenterType.GLOBAL)
    MainPresenter presenter;

    RecyclerViewFragment recyclerViewFragment;
    ImageViewFragment imageViewFragment;

    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_GALLERY = 2;
    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 777;
    public static final int REQUEST_PERMISSION_CAMERA = 666;
    public static File FILES_DIR;
    private Uri cropResultUri;
    private static final String CONTENT_AUTHORITY = "com.example.maxim.picoto.fileprovider";

    private File imageTempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setLogo(R.drawable.picoto_logo);
        toolbar.setContentInsetsAbsolute(0, 0);

        if (savedInstanceState == null) {
            FILES_DIR = getFilesDir();
            presenter.createFragment();
        }
        checkPermissions();
    }


    void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSION_CAMERA);
        }
    }

    @Override
    public void createFragment() {
        imageViewFragment = ImageViewFragment.newInstance();
        recyclerViewFragment = RecyclerViewFragment.newInstance();
        imageViewFragment.setMainPresenter(presenter);
        recyclerViewFragment.setMainPresenter(presenter);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.image_frame, imageViewFragment)
                .add(R.id.recycler_frame, recyclerViewFragment)
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
    public void requestImageFromGallery(File file) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                .setType("image/*");
        imageTempFile = file;
        startActivityForResult(pickIntent, REQUEST_GALLERY);
    }

    @Override
    public void getTempPhotoFile() {

    }

    @Override
    public void setImage(Bitmap image) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                cropResultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), cropResultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                presenter.setImage(bitmap);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    presenter.onCameraImageReady();
                    break;
                case REQUEST_GALLERY:
                    Uri uri = data.getData();
                    createFileByContentUri(uri, imageTempFile);
                    break;
            }
        }
    }

    @Override
    public void createFileByContentUri(Uri src, File dst) {
        try (InputStream in = getContentResolver().openInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error creating File by Content Uri", Toast.LENGTH_SHORT).show();
        }
        presenter.onGalleryImageReady();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ConstraintLayout layout = findViewById(R.id.great_layout);
        switch (item.getItemId()) {
            case R.id.save:
                saveImage();
                Snackbar snackbar = Snackbar.make(layout, "Successfuly saved", Snackbar.LENGTH_SHORT);
                snackbar.show();
                break;
            case R.id.share:
                String imagePath = presenter.getImageFile().getAbsolutePath();
                createInstagramIntent(imagePath);
                break;
            case R.id.crop:
                onSelectImageClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveImage() {
        presenter.saveImage();
    }

    private void createInstagramIntent(String mediaPath) {
        Log.d("mylog", mediaPath);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share to"));
    }

    public void onSelectImageClick() {
        startCropImageActivity(Uri.fromFile(presenter.getImageFile()));
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }


}
