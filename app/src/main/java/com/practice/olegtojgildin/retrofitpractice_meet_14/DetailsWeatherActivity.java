package com.practice.olegtojgildin.retrofitpractice_meet_14;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherDay;

import java.util.Date;

/**
 * Created by olegtojgildin on 01/02/2019.
 */

public class DetailsWeatherActivity extends AppCompatActivity {

    private WeatherDay weatherDay;
    private TextView date;
    private TextView tempMax;
    private TextView tempMin;
    private TextView humidity;
    private TextView pressure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_weather_activity);
        initView();
        initDay();
    }
    public void initView(){
        date=findViewById(R.id.dt_details);
        tempMax=findViewById(R.id.temp_max_details);
        tempMin=findViewById(R.id.temp_min_details);
        humidity=findViewById(R.id.humidity_detalis);
        pressure=findViewById(R.id.pressure_detalis);
    }
    public void initDay(){
        weatherDay=getIntent().getParcelableExtra(WeatherDay.class.getCanonicalName());
        date.setText(new Date((long) weatherDay.getDt() * 1000).toString());
        tempMax.setText(Float.toString(weatherDay.getMain().getTempMax()));
        tempMin.setText(Float.toString(weatherDay.getMain().getTempMin()));
        humidity.setText(Float.toString(weatherDay.getMain().getHumidity()));
        pressure.setText(Float.toString(weatherDay.getMain().getPressure()));
    }

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, DetailsWeatherActivity.class);
        return intent;
    }

}

