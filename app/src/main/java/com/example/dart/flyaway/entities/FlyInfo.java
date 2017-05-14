package com.example.dart.flyaway.entities;

import java.util.Date;
import java.util.Locale;

/**
 * Created by dart on 07.05.17.
 */

public class FlyInfo {
    private final Place mDeparture;
    private final Place mDestination;
    private final Date mDepartureDate;
    private final String mPrice;
    private final String mCurrencySymbol;
    private final String mImageUrl;
    private final String mOrderUrl;

    public FlyInfo(Place mDeparture,
                   Place mDestination,
                   Date mDepartureDate,
                   String mPrice,
                   String mCurrencySymbol,
                   String mImageUrl,
                   String mOrderUrl) {
        this.mDeparture = mDeparture;
        this.mDestination = mDestination;
        this.mDepartureDate = mDepartureDate;
        this.mPrice = mPrice;
        this.mCurrencySymbol = mCurrencySymbol;
        this.mImageUrl = mImageUrl;
        this.mOrderUrl = mOrderUrl;
    }

    public FlyInfo(String departurePlace,
                   String departureCode,
                   String destinationPlace,
                   String destinationCode,
                   Date mDepartureDate,
                   String mPrice,
                   String mCurrencySymbol,
                   String mImageUrl,
                   String mOrderUrl) {
        this.mDeparture = new Place(departureCode,departurePlace);
        this.mDestination = new Place(destinationCode,destinationPlace);
        this.mDepartureDate = mDepartureDate;
        this.mPrice = mPrice;
        this.mCurrencySymbol = mCurrencySymbol;
        this.mImageUrl = mImageUrl;
        this.mOrderUrl = mOrderUrl;

    }

    public String getAboutUrl() {
        return "https://"
                + Locale.getDefault().getLanguage()
                + ".m.wikipedia.org/wiki/"
                + mDestination.getFullName().replace(" ","_");
    }

    public String getOrderUrl() {
        return mOrderUrl;
    }

    public Place getDeparture() {
        return mDeparture;
    }

    public Place getDestination() {
        return mDestination;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getCurrencySymbol() {
        return mCurrencySymbol;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public Date getDepartureDate() {
        return mDepartureDate;
    }
}
