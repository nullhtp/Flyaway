package com.example.dart.flyaway.data.network.model;

import java.util.Date;

/**
 * Created by dart on 08.05.17.
 */

public class FlyResponse {
    private String mMinPrice;
    private Date mDepartureDate;
    private String mDestinationPlace;
    private String mDestinationCode;
    private String mCurrencySymbol;
    private String mOrderUrl;

    public FlyResponse(String minPrice,
                       Date departureDate,
                       String destinationPlace,
                       String destinationCode,
                       String currencySymbol,
                       String orderUrl) {
        mMinPrice = minPrice;
        mDepartureDate = departureDate;
        mDestinationPlace = destinationPlace;
        mDestinationCode = destinationCode;
        mCurrencySymbol = currencySymbol;
        mOrderUrl = orderUrl;
    }


    public String getMinPrice() {
        return mMinPrice;
    }

    public Date getDepartureDate() {
        return mDepartureDate;
    }

    public String getDestinationPlace() {
        return mDestinationPlace;
    }

    public String getDestinationCode() {
        return mDestinationCode;
    }

    public String getCurrencySymbol() {
        return mCurrencySymbol;
    }

    public String getOrderUrl() {
        return mOrderUrl;
    }
}
