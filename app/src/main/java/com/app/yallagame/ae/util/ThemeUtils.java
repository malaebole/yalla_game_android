package com.app.yallagame.ae.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeUtils {

    private static final String PREFERENCES_NAME = "theme_preferences";
    private static final String KEY_THEME = "theme";

    public static void applyTheme(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean isDarkTheme = preferences.getBoolean(KEY_THEME, false);
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static void toggleTheme(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean isDarkTheme = preferences.getBoolean(KEY_THEME, false);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_THEME, !isDarkTheme);
        editor.apply();
    }

    public static boolean isDarkTheme(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_THEME, false);
    }
}
