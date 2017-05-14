package com.example.dart.flyaway.data.network.model;

/**
 * Created by dart on 08.05.17.
 */

public class GeoPlaceResponse {

    private String mNamePlace;
    private String mCodePlace;


    public GeoPlaceResponse(String namePlace, String codePlace) {
        this.mNamePlace = namePlace;
        this.mCodePlace = codePlace;
    }

    public String getName() {
        return mNamePlace;
    }

    public String getCode() {
        return mCodePlace;
    }
}
