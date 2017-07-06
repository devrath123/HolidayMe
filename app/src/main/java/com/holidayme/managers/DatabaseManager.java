package com.holidayme.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.holidayme.dao.DBConstant;
import com.holidayme.dao.DBHelper;
import com.holidayme.data.Destination;

import java.util.ArrayList;

/**
 * Class contains functions to access the database
 */
public class DatabaseManager {
    private static DBHelper dbHelper;
    static Context mContext;

    private int mOpenCounter;
    private static DatabaseManager instance;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager();
            dbHelper = new DBHelper(context);
            mContext = context;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
            dbHelper = new DBHelper(mContext);
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if (mOpenCounter == 1) {
            mDatabase = dbHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if (mOpenCounter == 0) {
            mDatabase.close();
        }
    }

    public static long insertData(String destination, String check_in_date, String check_out_date,
                                  String room_info, String adults, String kids, String destination_category, String language, String destination_key) {
        ContentValues values = new ContentValues();
        values.put(DBConstant.DESTINATION, destination);
        values.put(DBConstant.CHECK_IN_DATE, check_in_date);
        values.put(DBConstant.CHECK_OUT_DATE, check_out_date);
        values.put(DBConstant.ROOMS_INFO, room_info);
        values.put(DBConstant.ADULTS, adults);
        values.put(DBConstant.KIDS, kids);
        values.put(DBConstant.DESTINATION_CATEGORY, destination_category);
        values.put(DBConstant.DESTINATION_KEY, destination_key);
        values.put(DBConstant.LANGUAGE, language);
        long insertedID = DatabaseManager
                .getInstance()
                .openDatabase()
                .insertWithOnConflict(DBConstant.T_RECENT_SEARCH, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);
        DatabaseManager.getInstance().closeDatabase();
        return insertedID;
    }

    public  static  long updateData(String destination, String check_in_date, String check_out_date,
                                    String room_info, String adults, String kids, String destination_category, String language, String destination_key,String rowId){

        ContentValues contentValues= new ContentValues();

        contentValues.put(DBConstant.DESTINATION, destination);
        contentValues.put(DBConstant.CHECK_IN_DATE, check_in_date);
        contentValues.put(DBConstant.CHECK_OUT_DATE, check_out_date);
        contentValues.put(DBConstant.ROOMS_INFO, room_info);
        contentValues.put(DBConstant.ADULTS, adults);
        contentValues.put(DBConstant.KIDS, kids);
        contentValues.put(DBConstant.DESTINATION_CATEGORY, destination_category);
        contentValues.put(DBConstant.DESTINATION_KEY, destination_key);
        contentValues.put(DBConstant.LANGUAGE, language);


        long updatedId = DatabaseManager
                .getInstance()
                .openDatabase()
                .updateWithOnConflict(DBConstant.T_RECENT_SEARCH,contentValues,DBConstant.ID + "=" + rowId,null, SQLiteDatabase.CONFLICT_IGNORE);

        return  updatedId;

    }


    public static void deleteData(String destination_name, String language) {
        DatabaseManager
                .getInstance()
                .openDatabase()
                .delete(DBConstant.T_RECENT_SEARCH,
                        DBConstant.DESTINATION + "='" + destination_name + "'" + " AND " + DBConstant.LANGUAGE + "='" + language + "'", null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public static String getOldData(String language) {
        String destination_name = "";
        String selectQuery = "SELECT * FROM " + DBConstant.T_RECENT_SEARCH + " WHERE " + DBConstant.LANGUAGE + "= '" + language + "'" + " ORDER BY " + DBConstant.ID + " ASC " + "LIMIT 1";
        Cursor c = DatabaseManager.getInstance().openDatabase()
                .rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            destination_name = c.getString(c.getColumnIndex(DBConstant.DESTINATION));
        }
        c.close();
        DatabaseManager.getInstance().closeDatabase();
        return destination_name;
    }

    public static int getRowCount(String language) {
        int count ;
        String selectQuery = "SELECT * FROM " + DBConstant.T_RECENT_SEARCH
                + " WHERE " + DBConstant.LANGUAGE + "= '" + language + "'";
        Cursor c = DatabaseManager.getInstance().openDatabase()
                .rawQuery(selectQuery, null);
        count = c.getCount();
        c.close();
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }

    public static ArrayList<Destination> getAllData(String language) {
        ArrayList<Destination> destinations = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DBConstant.T_RECENT_SEARCH + " WHERE " + DBConstant.LANGUAGE + "= '" + language + "'" + " ORDER BY " + DBConstant.ID + " DESC ";
        Cursor c = DatabaseManager.getInstance().openDatabase()
                .rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Destination destination = new Destination();
                destination.setCategory(c.getString(c.getColumnIndex(DBConstant.DESTINATION_CATEGORY)));
                if (destination.getCategory().equalsIgnoreCase("City")) {
                    destination.setIsCity(true);
                } else {
                    destination.setIsHotel(true);
                }
                destination.setKey(c.getString(c.getColumnIndex(DBConstant.DESTINATION_KEY)));
                destination.setDestinationName(c.getString(c.getColumnIndex(DBConstant.DESTINATION)));
                destinations.add(destination);
            } while (c.moveToNext());
        }
        c.close();
        DatabaseManager.getInstance().closeDatabase();
        return destinations;
    }

    public static Destination getLatestData(String language) {
        Destination destination = null;
        String selectQuery = "SELECT * FROM " + DBConstant.T_RECENT_SEARCH + " WHERE " + DBConstant.LANGUAGE + "= '" + language + "'" + " ORDER BY " + DBConstant.ID + " DESC " + "LIMIT 1";
        Cursor c = DatabaseManager.getInstance().openDatabase()
                .rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                destination = new Destination();
                destination.setCategory(c.getString(c.getColumnIndex(DBConstant.DESTINATION_CATEGORY)));
                if (destination.getCategory().equalsIgnoreCase("City")) {
                    destination.setIsCity(true);
                } else {
                    destination.setIsHotel(true);
                }
                destination.setKey(c.getString(c.getColumnIndex(DBConstant.DESTINATION_KEY)));
                destination.setDestinationName(c.getString(c.getColumnIndex(DBConstant.DESTINATION)));
                destination.setCheckInDate(c.getString(c.getColumnIndex(DBConstant.CHECK_IN_DATE)));
                destination.setCheckOutDate(c.getString(c.getColumnIndex(DBConstant.CHECK_OUT_DATE)));
                destination.setmRoomInfo(c.getString(c.getColumnIndex(DBConstant.ROOMS_INFO)));
            } while (c.moveToNext());
        }
        c.close();
        DatabaseManager.getInstance().closeDatabase();
        return destination;
    }

  ;


        public static  String getLatestRowId(String language){

            String rowId="";
            String selectQuery = "SELECT * FROM " + DBConstant.T_RECENT_SEARCH + " WHERE " + DBConstant.LANGUAGE + "= '" + language + "'" + " ORDER BY " + DBConstant.ID + " DESC " + "LIMIT 1";
            Cursor c = DatabaseManager.getInstance().openDatabase()
                    .rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                do {

                    rowId  =c.getString(c.getColumnIndex(DBConstant.ID));

                } while (c.moveToNext());
            }
            c.close();
            DatabaseManager.getInstance().closeDatabase();
            return rowId;



    }


    public static  String getAvailableRowId(String language, String destination){

        String rowId="";

        Cursor cursor= DatabaseManager.getInstance().openDatabase().query(DBConstant.T_RECENT_SEARCH,null,DBConstant.LANGUAGE + "= ?" + " AND " +
                DBConstant.DESTINATION + "= ?" ,new String[]{language,destination},null,null,null);

        if (cursor.moveToFirst()) {
            do {

                rowId  =cursor.getString(cursor.getColumnIndex(DBConstant.ID));

            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return rowId;
    }
}
