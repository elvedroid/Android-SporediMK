package com.example.elvedin.sporedimk.ui.manager.persistance;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.elvedin.sporedimk.utils.Constants;

import java.util.Set;

/**
 * Created by elvedin on 10/26/17.
 */

public class SharedPreferencesManager implements IPersistance {
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public void setInteger(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public void setFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    @Override
    public void setLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    @Override
    public void setString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public void setStringSet(String key, Set<String> value) {
        sharedPreferences.edit().putStringSet(key, value).apply();
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public int getInteger(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    @Override
    public void removeValueForKey(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    @Override
    public boolean doesKeyExist(String key) {
        return sharedPreferences.contains(key);
    }
}
