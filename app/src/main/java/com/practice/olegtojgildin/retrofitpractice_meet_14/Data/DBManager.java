package com.practice.olegtojgildin.retrofitpractice_meet_14.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.practice.olegtojgildin.retrofitpractice_meet_14.Data.DbHelper;
import com.practice.olegtojgildin.retrofitpractice_meet_14.model.WeatherDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 01/02/2019.
 */

public class DBManager {
    private DbHelper dbHelper;

    private static volatile DBManager INSTANCE;

    public static DBManager getInstance(final Context context) {
        DBManager instance = INSTANCE;
        if (instance == null) {
            synchronized (DBManager.class) {
                instance = INSTANCE;
                if (instance == null) {
                    instance = INSTANCE = new DBManager(context);
                }
            }
        }
        return instance;
    }

    public DBManager(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    public void addWeather(WeatherDay weather) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            if (db.inTransaction())
                addWeatherInternal(db, getContentValues(weather));
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                if (db.inTransaction())
                    db.endTransaction();
                db.close();
            }
        }
    }

    public void removeForecasts() {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            removeForecastsInternal(db);
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null) {
                if (db.inTransaction()) {
                    db.endTransaction();
                }
                db.close();
            }
        }
    }


    public List<WeatherDay> getAllWeatherDay() {
        List<WeatherDay> mWeatherList = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            db.beginTransaction();

            String selectQuery = "SELECT  * FROM " + DbHelper.FORECAST_TABLE;
            mWeatherList=cursorToList(db,selectQuery);

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
                db.close();
            }
        }
        return mWeatherList;
    }

    public List<WeatherDay> cursorToList(SQLiteDatabase db, String selectQuery){
        List<WeatherDay> mWeatherList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst() && db.inTransaction()) {
            do {
                mWeatherList.add(converterCursor(cursor));
            } while (cursor.moveToNext());
        }
        return mWeatherList;
    }


    private ContentValues getContentValues(WeatherDay weatherDay) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.DATE, weatherDay.getDt());
        contentValues.put(DbHelper.TEMPER, weatherDay.getMain().getTemp());
        contentValues.put(DbHelper.TEMP_MIN, weatherDay.getMain().getTempMin());
        contentValues.put(DbHelper.TEMP_MAX, weatherDay.getMain().getTempMax());
        contentValues.put(DbHelper.HUMIDITY, weatherDay.getMain().getHumidity());
        contentValues.put(DbHelper.PRESSURE, weatherDay.getMain().getPressure());
        return contentValues;
    }

    public WeatherDay converterCursor(Cursor cursor) {
        return new WeatherDay(cursor.getLong(1), cursor.getFloat(2), cursor.getFloat(3), cursor.getFloat(4), cursor.getFloat(5), cursor.getFloat(6));

    }

    private void addWeatherInternal(SQLiteDatabase db, ContentValues contentValues) {
        db.insertOrThrow(DbHelper.FORECAST_TABLE, null, contentValues);
    }

    private void removeForecastsInternal(SQLiteDatabase db) {
        if (db != null) {
            db.delete(DbHelper.FORECAST_TABLE, null, null);
        }
    }
}
