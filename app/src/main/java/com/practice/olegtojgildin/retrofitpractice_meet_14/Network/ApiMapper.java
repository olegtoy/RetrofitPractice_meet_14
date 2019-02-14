package com.practice.olegtojgildin.retrofitpractice_meet_14.Network;

import android.content.Context;
import android.util.Log;

import com.practice.olegtojgildin.retrofitpractice_meet_14.Data.DBManager;
import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherDay;
import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherForecast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by olegtojgildin on 01/02/2019.
 */

public class ApiMapper {
    public static final String API_KEY="acf993bf91158e1b964db7d30554fc95";
    public static final String LANG="ru";
    public static final String UNITS="mtric";
    public static final String MODE="json";

    private RetrofitHelper helper;
    private List<WeatherDay> mListWeather;

    public ApiMapper(RetrofitHelper helper) {
        this.helper = helper;
        mListWeather = new ArrayList<>();
    }

    private static volatile ApiMapper INSTANCE;

    public static ApiMapper getInstance(final RetrofitHelper helper) {
        ApiMapper instance = INSTANCE;
        if (instance == null) {
            synchronized (ApiMapper.class) {
                instance = INSTANCE;
                if (instance == null) {
                    instance = INSTANCE = new ApiMapper(helper);
                }
            }
        }
        return instance;
    }


    public List<WeatherDay> getForecastWeatherSync(String country, final Context context) {
        Response<WeatherForecast> response = null;
        try {
            response = helper.getService().getForecast(country, MODE, UNITS, LANG, API_KEY).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
 /*       if (response == null) {
            Log.d("frombd", "dfsdf");
            DBManager dbManager = new DBManager(context);
            return dbManager.getAllWeatherDay();
        }
*/

        if (response != null) {
            if (response.isSuccessful()) {
                return response.body().getItems();
            } else {
                Log.e("ApiMapper", response.code() + " " + response.message());
            }
        }
        return null;
    }


}
