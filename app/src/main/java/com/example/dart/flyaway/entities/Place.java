package com.example.dart.flyaway.entities;

/**
 * Created by dart on 07.05.17.
 * Entity description airport
 */

public class Place {
    private String mCode;
    private String mFullName;

    public Place(String code,
          String fullName){
        mCode = code;
        mFullName = fullName;
    }

    public String getCode() {
        return mCode;
    }

    public String getFullName() {
        return mFullName;
    }

}
