package com.practice.olegtojgildin.retrofitpractice_meet_14.model;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity(tableName = "forecast")
public class WeatherDay implements Parcelable {

    public WeatherDay(long dt, float temp, float temp_max, float temp_min, float humidity, float pressure) {
        this.dt = dt;
        Main main = new Main();
        main.temp = temp;
        main.temp_max = temp_max;
        main.temp_min = temp_min;
        main.humidity = humidity;
        main.pressure = pressure;
        this.main = main;
    }

    protected WeatherDay(Parcel in) {
        dt = in.readLong();
        main = (Main) in.readParcelable(Main.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherDay> CREATOR = new Creator<WeatherDay>() {
        @Override
        public WeatherDay createFromParcel(Parcel in) {
            return new WeatherDay(in);
        }

        @Override
        public WeatherDay[] newArray(int size) {
            return new WeatherDay[size];
        }
    };


    @SerializedName("main")
    public Main main;

    public Main getMain() {
        return main;
    }

    @SerializedName("wind")
    private Wind wind;

    public Wind getWind() {
        return wind;
    }

    @SerializedName("dt")
    private long dt;

    public long getDt() {
        return dt;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(dt);
        parcel.writeParcelable(main, i);
    }


    public static class Main implements Parcelable {
        @SerializedName("temp")
        private float temp;
        @SerializedName("temp_min")
        public float temp_min;
        @SerializedName("temp_max")
        public float temp_max;
        @SerializedName("humidity")
        private float humidity;
        @SerializedName("pressure")
        private float pressure;


        public Main() {

        }

        protected Main(Parcel in) {
            temp = in.readFloat();
            temp_max = in.readFloat();
            temp_min = in.readFloat();
            humidity = in.readFloat();
            pressure = in.readFloat();

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeFloat(temp);
            parcel.writeFloat(temp_max);
            parcel.writeFloat(temp_min);
            parcel.writeFloat(humidity);
            parcel.writeFloat(pressure);
        }

        public static final Creator<Main> CREATOR = new Creator<Main>() {
            @Override
            public Main createFromParcel(Parcel in) {
                return new Main(in);
            }

            @Override
            public Main[] newArray(int size) {
                return new Main[size];
            }
        };

        public Float getTemp() {
            return temp;
        }

        public Float getTempMax() {
            return temp_max;
        }

        public Float getTempMin() {
            return temp_min;
        }

        public Float getHumidity() {
            return humidity;
        }

        public Float getPressure() {
            return pressure;
        }

    }

    class Sys {
        @SerializedName("sunrise")
        private long sunrise;
        @SerializedName("sunset")
        private long sunset;

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }
    }


    class Wind {
        @SerializedName("speed")
        private float speed;

        public Float getSpeed() {
            return speed;
        }

    }

    public class Weather {
        @SerializedName("id")
        private int id;
        @SerializedName("main")
        public String main;
        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }
    }

    @SerializedName("weather")
    private ArrayList<Weather> weather = new ArrayList();

    public ArrayList<Weather> getWeather() {
        return weather;
    }

}

