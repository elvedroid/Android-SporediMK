package org.emobile.myitmarket.manager.log;

import android.util.Log;

/**
 * Created by Risto Muchev on 02-May-16.
 */
public class LogCatLogger implements ILogger{
    @Override
    public void log(LogLevel logLevel, String tag, String message) {
        if (logLevel == LogLevel.DEBUG) {
            Log.d(tag, "DEBUG || " + message);
        } else if (logLevel == LogLevel.INFO) {
            Log.i(tag, "INFO || " + message);
        } else if (logLevel == LogLevel.WARN) {
            Log.w(tag, "WARNING || " + message);
        } else if (logLevel == LogLevel.ERROR) {
            Log.e(tag, "ERROR || " + message);
        } else if (logLevel == LogLevel.ASSERT) {
            Log.wtf(tag, "ASSERT || " + message);
        } else if (logLevel == LogLevel.VERBOSE) {
            Log.v(tag, "VERBOSE || " + message);
        }
    }
}
