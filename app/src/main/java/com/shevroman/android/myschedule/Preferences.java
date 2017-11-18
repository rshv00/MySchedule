package com.shevroman.android.myschedule;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by roman on 18.02.17.
 */

public class Preferences {
    public String getSelectedGroup(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
            return preferences.getString("nameOfGroup", "");
    }

    public void setSelectedGroup(String nameOfGroup, Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nameOfGroup", nameOfGroup);
        editor.apply();
    }
}