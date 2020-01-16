package com.example.harjoitustyopankkiapplikaatio;


import android.app.Application;
import android.content.Context;

public class ContextClass extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ContextClass.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ContextClass.context;
    }
}