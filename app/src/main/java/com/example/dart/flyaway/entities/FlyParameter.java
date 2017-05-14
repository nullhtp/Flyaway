package com.example.dart.flyaway.entities;

import android.content.Intent;

/**
 * Created by dart on 07.05.17.
 */

public class FlyParameter {
    private String mDescription;
    private Intent mIntent;
    private int mImageResourceId;

    private static final int NO_IMAGE_PROVIDED = -1;

    public FlyParameter(String description, Intent intent, int imageResourceId) {
        mDescription = description;
        mIntent = intent;
        mImageResourceId = imageResourceId;
    }

    public FlyParameter(String description, Intent intent) {
        mDescription = description;
        mIntent = intent;
        mImageResourceId = NO_IMAGE_PROVIDED;
    }

    public FlyParameter(String description, int imageResourceId) {
        mDescription = description;
        mImageResourceId = imageResourceId;
    }

    public String getDescription() {
        return mDescription;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }


    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public boolean hasIntent() {
        return mIntent != null;
    }

}
