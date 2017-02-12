package com.example.android.myschedule;

import android.app.Application;

/**
 * Created by Рома on 04.02.2017.
 */

public class App extends Application {
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
    public static App getInstance() {
        return instance;
    }
}
