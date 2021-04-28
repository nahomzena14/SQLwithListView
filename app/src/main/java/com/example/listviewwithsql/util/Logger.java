package com.example.listviewwithsql.util;

import android.util.Log;

public class Logger {

    public static final String TAG = "TAG_X";

    public static void logMessage(String message){
        Log.d(TAG, message);
    }
}