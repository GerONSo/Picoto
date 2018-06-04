package com.example.maxim.picoto.utils;


import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {                      //Subsidiary class for creating temp files
    private static final String CONTENT_AUTHORITY = "com.example.maxim.picoto.fileprovider";

    public static File getNewImageFile(File dir, String prefix, String suffix) {
        return new File(dir, prefix +
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) +
                suffix);
    }
}
