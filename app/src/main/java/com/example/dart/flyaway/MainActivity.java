package com.example.dart.flyaway;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.widget.ANImageView;
import com.example.dart.flyaway.data.AppDataManager;
import com.example.dart.flyaway.data.IDataManager;
import com.example.dart.flyaway.data.network.AppFlyApiHelper;
import com.example.dart.flyaway.data.network.AppImageApiHelper;
import com.example.dart.flyaway.data.prefs.AppPreferencesHelper;
import com.example.dart.flyaway.entities.FlyInfo;
import com.example.dart.flyaway.entities.FlyParameter;
import com.example.dart.flyaway.service.NotifyService;
import com.example.dart.flyaway.utils.AppConstants;
import com.example.dart.flyaway.utils.NetworkUtils;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    public ArrayList<FlyParameter> flyParameters;
    private IDataManager mDataManager;
    private FlyInfo mFlyCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!NetworkUtils.isNetworkConnected(this)) {
            setContentView(R.layout.information_layout);
            TextView informationMessage = (TextView) findViewById(R.id.information_message);
            informationMessage.setText(R.string.error_internet_connection);
            return;
        }

        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());




        mDataManager = new AppDataManager(getApplicationContext(),
                new AppPreferencesHelper(getApplicationContext(), AppConstants.PREF_NAME),
                new AppFlyApiHelper(),
                new AppImageApiHelper());

        String userName = mDataManager.getUserName();
        String userCity = mDataManager.getUserCity();

        if (userName == null || userName.isEmpty()) {
            startActivityForResult(new Intent(this, MeetingActivity.class),RESULT_OK);
        }
        if (userCity == null || userCity.isEmpty()) {
            startActivityForResult(new Intent(this, LocationActivity.class),RESULT_OK);
        }

        startService(new Intent(getBaseContext(), NotifyService.class));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mFlyCard = mDataManager.getFlyInfo();

        if(mFlyCard == null){
            setContentView(R.layout.information_layout);
            TextView informationMessage = (TextView) findViewById(R.id.information_message);
            informationMessage.setText(R.string.error_no_any_fly);
            Button buttonHelp = (Button) findViewById(R.id.information_button);
            buttonHelp.setVisibility(View.VISIBLE);
            buttonHelp.setText(R.string.error_no_any_fly_button_text);
            buttonHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), WebviewActivity.class));
                }
            });

            Button buttonUpdate = (Button) findViewById(R.id.information_button_update);
            buttonUpdate.setVisibility(View.VISIBLE);
            buttonUpdate.setText(R.string.error_no_any_fly_button_update_text);
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }
            });

            return;
        }

        ANImageView imageView = (ANImageView) findViewById(R.id.main_thumb_image);
        imageView.setDefaultImageResId(R.drawable.empty_image);
        imageView.setErrorImageResId(R.drawable.empty_image);
        imageView.setImageUrl(mFlyCard.getImageUrl());



        TextView orderText = (TextView) findViewById(R.id.main_link_order_text);
        orderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkIntent = new Intent(getApplicationContext(), WebviewActivity.class);
                linkIntent.putExtra(AppConstants.KEY_WEBVIEW_URL, mFlyCard.getOrderUrl());
                startActivity(linkIntent);
            }
        });

        TextView moreText = (TextView) findViewById(R.id.main_link_more_text);
        moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebviewActivity.class));
            }
        });

        flyParameters = mDataManager.getFlyParameters(mFlyCard);

        FlyParameterAdapter flyParameterAdapter =
                new FlyParameterAdapter(this, flyParameters);
        ListView listView = (ListView) findViewById(R.id.list_fly_parameter);
        listView.setAdapter(flyParameterAdapter);

        TextView departureCodeText = (TextView) findViewById(R.id.main_thumb_departure_text);
        TextView destinationCodeText = (TextView) findViewById(R.id.main_thumb_destination_text);

        departureCodeText.setText(mFlyCard.getDeparture().getCode());
        destinationCodeText.setText(mFlyCard.getDestination().getCode());
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
