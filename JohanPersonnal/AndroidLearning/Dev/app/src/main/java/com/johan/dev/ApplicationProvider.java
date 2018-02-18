package com.johan.dev;

import android.app.Application;
import android.content.Context;

/**
 * Created by johan on 13.09.2017.
 */

public class ApplicationProvider extends Application {
    private static Context sApplicationContext;

    @Override
    public void onCreate(){
        super.onCreate();
        sApplicationContext = getApplicationContext();
    }
    public static Context getContext(){
        return sApplicationContext;
    }
}
