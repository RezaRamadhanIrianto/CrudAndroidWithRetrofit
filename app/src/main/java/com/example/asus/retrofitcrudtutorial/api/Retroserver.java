package com.example.asus.retrofitcrudtutorial.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retroserver {
    public static final String base_url = "http://192.168.121.2/crud_retrofit/";

    private static Retrofit retrofit;


    public static Retrofit getClient(){

        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }


}
