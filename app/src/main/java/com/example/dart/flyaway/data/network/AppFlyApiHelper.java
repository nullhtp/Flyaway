package com.example.dart.flyaway.data.network;

import android.util.Log;

import com.example.dart.flyaway.data.network.model.FlyRequest;
import com.example.dart.flyaway.data.network.model.FlyResponse;
import com.example.dart.flyaway.data.network.model.GeoPlaceRequest;
import com.example.dart.flyaway.data.network.model.GeoPlaceResponse;
import com.example.dart.flyaway.utils.AppUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dart on 08.05.17.
 */

public class AppFlyApiHelper implements IFlyApiHelper {

    private static final String TAG = "AppFlyApiHelper";

    @Override
    public FlyResponse getFlyWithMinPrice(FlyRequest request) {
        String dtStart = "2017-05-19T15:15:00";
        Date date= AppUtils.formatStringToDate("yyyy-MM-dd'T'HH:mm:ss",dtStart);
        return new FlyResponse("10 529",date,"London","LHR","\u20BD",
                "http://www.apideeplink.com/transport_deeplink/4.0/RU/en-gb/RUB/ssev/1/13003.13554.2017-05-19/air/airli/flights?itinerary=flight%7c-31913%7c68%7c13003%7c2017-05-19T15%3a15%7c11051%7c2017-05-19T18%3a20%3bflight%7c-31913%7c4003%7c11051%7c2017-05-20T16%3a05%7c13554%7c2017-05-20T18%3a00&carriers=-31913&passengers=1&channel=dataapi&cabin_class=economy&facilitated=false&ticket_price=10529.00&is_npt=false&is_multipart=false&client_id=skyscanner_b2b&request_id=8a6cb1bc-7556-4e12-8c8e-25273fe8b858&deeplink_ids=eu-central-1.prod_6fe08e7105a18e10b4ae2e04118d70fd&commercial_filters=false&q_datetime_utc=2017-05-14T15%3a25%3a21&api_logo=http%3a%2f%2flogos.skyscnr.com%2fimages%2fpartners%2fdefault.png&api_pbs=true&_ga=2.249510991.977079419.1494775500-1715160024.1493975372&app_id=U3JSea2mKUEEs96eepPrHZhTJvxklImVgOia5xL%2bFQ47EB3asHYgslgEPX%2b%2b0FxE&is_external_partner_app=true");
    }

    @Override
    public GeoPlaceResponse getPlaceFromCoords(GeoPlaceRequest request) {
        return new GeoPlaceResponse("Kaliningrad","KGD");
    }


}
