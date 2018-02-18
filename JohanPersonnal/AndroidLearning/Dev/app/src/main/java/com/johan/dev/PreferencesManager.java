package com.johan.dev;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by johan on 13.09.2017.
 */

public class PreferencesManager {
    private static PreferencesManager ourInstance = new PreferencesManager();
    private SharedPreferences mPreferenceManager;
    public static PreferencesManager getInstance(){
        return ourInstance;
    }
    private PreferencesManager(){
        mPreferenceManager = PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getContext());
    }

    public String getUserName(){
        return mPreferenceManager.getString("pref_username", null);
    }
}

