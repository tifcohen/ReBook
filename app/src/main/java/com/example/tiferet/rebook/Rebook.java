package com.example.tiferet.rebook;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Alon Abadi on 1/7/2016.
 */

public class Rebook extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
    }
}
