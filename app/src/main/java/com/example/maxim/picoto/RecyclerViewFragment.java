package com.example.maxim.picoto;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.example.maxim.picoto.helpers.HttpServerHelper;
import com.example.maxim.picoto.interfaces.IRecyclerView;
import com.example.maxim.picoto.presenters.MainPresenter;
import com.example.maxim.picoto.presenters.RecyclerViewPresenter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerViewFragment extends MvpAppCompatFragment implements IRecyclerView {

    @InjectPresenter(type = PresenterType.LOCAL)
    public RecyclerViewPresenter recyclerViewPresenter;
    @BindView(R.id.listView) public RecyclerView listView;

    private RecyclerViewAdapter adapter;
    private File styleImageFile;
    private static boolean isImageSetted = true;
    private MainPresenter mainPresenter;

    public RecyclerViewFragment() {}

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            if(mainPresenter != null) {
                recyclerViewPresenter.setRecyclerViewPresenter(mainPresenter);
            }
        }
        recyclerViewPresenter.setResources(getResources());
        recyclerViewPresenter.setContext(getContext());
        recyclerViewPresenter.setList();
        Log.d("name", String.valueOf(recyclerViewPresenter.getList().get(0).getName()));
        listView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapter=new RecyclerViewAdapter(view, this, recyclerViewPresenter, new RecyclerViewAdapter.OnStyleSelected() {
            @Override
            public void onStyleSelected(int position) {         // Handling style selected
                styleImageFile = recyclerViewPresenter.getImageFile();
                if(styleImageFile == null) {
                    ConstraintLayout layout = view.findViewById(R.id.recycler_view_fragment);
                    Snackbar snackbar = Snackbar.make(layout, "Select or make photo", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    return;
                }
                recyclerViewPresenter.setProgressVisible();
                recyclerViewPresenter.setHighOpacity();
                isImageSetted = false;
                HttpServerHelper.sendImage(styleImageFile, position, new HttpServerHelper.OnImageResult() {
                    @Override
                    public void onImageResult(Bitmap resultImage) {     // Send image for stylization

                        recyclerViewPresenter.setProgressGone();
                        recyclerViewPresenter.setLowOpacity();
                        Log.d("heii","here");
                        try {
                            recyclerViewPresenter.setImage(resultImage);
                            recyclerViewPresenter.setImageFile(getImageFile(resultImage, view));
                            isImageSetted = true;
                        } catch (NullPointerException e) {
                            ConstraintLayout layout = view.findViewById(R.id.recycler_view_fragment);
                            Snackbar snackbar = Snackbar.make(layout, "Something went wrong :(", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                    }
                });

            }
        });
        listView.setAdapter(adapter);
    }

    public RecyclerViewPresenter getRecyclerViewPresenter() {
        return recyclerViewPresenter;
    }

    public void redrawRecyclerView() {
        adapter.notifyDataSetChanged();
    }

    public static boolean isImageSetted() {
        return isImageSetted;
    }

    private File getImageFile(Bitmap resultImage, View view) {
        File imageFile = mainPresenter.getTempPhotoFile();
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            resultImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            ConstraintLayout layout = view.findViewById(R.id.recycler_view_fragment);
            Snackbar snackbar = Snackbar.make(layout, "Something went wrong :(", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        return imageFile;
    }
}
