package com.practice.olegtojgildin.retrofitpractice_meet_14.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olegtojgildin on 01/02/2019.
 */


public class DbHelper extends SQLiteOpenHelper {

    public static final int VERSION_DB=1;
    public static final String DB_NAME="forecast_database.db";
    public static final String FORECAST_TABLE="WEATHER";
    public static final String ID="id";
    public static final String DATE="date";
    public static final String TEMPER="temper";
    public static final String HUMIDITY="humidity";
    public static final String PRESSURE="pressure";
    public static final String TEMP_MIN="temp_min";
    public static final String TEMP_MAX="temp_max";
    public static final String WIND_SPEED="wind_speed";
    public static final String DESCRIPTION="description";


    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DbHelper(Context context){
        this(context,DB_NAME,null,VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createEmptyTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        deleteTables(db);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

    public void createEmptyTables(SQLiteDatabase database){
        database.execSQL( "CREATE TABLE WEATHER(id integer primary key AUTOINCREMENT, date text not null, temper real,temp_min real,temp_max real,humidity real,pressure real, wind_speed real)" );
    }
    private void deleteTables(SQLiteDatabase database){
        database.execSQL( "DROP TABLE IF EXISTS WEATHER" );
    }
}
