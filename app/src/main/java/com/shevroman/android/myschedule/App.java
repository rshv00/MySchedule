package com.shevroman.android.myschedule;

import android.app.Application;

/**
 * Created by Рома on 04.02.2017.
 */

public class App extends Application {
    private static App instance;
    private ScheduleRepository scheduleRepository = new ScheduleRepository();

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public ScheduleRepository getScheduleRepository() {
        return scheduleRepository;
    }
}
