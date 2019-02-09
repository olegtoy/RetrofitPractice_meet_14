package com.practice.olegtojgildin.retrofitpractice_meet_14;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by olegtojgildin on 01/02/2019.
 */

public class ServiceWeather extends Service {
    private IBinder mBinder=new LocalBinder();
    public class LocalBinder extends Binder{
        ServiceWeather getService(){
            return ServiceWeather.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public static final Intent newIntent(Context context){
        return new Intent(context,ServiceWeather.class);
    }
}
