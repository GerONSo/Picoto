package com.example.maxim.picoto.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.maxim.picoto.data.AsyncSendImageData;
import com.example.maxim.picoto.interfaces.ServerService;
import com.example.maxim.picoto.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpServerHelper {

    public interface OnImageResult{
        void onImageResult(Bitmap image);
    }

    private static OnImageResult callback;
    private static Bitmap resultImage;

    public static void sendImage(Bitmap image, int styleType, ServerHelper.OnImageResult c) {
        new AsyncSendImage().execute(new AsyncSendImageData(image, styleType));
    }

    static class AsyncSendImage extends AsyncTask<AsyncSendImageData, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(AsyncSendImageData... data) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://194.87.103.212:4567/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServerService api = retrofit.create(ServerService.class);
            File file = new File("/home/maxim/IdeaProjects/content.jpg");
            MultipartBody.Part filePart = MultipartBody.Part.
                    createFormData("file", file.getName(),
                            RequestBody.create(MediaType.parse("image/*"), file));
            Call<String> call = api.upload(filePart);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body() != null) {
                        String s = response.body();
                        byte[] bytes = Base64.decode(s, 0);
                        resultImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        System.out.println(response.body());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println(t.getMessage());
                    System.out.println("Failed");
                }
            });
            return resultImage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d("tag", String.valueOf(bitmap));
            callback.onImageResult(bitmap);

        }
    }
}
