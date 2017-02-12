package com.shevroman.android.myschedule;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by roman on 12.02.17.
 */

public class Preferences {
    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getSelectedGroup() {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString("currentGroup", "");
    }

    public void setSelectedGroup(String nameOfGroup) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nameOfGroup", nameOfGroup);
        editor.commit();


    }

}
