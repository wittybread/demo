package com.example.demo.utils;


import com.example.demo.ApiService;
import com.example.demo.LoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SDC on 2017/12/21.
 */

public class RetrofitUtils {
    private static volatile  RetrofitUtils instance;
    private OkHttpClient client= new OkHttpClient
            .Builder()
            .addInterceptor(new LoggingInterceptor())
            .build();
    private ApiService apiService;
//"http://120.27.23.105/"
    private RetrofitUtils(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        apiService = retrofit.create(ApiService.class);
    }
    public static RetrofitUtils getInstance(String url){
        if(instance==null){
            synchronized (RetrofitUtils.class){
                if(null==instance){
                    instance = new RetrofitUtils(url);
                }
            }
        }
        return instance;
    }
    public ApiService getApiService(){
        return apiService;
    }
}
