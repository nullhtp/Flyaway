package com.example.dart.flyaway.data.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.dart.flyaway.utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by dart on 08.05.17.
 */

public class AppImageApiHelper implements IImageApiHelper {

    private static final String TAG = "AppImageApiHelper";

    private final String API_IMAGE_URL = "https://pixabay.com/api/";
    private String imageUrl;


    @Override
    public  String getImageUrl(String query) {

        ANRequest request = AndroidNetworking.get(API_IMAGE_URL)
                .addQueryParameter("key", AppConstants.API_KEY_IMAGE_PIXABAY)
                .addQueryParameter("q", query)
                .addQueryParameter("image_type", "photo")
                .addQueryParameter("page", "2")
                .addQueryParameter("min_height", "500")
                .addQueryParameter("min_width", "500")
                .addQueryParameter("orientation", "horizontal")
                .setPriority(Priority.MEDIUM)
                .build();

        ANResponse<JSONObject> response = request.executeForJSONObject();
        if (response.isSuccess()) {
            try {
                JSONObject root = response.getResult();
                JSONArray features = root.getJSONArray("hits");

                if(features.length()>0){
                    JSONObject feature = features.getJSONObject(0);
                    imageUrl = feature.getString("webformatURL");
                }
            }
            catch(Exception e){}
        } else {
            Log.e(TAG,"Response filed");
        }
        return imageUrl;
    }
}
