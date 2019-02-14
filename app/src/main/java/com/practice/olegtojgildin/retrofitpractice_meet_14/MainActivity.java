package com.practice.olegtojgildin.retrofitpractice_meet_14;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.practice.olegtojgildin.retrofitpractice_meet_14.Data.DBManager;
import com.practice.olegtojgildin.retrofitpractice_meet_14.Network.ApiMapper;
import com.practice.olegtojgildin.retrofitpractice_meet_14.Network.RetrofitHelper;
import com.practice.olegtojgildin.retrofitpractice_meet_14.RecyclerForecast.WeatherAdapter;
import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherDay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherAdapter.OnWeatherListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    private List<WeatherDay> mListForecast;
    private Boolean isBound;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();

    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBindService();
    }

    public void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new WeatherAdapter(mListForecast, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onWeatherClick(int position) {
        Intent intent = DetailsWeatherActivity.newIntent(this);
        intent.putExtra(WeatherDay.class.getCanonicalName(), mListForecast.get(position));
        startActivity(intent);
    }

    private ServiceWeather mBoundService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBoundService = ((ServiceWeather.LocalBinder) service).getService();
            Log.d("MainActivity", "connected111");
            new AsyncTaskForecast().execute("Moscow");
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("MainActivity", "disconnected");
            isBound = false;
        }
    };

    public void bindService() {
        bindService(ServiceWeather.newIntent(MainActivity.this), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unBindService() {
        unbindService(mServiceConnection);
    }

    public  class AsyncTaskForecast extends AsyncTask<String, Void, List<WeatherDay>> {
        private boolean forecastFromDB=false;
        @Override
        protected List<WeatherDay> doInBackground(String... strings) {
            ApiMapper mApiMapper = new ApiMapper(new RetrofitHelper());
            List<WeatherDay> forecastList = mApiMapper.getForecastWeatherSync(strings[0], getApplicationContext());
            if (forecastList != null)
                return forecastList;
            else {
                Log.d("frombd", "dfsdf");
                DBManager dbManager = DBManager.getInstance(MainActivity.this);
                forecastFromDB=true;
                return dbManager.getAllWeatherDay();
            }
        }

        @Override
        protected void onPostExecute(List<WeatherDay> weatherDays) {
            mListForecast = weatherDays;
            Log.d("size", Integer.toString(mListForecast.size()));
            if(!forecastFromDB){
                DBManager dbManager = DBManager.getInstance(MainActivity.this);
                dbManager.removeForecasts();
                for (int i = 0; i < weatherDays.size(); i++)
                    dbManager.addWeather(weatherDays.get(i));
            }
            initViews();
        }
    }
}
