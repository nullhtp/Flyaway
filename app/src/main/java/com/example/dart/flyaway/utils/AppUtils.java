package com.example.dart.flyaway.utils;

import android.util.Log;

import com.example.dart.flyaway.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dart on 13.05.17.
 */

public class AppUtils {

    private static final String TAG = "AppUtils";

    public static Date formatStringToDate(String formatString, String dateString){
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        Date date = new Date();
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateToString(String formatString, Date date){
        SimpleDateFormat simpleDate = new SimpleDateFormat(formatString);
        String stringDate = simpleDate.format(date);
        return stringDate;
    }
}
