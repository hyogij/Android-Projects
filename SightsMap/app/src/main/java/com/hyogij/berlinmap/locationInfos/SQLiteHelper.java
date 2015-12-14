package com.hyogij.berlinmap.locationInfos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/*
 * Description : SQLiteHelper class to execute sqlite query
 * Date : 2015.11.16
 * Author : hyogij@gmail.com
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String CLASS_NAME = SQLiteHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "LocationInfoDB";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCATION_INFO_TABLE = "CREATE TABLE locationInfo ( "
                + "name TEXT PRIMARY KEY, "
                + "latitude FLOAT, "
                + "longitude FLOAT, "
                + "description TEXT, "
                + "url TEXT )";

        // Create locationInfo table
        db.execSQL(CREATE_LOCATION_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older locationInfo table if existed
        db.execSQL("DROP TABLE IF EXISTS 'locationInfo'");

        // Create fresh locationInfo table
        this.onCreate(db);
    }

    // Location Information table name
    private static final String TABLE_LOCATION_INFO = "locationInfo";

    // Location Information Table Columns names
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "url";

    private static final String[] COLUMNS =
            {KEY_NAME, KEY_LATITUDE, KEY_LONGITUDE, KEY_DESCRIPTION, KEY_URL};

    public void addLocationInfo(LocationInfo locationInfo) {
        Log.d(CLASS_NAME, "addLocationInfo " + locationInfo.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, locationInfo.getName());
        values.put(KEY_LATITUDE, locationInfo.getLatitude());
        values.put(KEY_LONGITUDE, locationInfo.getLongitude());
        values.put(KEY_DESCRIPTION, locationInfo.getDescription());
        values.put(KEY_URL, locationInfo.getUrl());

        db.insert(TABLE_LOCATION_INFO,
                null,
                values);

        db.close();
    }

    public LocationInfo getLocationInfo(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Build query
        Cursor cursor =
                db.query(TABLE_LOCATION_INFO,
                        COLUMNS,
                        " name = ?",
                        new String[]{String.valueOf(name)},
                        null,
                        null,
                        null,
                        null);

        // If we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // Build locationInfo object
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setName(cursor.getString(0));
        locationInfo.setLatitude(cursor.getDouble(1));
        locationInfo.setLongitude(cursor.getDouble(2));
        locationInfo.setDescription(cursor.getString(3));
        locationInfo.setUrl(cursor.getString(4));

        return locationInfo;
    }

    // Get All location informations
    public ArrayList<LocationInfo> getAllLocationInfos() {
        ArrayList<LocationInfo> locationInfos = new ArrayList<LocationInfo>();

        // Build the query
        String query = "SELECT  * FROM " + TABLE_LOCATION_INFO + " order by name asc";

        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Go over each row, build LocationInfo and add it to list
        LocationInfo locationInfo = null;
        if (cursor.moveToFirst()) {
            do {
                locationInfo = new LocationInfo();
                locationInfo.setName(cursor.getString(0));
                locationInfo.setLatitude(cursor.getDouble(1));
                locationInfo.setLongitude(cursor.getDouble(2));
                locationInfo.setDescription(cursor.getString(3));
                locationInfo.setUrl(cursor.getString(4));

                // Add locationInfo to locationInfos
                locationInfos.add(locationInfo);
            } while (cursor.moveToNext());
        }

        Log.d(CLASS_NAME, "getAllLocationInfos() " + locationInfos.toString());

        // return locationInfos
        return locationInfos;
    }
}