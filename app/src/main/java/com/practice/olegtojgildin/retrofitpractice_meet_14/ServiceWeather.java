package com.practice.olegtojgildin.retrofitpractice_meet_14;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.practice.olegtojgildin.retrofitpractice_meet_14.Data.DBManager;
import com.practice.olegtojgildin.retrofitpractice_meet_14.Network.ApiMapper;
import com.practice.olegtojgildin.retrofitpractice_meet_14.Network.RetrofitHelper;
import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherDay;

import java.util.List;

/**
 * Created by olegtojgildin on 01/02/2019.
 */

public class ServiceWeather extends Service {
    private IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        ServiceWeather getService() {
            return ServiceWeather.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, ServiceWeather.class);
    }
    public List<WeatherDay> getForecast(String city) {
        ApiMapper mApiMapper = new ApiMapper(new RetrofitHelper());
        List<WeatherDay> forecastList = mApiMapper.getForecastWeatherSync(city, getApplicationContext());
        if (forecastList == null) {
            Log.d("frombd", "dfsdf");
            DBManager dbManager = DBManager.getInstance(getApplicationContext());
            forecastList = dbManager.getAllWeatherDay();
        } else {
            DBManager dbManager = DBManager.getInstance(getApplicationContext());
            dbManager.removeForecasts();
            for (int i = 0; i < forecastList.size(); i++)
                dbManager.addWeather(forecastList.get(i));
        }
        return forecastList;
    }
}