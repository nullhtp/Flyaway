package com.example.dart.flyaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.dart.flyaway.utils.AppConstants;

public class WebviewActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String url = getString(R.string.partner_url);

        if (getIntent().hasExtra(AppConstants.KEY_WEBVIEW_URL)) {
            url = getIntent().getStringExtra(AppConstants.KEY_WEBVIEW_URL);
        }

        Log.e("URL: ", url);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
    }


}
