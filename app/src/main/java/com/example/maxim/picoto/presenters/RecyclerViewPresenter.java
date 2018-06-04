package com.example.maxim.picoto.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.maxim.picoto.interfaces.IRecyclerView;
import com.example.maxim.picoto.R;
import com.example.maxim.picoto.data.RecyclerViewData;

import java.io.File;
import java.util.ArrayList;

@InjectViewState
public class RecyclerViewPresenter extends MvpPresenter<IRecyclerView> {

    private ArrayList<RecyclerViewData> list;
    private Resources resources;
    private Context context;
    private MainPresenter mainPresenter;
    private int currentPosition = -1;

    public ArrayList<RecyclerViewData> getList() {
        return list;
    }

    public void setList() {         // Loading images for RecyclerView from /res/drawable folder
        list = new ArrayList<>();
        String names[] = resources.getStringArray(R.array.names);
        int j = 0;
        for (int i = 0; i < 31; i++) {
            if (i == 11 || i == 8) continue;
            String name = "p";
            if (i < 10) name += "0";
            name += String.valueOf(i);
            list.add(getRecyclerViewData(getId(name), names[j], i));
            j++;
        }
    }

    private RecyclerViewData getRecyclerViewData(int bitmapId, String name, int styleNumber) {      // Subsidiary method
        return new RecyclerViewData(BitmapFactory.decodeResource(resources, bitmapId),              // to create
                BitmapFactory.decodeResource(resources, R.id.cross), name, styleNumber);            // RecyclerViewData
    }

    public void redrawRecyclerView() {
        getViewState().redrawRecyclerView();
    }

    //  Just getters & setters lower

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public Resources getResources() {
        return resources;
    }

    private int getId(String s) {
        return resources.getIdentifier(s, "drawable", context.getPackageName());
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bitmap getImage() {
        return mainPresenter.getImage();
    }

    public void setRecyclerViewPresenter(MainPresenter mainPresenter) {
        mainPresenter.setRecyclerViewPresenter(this);
        this.setMainPresenter(mainPresenter);
    }

    public void setMainPresenter(MainPresenter presenter) {
        mainPresenter = presenter;
    }

    public File getImageFile() {
        return mainPresenter.getImageFile();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setProgressVisible() {
        mainPresenter.setProgressVisible();
    }

    public void setHighOpacity() {
        mainPresenter.setHighOpacity();
    }

    public void setProgressGone() {
        mainPresenter.setProgressGone();
    }

    public void setLowOpacity() {
        mainPresenter.setLowOpacity();
    }

    public void setImage(Bitmap image) {
        mainPresenter.setImage(image);
    }
}
