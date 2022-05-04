package com.example.test.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://dapi.kakao.com/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) //사용할 주소
                    .addConverterFactory(GsonConverterFactory.create()) //json -> dto 자동변환
                    .build();
        }
        return  retrofit;
    }
}