package com.example.dart.flyaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dart.flyaway.utils.AppConstants;
import com.example.dart.flyaway.data.prefs.AppPreferencesHelper;
import com.example.dart.flyaway.data.prefs.IPreferencesHelper;

public class MeetingActivity extends AppCompatActivity {

    private IPreferencesHelper mPrefHelper;
    private TextView mUserNameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        mPrefHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        String userName = mPrefHelper.getUserName();

        mUserNameText = (TextView) findViewById(R.id.meeting_name_edit);
        if (userName!=null && !userName.isEmpty()){
            mUserNameText.setText(userName);
        }

        Button saveButton = (Button) findViewById(R.id.meeting_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserNameText.getText().toString().trim();
                mPrefHelper.setUserName(userName);
                startActivity(new Intent(getApplicationContext(),LocationActivity.class));
                finish();
            }
        });

    }
}
