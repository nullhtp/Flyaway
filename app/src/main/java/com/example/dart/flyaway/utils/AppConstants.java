package com.example.dart.flyaway.utils;

/**
 * Created by dart on 08.05.17.
 */

public final class AppConstants {
    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";
    public static final String DATE_FORMAT = "dd.MM.yyyy'T'HH:mm:ss";
    public static final String DATE_FORMAT_PARAMETR = "dd.MM.yyyy HH:mm";

    public static final String KEY_WEBVIEW_URL = "com.example.dart.flyaway.KEY_URL";

    public static final String API_KEY_IMAGE_PIXABAY = "5309579-7698732689ea3501bf024b2a0";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "flyaway.db";
    public static final String PREF_NAME = "flyaway_pref";

    public static final long NULL_INDEX = -1L;

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
