package com.example.dart.flyaway.data.network.model;

/**
 * Created by dart on 08.05.17.
 */

public class GeoPlaceRequest {

    private String mLatitude;
    private String mLongitude;

    public GeoPlaceRequest(String latitude, String longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }
}
