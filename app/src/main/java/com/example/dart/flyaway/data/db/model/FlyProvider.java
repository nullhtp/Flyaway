package com.example.dart.flyaway.data.db.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.dart.flyaway.data.db.AppFlyDbHelper;
import com.example.dart.flyaway.data.db.model.FlyContract.FlyEntry;

/**
 * Created by dart on 10.05.17.
 */

public class FlyProvider extends ContentProvider {
    private  static final int FLYS = 200;
    private  static final int FLY_ID = 201;
    /** Tag for the log messages */
    public static final String TAG = "FlyProvider";

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(FlyContract.CONTENT_AUTHORITY,FlyContract.PATH_FLY_CARDS,FLYS);
        sUriMatcher.addURI(FlyContract.CONTENT_AUTHORITY,FlyContract.PATH_FLY_CARDS+"/#",FLY_ID);
    }

    private AppFlyDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new AppFlyDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case FLYS:
                cursor = database.query(FlyEntry.TABLE_NAME, projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case FLY_ID:
                selection = FlyEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(FlyEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(
                getContext().getContentResolver(),
                uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FLYS:
                return FlyEntry.CONTENT_LIST_TYPE;
            case FLY_ID:
                return FlyEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FLYS:
                return insertFly(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private boolean checkData(ContentValues values){
        if (values.containsKey(FlyEntry.COLUMN_FLY_DEPARTURE_CODE)) {
            String data = values.getAsString(FlyEntry.COLUMN_FLY_DEPARTURE_CODE);
            if (data == null) {
                throw new IllegalArgumentException("Fly requires a departure code");
            }
        }
        if (values.containsKey(FlyEntry.COLUMN_FLY_DEPARTURE_DATE)) {
            String data = values.getAsString(FlyEntry.COLUMN_FLY_DEPARTURE_DATE);
            if (data == null) {
                throw new IllegalArgumentException("Fly requires a departure date");
            }
        }
        if (values.containsKey(FlyEntry.COLUMN_FLY_DEPARTURE_NAME)) {
            String data = values.getAsString(FlyEntry.COLUMN_FLY_DEPARTURE_NAME);
            if (data == null) {
                throw new IllegalArgumentException("Fly requires a departure name");
            }
        }
        if (values.containsKey(FlyEntry.COLUMN_FLY_DESTINATION_CODE)) {
            String data = values.getAsString(FlyEntry.COLUMN_FLY_DESTINATION_CODE);
            if (data == null) {
                throw new IllegalArgumentException("Fly requires a destination code");
            }
        }
        if (values.containsKey(FlyEntry.COLUMN_FLY_DESTINATION_NAME)) {
            String data = values.getAsString(FlyEntry.COLUMN_FLY_DESTINATION_NAME);
            if (data == null) {
                throw new IllegalArgumentException("Fly requires a destination name");
            }
        }
        if (values.containsKey(FlyEntry.COLUMN_FLY_IMAGE_URL)) {
            String data = values.getAsString(FlyEntry.COLUMN_FLY_IMAGE_URL);
            if (data == null) {
                throw new IllegalArgumentException("Fly requires a image url");
            }
        }
        if (values.containsKey(FlyEntry.COLUMN_FLY_ORDER_URL)) {
            String data = values.getAsString(FlyEntry.COLUMN_FLY_ORDER_URL);
            if (data == null) {
                throw new IllegalArgumentException("Fly requires a order url");
            }
        }
        if (values.containsKey(FlyEntry.COLUMN_FLY_PRICE)) {
            String data = values.getAsString(FlyEntry.COLUMN_FLY_PRICE);
            if (data == null) {
                throw new IllegalArgumentException("Fly requires a price");
            }
        }
        if (values.containsKey(FlyEntry.COLUMN_FLY_CURRENCY_SYMBOL)) {
            String data = values.getAsString(FlyEntry.COLUMN_FLY_CURRENCY_SYMBOL);
            if (data == null) {
                throw new IllegalArgumentException("Fly requires a currency symbol");
            }
        }

        return true;
    }

    private Uri insertFly(Uri uri, ContentValues values) {

        checkData(values);

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(FlyEntry.TABLE_NAME,null,values);

        if (id == -1) {
            Log.e(TAG, "Failed to insert row for " + uri);
            return null;
        }

        if (id > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FLYS:
                return deleteFly(uri, selection, selectionArgs);
            case FLY_ID:
                selection = FlyEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return deleteFly(uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    private int deleteFly(Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int id = database.delete(FlyEntry.TABLE_NAME, selection, selectionArgs);
        if (id > 0 ) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return id;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FLYS:
                return updateFly(uri, values, selection, selectionArgs);
            case FLY_ID:
                selection = FlyEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateFly(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateFly(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        checkData(values);

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int id = database.update(FlyEntry.TABLE_NAME,values,selection,selectionArgs);
        if (id > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return id;
    }
}
