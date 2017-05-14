package com.example.dart.flyaway.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.dart.flyaway.R;
import com.example.dart.flyaway.data.AppDataManager;
import com.example.dart.flyaway.data.network.AppFlyApiHelper;
import com.example.dart.flyaway.data.network.AppImageApiHelper;
import com.example.dart.flyaway.data.prefs.AppPreferencesHelper;
import com.example.dart.flyaway.entities.FlyInfo;
import com.example.dart.flyaway.utils.AppConstants;
import com.example.dart.flyaway.utils.AppUtils;
import com.example.dart.flyaway.utils.NotificationUtil;

/**
 * Created by dart on 09.05.17.
 */

public class NotifyService extends Service {

    private static final String TAG = "NotifyService";

    /**
     * indicates how to behave if the service is killed
     */
    int mStartMode;

    /**
     * interface for clients that bind
     */
    IBinder mBinder;

    Thread mThread;

    /**
     * indicates whether onRebind should be used
     */
    boolean mAllowRebind;

    /**
     * Called when the service is being created.
     */
    @Override
    public void onCreate() {

    }

    /**
     * The service is starting, due to a call to startService()
     */
    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        mThread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        int second = 1000;
                        int minute = 60 * second;
                        int hour = minute * 60;

                        AppDataManager dataManager = new AppDataManager(getApplicationContext(),
                                new AppPreferencesHelper(getApplicationContext(), AppConstants.PREF_NAME),
                                new AppFlyApiHelper(),
                                new AppImageApiHelper());
                        FlyInfo flyInfo = null;
                        while (dataManager.getNearestAirport()==null){
                            Thread.sleep(500);
                        }
                        while (flyInfo==null||flyInfo.getDeparture().getCode()==null){
                            try {
                                flyInfo = dataManager.getApiFlyInfo();
                                dataManager.insertFlyInfo(flyInfo);

                            }catch (Exception e){
                                Thread.sleep(2*second);
                            }
                            Log.e(TAG,"Try get fly info");
                        }

                        NotificationUtil.notification(getApplicationContext(),
                                R.drawable.ic_alarm_black_24dp,
                                String.format(getString(R.string.notification_title), dataManager.getUserName()),
                                String.format(getString(R.string.notification_message),
                                        flyInfo.getDestination().getFullName(),
                                        AppUtils.formatDateToString(AppConstants.DATE_FORMAT_PARAMETR,
                                                flyInfo.getDepartureDate()),
                                        flyInfo.getCurrencySymbol(),
                                        flyInfo.getPrice()));
                        Thread.sleep(hour*4);

                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
        mThread.start();

        return mStartMode;
    }

    /**
     * A client is binding to the service with bindService()
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Called when all clients have unbound with unbindService()
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /**
     * Called when a client is binding to the service with bindService()
     */
    @Override
    public void onRebind(Intent intent) {

    }

    /**
     * Called when The service is no longer used and is being destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.stop();
    }
}
