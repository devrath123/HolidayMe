package com.holidayme.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shaikh.salim on 2/18/2016.
 * Class for creating database and providing access to the database
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DBConstant.DATABASE_NAME, null, DBConstant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_T_RECENT_SEARCH = "CREATE TABLE IF NOT EXISTS "
                + DBConstant.T_RECENT_SEARCH + "(" + DBConstant.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DBConstant.DESTINATION
                + " TEXT, " + DBConstant.CHECK_IN_DATE + " TEXT, "
                + DBConstant.CHECK_OUT_DATE + " TEXT," + DBConstant.ROOMS_INFO
                + " TEXT," + DBConstant.ADULTS + " TEXT,"
                + DBConstant.KIDS + " TEXT,"
                + DBConstant.DESTINATION_CATEGORY + " TEXT,"
                + DBConstant.DESTINATION_KEY + " TEXT,"
                + DBConstant.LANGUAGE + " TEXT )";

        db.execSQL(CREATE_T_RECENT_SEARCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstant.T_RECENT_SEARCH);
        onCreate(db);
    }

}
