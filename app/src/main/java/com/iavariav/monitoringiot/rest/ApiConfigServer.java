package com.iavariav.monitoringiot.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfigServer {

    public static ApiService getApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sig.upgris.ac.id/api_iav/")
//                .baseUrl("http://sig.upgris.ac.id/api_iav/sertifikasi_android/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service =retrofit.create(ApiService.class);
        return service;
    }
}
