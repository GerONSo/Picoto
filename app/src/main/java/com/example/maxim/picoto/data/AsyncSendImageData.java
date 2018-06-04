package com.example.maxim.picoto.data;

import android.graphics.Bitmap;

import java.io.File;

public class AsyncSendImageData {
    private File file;
    private int position;

    public AsyncSendImageData(File file, int position) {
        this.file = file;
        this.position = position;
    }

    public File getFile() {
        return file;
    }

    public int getPosition() {
        return position;
    }
}
