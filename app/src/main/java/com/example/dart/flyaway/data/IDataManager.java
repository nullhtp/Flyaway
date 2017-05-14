package com.example.dart.flyaway.data;

import com.example.dart.flyaway.entities.FlyInfo;
import com.example.dart.flyaway.entities.FlyParameter;
import com.example.dart.flyaway.entities.Place;
import com.example.dart.flyaway.data.prefs.IPreferencesHelper;

import java.util.ArrayList;

/**
 * Created by dart on 08.05.17.
 */

public interface IDataManager extends IPreferencesHelper {
    FlyInfo getFlyInfo();
    FlyInfo getApiFlyInfo();
    Place getApiPlace();
    String insertFlyInfo(String departureCode,
                         String departureName,
                         String destinationCode,
                         String destinationName,
                         String price,
                         String orderUrl,
                         String departureDate,
                         String imageUrl,
                         String currencySymbol);
    int deleteFlyInfo(int id);

    ArrayList<FlyParameter> getFlyParameters(FlyInfo flyInfo);
}
