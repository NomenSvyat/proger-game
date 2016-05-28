package com.sml.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tim on 28.05.2016.
 */
public class SettingsService {

    private static final String PREFS_NAME = "user_settings";

    private static SettingsService instance;
    public static SettingsService getInstance() {
        if (instance == null)
            instance = new SettingsService();
        return instance;
    }

    private SettingsService() {}

    public void saveString(Context ctx, String key, String value) {
        SharedPreferences preferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(Context ctx, String key) {
        SharedPreferences preferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
}
