package com.example.dart.flyaway.data.network;

import com.example.dart.flyaway.data.network.model.FlyRequest;
import com.example.dart.flyaway.data.network.model.FlyResponse;
import com.example.dart.flyaway.data.network.model.GeoPlaceRequest;
import com.example.dart.flyaway.data.network.model.GeoPlaceResponse;

import java.util.Observable;

/**
 * Created by dart on 08.05.17.
 */

public interface IFlyApiHelper {

    FlyResponse getFlyWithMinPrice(FlyRequest request);
    GeoPlaceResponse getPlaceFromCoords(GeoPlaceRequest request);
}
