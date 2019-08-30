package com.iavariav.monitoringiot.rest;

import com.iavariav.monitoringiot.model.ResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api_get.php")
    Call<ResponseModel> ambilData();

    @GET("firebase")
    Call<ResponseBody> postData(
            @Query("title") String title,
            @Query("message") String message,
            @Query("push_type") String push_type,
            @Query("regId") String regId
    );
}
