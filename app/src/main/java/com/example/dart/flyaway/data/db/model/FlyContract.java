package com.example.dart.flyaway.data.db.model;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dart on 09.05.17.
 */

public class FlyContract {
    public static final String CONTENT_AUTHORITY = "com.example.dart.flyaway";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FLY_CARDS = "flycards";

    private FlyContract(){}

    public static abstract class FlyEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FLY_CARDS);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FLY_CARDS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FLY_CARDS;
        public static final String TABLE_NAME = "flycards";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FLY_DEPARTURE_NAME = "departure_name";
        public static final String COLUMN_FLY_DEPARTURE_CODE = "departure_code";
        public static final String COLUMN_FLY_DEPARTURE_DATE = "departure_date";
        public static final String COLUMN_FLY_DESTINATION_NAME = "destination_name";
        public static final String COLUMN_FLY_DESTINATION_CODE = "destination_code";
        public static final String COLUMN_FLY_PRICE = "price";
        public static final String COLUMN_FLY_CURRENCY_SYMBOL = "currency_symbol";
        public static final String COLUMN_FLY_IMAGE_URL = "image_url";
        public static final String COLUMN_FLY_ORDER_URL = "order_url";
        public static final String COLUMN_FLY_CREATED = "created";
    }
}
