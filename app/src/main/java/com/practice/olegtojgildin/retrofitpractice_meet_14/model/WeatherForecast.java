package com.practice.olegtojgildin.retrofitpractice_meet_14.model;

import com.google.gson.annotations.SerializedName;
import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherDay;

import java.util.List;

/**
 * Created by olegtojgildin on 31/01/2019.
 */

public class WeatherForecast {
    @SerializedName("list")
    private List<WeatherDay> items;

    public WeatherForecast(List<WeatherDay> items) {
        this.items = items;
    }

    public List<WeatherDay> getItems() {
        return items;
    }
}