package com.practice.olegtojgildin.retrofitpractice_meet_14.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by olegtojgildin on 31/01/2019.
 */

public class RetrofitHelper {
    public static final String VERSION_API="2.5/";
    public static final String BASE_URL="https://api.openweathermap.org/data/"+VERSION_API;

    public WebService getService(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);


        Gson gson=new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        return retrofit.create(WebService.class);
    }
}
