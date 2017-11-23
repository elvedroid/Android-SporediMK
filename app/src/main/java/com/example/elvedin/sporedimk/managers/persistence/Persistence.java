package com.example.elvedin.sporedimk.managers.persistence;

import java.util.Set;

/**
 * Created by Boris on 02-Oct-17.
 */

public class Persistence {

    private boolean init = false;
    private static Persistence instance = null;

    private IPersistence persistence = null;

    private Persistence() {
    }

    public void init(IPersistence persistence) {
        if(!init) {
            this.persistence = persistence;
            init = true;
        }
    }

    public static Persistence getInstance() {
        if (instance == null) {
            instance = new Persistence();
        }
        return instance;
    }

    public IPersistence getPersistence() {
        return persistence;
    }

    public static boolean setBoolean(String key, boolean value) {
        return getInstance().getPersistence().setBoolean(key, value);
    }

    public static boolean setInteger(String key, int value) {
        return getInstance().getPersistence().setInteger(key, value);
    }

    public static boolean setFloat(String key, float value) {
        return getInstance().getPersistence().setFloat(key, value);
    }

    public static boolean setLong(String key, long value) {
        return getInstance().getPersistence().setLong(key, value);
    }

    public static boolean setString(String key, String value) {
        return getInstance().getPersistence().setString(key, value);
    }

    public static boolean setHashedString(String key, String value) {
        return getInstance().getPersistence().setHashedString(key,value);
    }

    public static boolean setStringSet(String key, Set<String> value) {
        return getInstance().getPersistence().setStringSet(key, value);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getInstance().getPersistence().getBoolean(key, defaultValue);
    }

    public static int getInteger(String key, int defaultValue) {
        return getInstance().getPersistence().getInteger(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return getInstance().getPersistence().getFloat(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return getInstance().getPersistence().getLong(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return getInstance().getPersistence().getString(key, defaultValue);
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValue) {
        return getInstance().getPersistence().getStringSet(key, defaultValue);
    }

    public static boolean removeValueForKey(String key){
        return getInstance().getPersistence().removeValueForKey(key);
    }

    public static boolean doesKeyExist(String key){
        return getInstance().getPersistence().doesKeyExist(key);
    }
}

