package com.twodwarfs.shutterstock.utils;

import android.text.TextUtils;
import android.util.Log;

import com.twodwarfs.shutterstock.BuildConfig;
import com.twodwarfs.shutterstock.cons.Constants;

/**
 * Created by Aleksandar Balalovski
 * <abalalovski@gmail.com>
 */

public class Logger {

    public static void doLog(Object text) {
        if (BuildConfig.DEBUG && text != null && !TextUtils.isEmpty(text.toString())) {
            Log.i(Constants.TAG, text.toString());
        }
    }

    public static void doLogException(Exception e) {
        if (BuildConfig.DEBUG && e != null) {
            Log.e(Constants.TAG, "Exception", e);
        }
    }
}
