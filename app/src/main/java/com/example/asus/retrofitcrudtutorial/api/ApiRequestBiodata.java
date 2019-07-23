package com.example.asus.retrofitcrudtutorial.api;

import com.example.asus.retrofitcrudtutorial.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequestBiodata {

    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponseModel> sendBiodata(@Field("nama") String nama,
                                    @Field("usia") String usia,
                                    @Field("domisili") String domisili);

    @GET("read.php")
    Call<ResponseModel> getBiodata();

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModel> updateData( @Field("id") String id,
                                    @Field("nama") String nama,
                                    @Field("usia") String usia,
                                    @Field("domisili") String domisili);
    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> hapusData(@Field("id") String id);
}
