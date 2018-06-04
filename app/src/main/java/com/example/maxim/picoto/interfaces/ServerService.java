package com.example.maxim.picoto.interfaces;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ServerService {
    @Multipart
    @POST("{styleNumber}")
    Call<String> upload(@Part MultipartBody.Part filePart, @Path("styleNumber") String styleNumber);
}
