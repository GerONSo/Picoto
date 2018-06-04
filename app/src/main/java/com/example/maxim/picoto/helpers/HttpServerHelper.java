package com.example.maxim.picoto.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.example.maxim.picoto.interfaces.ServerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpServerHelper {

    public interface OnImageResult {
        void onImageResult(Bitmap resultImage);
    }

    private static Bitmap resultImage;
    private static OnImageResult callback;

    public static void sendImage(File file, int styleType, OnImageResult c) {

        callback = c;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://194.87.103.212:4567/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build();
        ServerService api = retrofit.create(ServerService.class);
        MultipartBody.Part filePart = MultipartBody.Part.
                createFormData("file", file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file));
        Call<String> call = api.upload(filePart, String.valueOf(styleType));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    String s = response.body();
                    byte[] bytes = Base64.decode(s, Base64.DEFAULT);
                    resultImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    callback.onImageResult(resultImage);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onImageResult(null);
            }
        });
    }

    private static OkHttpClient getHttpClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .build();
        return okHttpClient;
    }
}
