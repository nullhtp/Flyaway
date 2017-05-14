package com.example.dart.flyaway.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dart on 08.05.17.
 */

public class AppPreferencesHelper implements IPreferencesHelper {

    private static final String TAG = "AppPreferencesHelper";

    private static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";
    private static final String PREF_KEY_USER_CITY = "PREF_KEY_USER_CITY";
    private static final String PREF_KEY_NEAREST_AIRPORT = "PREF_KEY_NEAREST_AIRPORT";

    private final SharedPreferences mPrefs;

    public AppPreferencesHelper(Context context, String prefsFileName) {
        mPrefs = context.getSharedPreferences(prefsFileName,Context.MODE_PRIVATE);
    }


    @Override
    public String getUserName() {
        return mPrefs.getString(PREF_KEY_USER_NAME, null);
    }

    @Override
    public String getUserCity() {
        return mPrefs.getString(PREF_KEY_USER_CITY, null);
    }

    @Override
    public String getNearestAirport() {
        return mPrefs.getString(PREF_KEY_NEAREST_AIRPORT, null);
    }

    @Override
    public void setUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_USER_NAME, userName).apply();
    }

    @Override
    public void setNearestAirport(String nearestAirport) {
        mPrefs.edit().putString(PREF_KEY_NEAREST_AIRPORT, nearestAirport).apply();
    }

    @Override
    public void setUserCity(String userCity) {
        mPrefs.edit().putString(PREF_KEY_USER_CITY, userCity).apply();
    }
}
