package com.iavariav.monitoringiot.rest;

import com.iavariav.monitoringiot.model.ResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("api_get.php")
    Call<ResponseModel> ambilData();

    @FormUrlEncoded
    @POST("api_login.php")
    Call<ResponseBody> login(@Field("username") String username,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("api_hapus_barang.php")
    Call<ResponseBody> deleteData(@Field("id_barang") String id);

    @FormUrlEncoded
    @POST("api_tambah_barang.php")
    Call<ResponseBody> tambahData(
            @Field("nama_barang") String nama_barang,
            @Field("image_barang") String image_barang,
            @Field("deskripsi_barang") String deskripsi_barang,
            @Field("harga_barang") String harga_barang,
            @Field("stok_barang") String stok_barang
    );




    @FormUrlEncoded
    @POST("api_update_barang.php")
    Call<ResponseBody> updateData(
            @Field("id_barang") String id_barang,
            @Field("nama_barang") String nama_barang,
            @Field("image_barang") String image_barang,
            @Field("deskripsi_barang") String deskripsi_barang,
            @Field("harga_barang") String harga_barang,
            @Field("stok_barang") String stok_barang
    );

    @FormUrlEncoded
    @POST("api_beli_barang.php")
    Call<ResponseBody> beliData(
            @Field("id_barang") String id_barang
    );


}
