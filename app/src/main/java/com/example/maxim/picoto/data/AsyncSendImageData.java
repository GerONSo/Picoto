package com.example.maxim.picoto.data;


import android.graphics.Bitmap;

public class AsyncSendImageData {
    private Bitmap image;
    private int position;

    public AsyncSendImageData(Bitmap image, int position) {
        this.image = image;
        this.position = position;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getPosition() {
        return position;
    }
}
