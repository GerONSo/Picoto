package com.example.maxim.picoto;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.maxim.picoto.interfaces.IImageView;
import com.example.maxim.picoto.presenters.ImageViewPresenter;
import com.example.maxim.picoto.presenters.MainPresenter;
import com.example.maxim.picoto.utils.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.maxim.picoto.RecyclerViewFragment.isImageSetted;


public class ImageViewFragment extends MvpAppCompatFragment implements IImageView {

    @InjectPresenter ImageViewPresenter presenter;
    @BindView(R.id.image_view) public ImageView imageView;
    @BindView(R.id.progress_bar) public ProgressBar progressBar;
    @BindView(R.id.camera_button) public FloatingActionButton cameraButton;

    private View imageFragmentView;
    private MainPresenter mainPresenter;

    public ImageViewFragment() {}

    public static ImageViewFragment newInstance() {
        return new ImageViewFragment();
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        imageFragmentView = view;
        Bitmap openGallery = null;
        if (savedInstanceState == null) {
            if(mainPresenter != null) {
                presenter.setImageViewPresenter(mainPresenter);
            }
            openGallery = BitmapFactory.decodeResource(getResources(), R.drawable.no_image);
            presenter.setImage(openGallery);
        }
        presenter.setImageFragmentView(imageFragmentView);
        presenter.setImageView(imageView);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.setCameraImage();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isImageSetted()) presenter.openGallery();
                else {
                    ConstraintLayout layout = imageFragmentView.findViewById(R.id.image_view_fragment);
                    Snackbar snackbar = Snackbar.make(layout, "Wait until image will be stylized, please", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }

    @Override
    public void setImage(Bitmap bmp) {
        imageView.setImageBitmap(bmp);
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
            ConstraintLayout layout = imageFragmentView.findViewById(R.id.image_view_fragment);
            Snackbar snackbar = Snackbar.make(layout, "Something went wrong :(", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }


}
