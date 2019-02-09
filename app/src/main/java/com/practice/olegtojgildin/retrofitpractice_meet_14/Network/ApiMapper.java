package com.practice.olegtojgildin.retrofitpractice_meet_14.Network;

import android.content.Context;
import android.util.Log;

import com.practice.olegtojgildin.retrofitpractice_meet_14.Data.DBManager;
import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherDay;
import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherForecast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by olegtojgildin on 01/02/2019.
 */

public class ApiMapper {
    private RetrofitHelper helper;
    private List<WeatherDay> mListWeather;

    public ApiMapper(RetrofitHelper helper) {
        this.helper = helper;
        mListWeather = new ArrayList<>();
    }

    public List<WeatherDay> forecastAsync(String country) {
        helper.getService().getForecast(country, "json", "metric", "ru", "acf993bf91158e1b964db7d30554fc95").enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                mListWeather = response.body().getItems();
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.d("ApiMapperFailure", t.getMessage());
            }
        });
        return mListWeather;
    }

    public List<WeatherDay> forecastSync(String country, final Context context) {
        Response<WeatherForecast> response = null;
        try {
            response = helper.getService().getForecast(country, "json", "metric", "ru", "acf993bf91158e1b964db7d30554fc95").execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Log.d("frombd", "dfsdf");
            DBManager dbManager = new DBManager(context);
            return dbManager.getAllWeatherDay();
        }

        return response.body().getItems();
    }


}
