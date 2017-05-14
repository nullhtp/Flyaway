package com.example.dart.flyaway.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dart.flyaway.data.db.model.FlyContract.FlyEntry;

/**
 * Created by dart on 10.05.17.
 */

public class AppFlyDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Flyaway.db";
    public static final String SQL_CREATE_DATABASE = "CREATE TABLE "+ FlyEntry.TABLE_NAME+" ( "
            +FlyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +FlyEntry.COLUMN_FLY_DEPARTURE_NAME +" TEXT NOT NULL,"
            +FlyEntry.COLUMN_FLY_DEPARTURE_CODE +" TEXT NOT NULL,"
            +FlyEntry.COLUMN_FLY_DESTINATION_NAME +" TEXT NOT NULL,"
            +FlyEntry.COLUMN_FLY_DESTINATION_CODE +" TEXT NOT NULL,"
            +FlyEntry.COLUMN_FLY_IMAGE_URL +" TEXT,"
            +FlyEntry.COLUMN_FLY_ORDER_URL +" TEXT NOT NULL,"
            +FlyEntry.COLUMN_FLY_DEPARTURE_DATE +" TEXT NOT NULL,"
            +FlyEntry.COLUMN_FLY_CURRENCY_SYMBOL +" TEXT NOT NULL,"
            +FlyEntry.COLUMN_FLY_PRICE +" TEXT NOT NULL,"
            +FlyEntry.COLUMN_FLY_CREATED +" INTEGER NOT NULL);";

    public static final String SQL_DELETE_DATABASE = "DROP TABLE "+ FlyEntry.TABLE_NAME+";";

    public AppFlyDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.e("AppFlyDbHelper",SQL_CREATE_DATABASE);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
}
