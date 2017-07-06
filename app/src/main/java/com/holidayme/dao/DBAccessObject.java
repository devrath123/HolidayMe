package com.holidayme.dao;

import android.content.Context;

/**
 * Class contains functions to access the database
 */
public class DBAccessObject {
    private DBHelper dbHelper;
    Context mContext;
    private  DBAccessObject instance;


    public  synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new DBAccessObject();
            dbHelper = new DBHelper(context);
            mContext = context;
        }
    }

    public  synchronized DBAccessObject getInstance() {
        if (instance == null) {
            instance = new DBAccessObject();
            dbHelper = new DBHelper(mContext);
        }
        return instance;
    }


}
