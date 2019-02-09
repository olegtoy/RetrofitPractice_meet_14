package com.practice.olegtojgildin.retrofitpractice_meet_14.Network;

import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherDay;
import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by olegtojgildin on 31/01/2019.
 */

public interface WebService {

    @GET("weather?")
    Call<WeatherDay> getToday(@Query("q") String cityName,
                              @Query("appid") String appid);

    @GET("forecast?")
    Call<WeatherForecast> getForecast(@Query("q") String cityName,
                                      @Query("mode") String mode,
                                      @Query("units") String units,
                                      @Query("lang") String lang,
                                      @Query("appid") String appid);
}
