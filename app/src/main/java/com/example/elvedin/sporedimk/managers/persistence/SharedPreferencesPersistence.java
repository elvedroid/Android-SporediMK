package com.example.elvedin.sporedimk.managers.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.security.SignatureException;
import java.util.Set;

/**
 * Created by Boris on 02-Oct-17.
 */

public class SharedPreferencesPersistence implements IPersistence{
    private SharedPreferences sharedPreferences;

    public SharedPreferencesPersistence(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public boolean setBoolean(String key, boolean value) {
        return sharedPreferences.edit().putBoolean(key, value).commit();
    }

    @Override
    public boolean setInteger(String key, int value) {
        return sharedPreferences.edit().putInt(key, value).commit();
    }

    @Override
    public boolean setFloat(String key, float value) {
        return sharedPreferences.edit().putFloat(key, value).commit();
    }

    @Override
    public boolean setLong(String key, long value) {
        return sharedPreferences.edit().putLong(key, value).commit();
    }

    @Override
    public boolean setString(String key, String value) {
        return sharedPreferences.edit().putString(key, value).commit();
    }

    @Override
    public boolean setHashedString(String key, String value) {
//        String hash;
//        try {
//            hash = HashHelper.hashMac(value);
            return sharedPreferences.edit().putString(key, value).commit();
//        }
//        catch (SignatureException e)    {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean setStringSet(String key, Set<String> value) {
        return sharedPreferences.edit().putStringSet(key, value).commit();
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
    public boolean removeValueForKey(String key) {
        return sharedPreferences.edit().remove(key).commit();
    }

    @Override
    public boolean doesKeyExist(String key) {
        return sharedPreferences.contains(key);
    }
}
