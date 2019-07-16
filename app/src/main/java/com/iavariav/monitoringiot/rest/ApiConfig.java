package com.iavariav.monitoringiot.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {

    public static ApiService getApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://139.180.220.65/android-api/")
//                .baseUrl("http://sig.upgris.ac.id/api_iav/sertifikasi_android/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service =retrofit.create(ApiService.class);
        return service;
    }
}
