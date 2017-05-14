package com.example.dart.flyaway.data.prefs;

/**
 * Created by dart on 08.05.17.
 */

public interface IPreferencesHelper {

    String getUserName();

    String getUserCity();

    String getNearestAirport();

    void setUserName(String userName);

    void setNearestAirport(String nearestAirport);

    void setUserCity(String userCity);
}
