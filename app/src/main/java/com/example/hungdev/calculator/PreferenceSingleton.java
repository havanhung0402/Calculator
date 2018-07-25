package com.example.hungdev.calculator;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hungdev on 23/07/2018.
 */

class PreferenceSingleton {
    public static final String PREFERENCES_FILE_KEY = "Recovery result";
    public static final String PREFERENCES_OBJECT_KEY = "result";
    private static  SharedPreferences.Editor editor = null;
    private static  SharedPreferences sharedPreferences = null;

    public static SharedPreferences.Editor getEditor(Context context) {
        if(editor == null){
            sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY,Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return editor;
    }
    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY,Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
