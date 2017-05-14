package com.example.dart.flyaway.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Toast;

import com.example.dart.flyaway.R;
import com.example.dart.flyaway.WebviewActivity;
import com.example.dart.flyaway.data.db.model.FlyContract.FlyEntry;
import com.example.dart.flyaway.data.db.model.FlyProvider;
import com.example.dart.flyaway.entities.FlyInfo;
import com.example.dart.flyaway.entities.FlyParameter;
import com.example.dart.flyaway.entities.Place;
import com.example.dart.flyaway.data.network.IFlyApiHelper;
import com.example.dart.flyaway.data.network.IImageApiHelper;
import com.example.dart.flyaway.data.network.model.FlyRequest;
import com.example.dart.flyaway.data.network.model.FlyResponse;
import com.example.dart.flyaway.data.network.model.GeoPlaceRequest;
import com.example.dart.flyaway.data.network.model.GeoPlaceResponse;
import com.example.dart.flyaway.data.prefs.IPreferencesHelper;
import com.example.dart.flyaway.utils.AppConstants;
import com.example.dart.flyaway.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dart on 08.05.17.
 */

public class AppDataManager implements IDataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final IPreferencesHelper mPreferencesHelper;
    private final IFlyApiHelper mFlyApiHelper;
    private final IImageApiHelper mImageApiHelper;

    public AppDataManager(Context context,
                          IPreferencesHelper preferencesHelper,
                          IFlyApiHelper flyApiHelper,
                          IImageApiHelper imageApiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mFlyApiHelper = flyApiHelper;
        mImageApiHelper = imageApiHelper;
    }


    @Override
    public String getUserName() {
        return mPreferencesHelper.getUserName();
    }

    @Override
    public String getUserCity() {
        return mPreferencesHelper.getUserCity();
    }

    @Override
    public String getNearestAirport() {
        return mPreferencesHelper.getNearestAirport();
    }

    @Override
    public void setUserName(String userName) {
        mPreferencesHelper.setUserName(userName);
    }

    @Override
    public void setNearestAirport(String nearestAirport) {
        mPreferencesHelper.setNearestAirport(nearestAirport);
    }

    @Override
    public void setUserCity(String userCity) {
        mPreferencesHelper.setUserCity(userCity);
    }

    @Override
    public FlyInfo getFlyInfo() {
        String[] projection = {FlyEntry._ID,
                FlyEntry.COLUMN_FLY_DEPARTURE_CODE,
                FlyEntry.COLUMN_FLY_DEPARTURE_NAME,
                FlyEntry.COLUMN_FLY_DEPARTURE_DATE,
                FlyEntry.COLUMN_FLY_DESTINATION_CODE,
                FlyEntry.COLUMN_FLY_DESTINATION_NAME,
                FlyEntry.COLUMN_FLY_PRICE,
                FlyEntry.COLUMN_FLY_IMAGE_URL,
                FlyEntry.COLUMN_FLY_ORDER_URL,
                FlyEntry.COLUMN_FLY_CREATED,
                FlyEntry.COLUMN_FLY_CURRENCY_SYMBOL,
        };
        String selection = FlyEntry._ID+"=(SELECT MAX("+FlyEntry._ID+") FROM "+ FlyEntry.TABLE_NAME +")";
        Cursor cursor = mContext.getContentResolver().query(FlyEntry.CONTENT_URI,projection,selection,null,null);
        FlyInfo flyInfo = null;
        if(cursor != null && cursor.moveToFirst()) {
            String departureCode = cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_DEPARTURE_CODE));
            String departureName = cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_DEPARTURE_NAME));
            String destinationCode = cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_DESTINATION_CODE));
            String destinationName = cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_DESTINATION_NAME));
            Date departureDate = AppUtils.formatStringToDate(
                    AppConstants.DATE_FORMAT,
                    cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_DEPARTURE_DATE)));

            Log.e(TAG,cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_DEPARTURE_DATE)));
            Log.e(TAG,departureDate.toString());

            String price = cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_PRICE));
            String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_IMAGE_URL));
            String orderUrl = cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_ORDER_URL));
            String currencySymbol =cursor.getString(cursor.getColumnIndexOrThrow(FlyEntry.COLUMN_FLY_CURRENCY_SYMBOL));

            flyInfo = new FlyInfo(departureName,
                    departureCode,
                    destinationName,
                    destinationCode,
                    departureDate,
                    price,
                    currencySymbol,
                    imageUrl,
                    orderUrl);
            cursor.close();

        }
        return flyInfo;
    }

    @Override
    public FlyInfo getApiFlyInfo() {
        String countryCode = Locale.getDefault().getCountry();
        String languageCode = Locale.getDefault().getLanguage();
        String localeCode = languageCode+"_"+countryCode;
        String currencyCode = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
        String departurePlace = getUserCity();
        String date = new SimpleDateFormat(AppConstants.DATE_FORMAT).format(new Date());
        FlyRequest request =
                new FlyRequest(countryCode,currencyCode,localeCode,departurePlace, date);
        FlyResponse response = mFlyApiHelper.getFlyWithMinPrice(request);
        String imageUrl =  mImageApiHelper.getImageUrl(response.getDestinationPlace());
        Log.e(TAG,imageUrl);

        FlyInfo flyInfo = new FlyInfo(getUserCity(),
                getNearestAirport(),
                response.getDestinationPlace(),
                response.getDestinationCode(),
                response.getDepartureDate(),
                response.getMinPrice(),
                response.getCurrencySymbol(),
                imageUrl,
                response.getOrderUrl());
        return flyInfo;
    }

    @Override
    public Place getApiPlace() {
        GeoPlaceRequest request = new GeoPlaceRequest("65.54112","12.26545");
        GeoPlaceResponse response = mFlyApiHelper.getPlaceFromCoords(request);
        return new Place(response.getCode(),response.getName());
    }

    @Override
    public String insertFlyInfo(String departureCode,
                                String departureName,
                                String destinationCode,
                                String destinationName,
                                String price,
                                String orderUrl,
                                String departureDate,
                                String imageUrl,
                                String currencySymbol) {
        ContentValues values = new ContentValues();
        values.put(FlyEntry.COLUMN_FLY_DEPARTURE_CODE, departureCode);
        values.put(FlyEntry.COLUMN_FLY_DEPARTURE_NAME, departureName);
        values.put(FlyEntry.COLUMN_FLY_DESTINATION_CODE, destinationCode);
        values.put(FlyEntry.COLUMN_FLY_DESTINATION_NAME, destinationName);
        values.put(FlyEntry.COLUMN_FLY_PRICE, price);
        values.put(FlyEntry.COLUMN_FLY_ORDER_URL, orderUrl);
        values.put(FlyEntry.COLUMN_FLY_DEPARTURE_DATE, departureDate);
        values.put(FlyEntry.COLUMN_FLY_IMAGE_URL, imageUrl);
        values.put(FlyEntry.COLUMN_FLY_CURRENCY_SYMBOL, currencySymbol);
        values.put(FlyEntry.COLUMN_FLY_CREATED,System.currentTimeMillis());
        Uri uri = mContext.getContentResolver().insert(FlyEntry.CONTENT_URI, values);

        return null;
    }

    public String insertFlyInfo(FlyInfo flyInfo) {
        ContentValues values = new ContentValues();
        values.put(FlyEntry.COLUMN_FLY_DEPARTURE_CODE, flyInfo.getDeparture().getCode());
        values.put(FlyEntry.COLUMN_FLY_DEPARTURE_NAME, flyInfo.getDeparture().getFullName());
        values.put(FlyEntry.COLUMN_FLY_DESTINATION_CODE, flyInfo.getDestination().getCode());
        values.put(FlyEntry.COLUMN_FLY_DESTINATION_NAME, flyInfo.getDestination().getFullName());
        values.put(FlyEntry.COLUMN_FLY_PRICE, flyInfo.getPrice());
        values.put(FlyEntry.COLUMN_FLY_ORDER_URL, flyInfo.getOrderUrl());
        values.put(FlyEntry.COLUMN_FLY_DEPARTURE_DATE, AppUtils.formatDateToString(AppConstants.DATE_FORMAT,flyInfo.getDepartureDate()));
        values.put(FlyEntry.COLUMN_FLY_IMAGE_URL, flyInfo.getImageUrl());
        values.put(FlyEntry.COLUMN_FLY_CURRENCY_SYMBOL, flyInfo.getCurrencySymbol());
        values.put(FlyEntry.COLUMN_FLY_CREATED,System.currentTimeMillis());
        Uri uri = mContext.getContentResolver().insert(FlyEntry.CONTENT_URI, values);

        return uri.toString();
    }

    @Override
    public int deleteFlyInfo(int id) {
        Uri uri = Uri.parse(FlyEntry.CONTENT_URI+"/"+String.valueOf(id));
        int rowsDeleted =mContext.getContentResolver().delete(uri,null,null);
        return rowsDeleted;
    }

    @Override
    public ArrayList<FlyParameter> getFlyParameters(FlyInfo flyCard) {

        ArrayList<FlyParameter> flyParameters = new ArrayList<>();
        flyParameters.add(new FlyParameter(flyCard.getDestination().getFullName(), R.drawable.ic_location_on_black_24dp));

        String strDt = AppUtils.formatDateToString(AppConstants.DATE_FORMAT_PARAMETR,flyCard.getDepartureDate());
        flyParameters.add(new FlyParameter(strDt, R.drawable.ic_alarm_black_24dp));
        flyParameters.add(new FlyParameter(flyCard.getCurrencySymbol() + String.valueOf(flyCard.getPrice()), R.drawable.ic_attach_money_black_24dp));


        Intent linkIntent = new Intent(mContext, WebviewActivity.class);
        linkIntent.putExtra(AppConstants.KEY_WEBVIEW_URL, flyCard.getAboutUrl());
        flyParameters.add(new FlyParameter(mContext.getString(R.string.fly_parameter_about_city),
                linkIntent,
                R.drawable.ic_public_black_24dp));

        Intent calendarIntent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, String.format(mContext.getString(R.string.fly_parameter_calendar_topic), flyCard.getDestination().getFullName()))
                .putExtra(CalendarContract.Events.EVENT_LOCATION, flyCard.getDestination().getFullName())
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, flyCard.getDepartureDate())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, flyCard.getDepartureDate());
        flyParameters.add(new FlyParameter(mContext.getString(R.string.fly_parameter_calendar), calendarIntent, R.drawable.ic_event_black_24dp));

        return flyParameters;
    }




}
