package com.example.maxim.picoto.data;

import android.graphics.Bitmap;

public class RecyclerViewData {

    private Bitmap image;
    private Bitmap crossed;
    private String name;
    private int styleNumber;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getStyleNumber() {
        return styleNumber;
    }

    public RecyclerViewData(Bitmap image, Bitmap crossed, String name, int styleNumber) {
        this.image = image;
        this.crossed = crossed;
        this.name = name;
        this.styleNumber = styleNumber;
    }
}
