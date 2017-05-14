package com.example.dart.flyaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dart.flyaway.utils.AppConstants;
import com.example.dart.flyaway.data.AppDataManager;
import com.example.dart.flyaway.data.IDataManager;
import com.example.dart.flyaway.data.network.AppFlyApiHelper;
import com.example.dart.flyaway.data.network.AppImageApiHelper;
import com.example.dart.flyaway.data.prefs.AppPreferencesHelper;

public class LocationActivity extends AppCompatActivity {


    private IDataManager mDataManager;
    private TextView mUserNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        mDataManager = new AppDataManager(getApplicationContext(),
                new AppPreferencesHelper(getApplicationContext(), AppConstants.PREF_NAME),
                new AppFlyApiHelper(),
                new AppImageApiHelper());

        String userName = mDataManager.getUserName();
        String userCity = mDataManager.getUserCity();

        if (userName==null || userName.isEmpty()){
            finish();
        }

        mUserNameText = (TextView) findViewById(R.id.meeting_name_edit);
        mUserNameText.setHint(R.string.location_activity_edit_hint);

        if (userCity!=null && !userCity.isEmpty()){
            mUserNameText.setText(userCity);
        }

        TextView meetingText = (TextView) findViewById(R.id.meeting_greeting_text);
        meetingText.setText(String.format(getString(R.string.location_activity_message), userName));


        Button saveButton = (Button) findViewById(R.id.meeting_save_button);
        saveButton.setText(R.string.location_activity_button_text);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = mUserNameText.getText().toString().trim();
                mDataManager.setUserCity(cityName);
                mDataManager.setNearestAirport(mDataManager.getApiPlace().getCode());
                finish();
            }
        });
    }
}
