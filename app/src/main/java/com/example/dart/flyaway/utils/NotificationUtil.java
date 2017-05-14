package com.example.dart.flyaway.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.dart.flyaway.MainActivity;

/**
 * Created by dart on 09.05.17.
 */

public class NotificationUtil {

    private static NotificationCompat.Builder mBuilder;
    private static NotificationManager mNotifyMgr;
    private static int mNotificationId;
    private NotificationUtil(){

    }

    public static void notification(Context context, int imageRes, String title, String text){
        if(mBuilder==null) {
            mBuilder = new NotificationCompat.Builder(context);
        }

        if(mNotifyMgr==null) {
            mNotifyMgr =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        }
        mBuilder.setSmallIcon(imageRes);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationId++;
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
