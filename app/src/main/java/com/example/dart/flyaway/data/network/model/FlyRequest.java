package com.example.dart.flyaway.data.network.model;

/**
 * Created by dart on 08.05.17.
 */

public class FlyRequest {

    private String mCountry;
    private String mCurrency;
    private String mLocale;
    private String mDeparturePlace;
    private String mDestinationPlace;
    private String mDepartureDate;

    public FlyRequest(String country,
                      String currency,
                      String locale,
                      String departurePlace,
                      String departureDate){
        mCountry = country;
        mCurrency = currency;
        mLocale = locale;
        mDeparturePlace = departurePlace;
        mDepartureDate = departureDate;
        mDestinationPlace = "anywhere";
    }


}
