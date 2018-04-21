package org.emobile.myitmarket.manager.persistance;

import java.util.Set;

/**
 * Created by elvedin on 10/26/17.
 */

public interface IPersistance {

    public void setBoolean(String key, boolean value);

    public void setInteger(String key, int value);

    public void setFloat(String key, float value);

    public void setLong(String key, long value);

    public void setString(String key, String value);

    public void setStringSet(String key, Set<String> value);

    public boolean getBoolean(String key, boolean defaultValue);

    public int getInteger(String key, int defaultValue);

    public float getFloat(String key, float defaultValue);

    public long getLong(String key, long defaultValue);

    public String getString(String key, String defaultValue);

    public Set<String> getStringSet(String key, Set<String> defaultValue);

    public void removeValueForKey(String key);

    public boolean doesKeyExist(String key);
}
