package com.example.elvedin.sporedimk.ui.manager;

import android.content.Context;
import android.widget.Toast;

import com.example.elvedin.sporedimk.ui.manager.log.ILogger;
import com.example.elvedin.sporedimk.ui.manager.log.LogLevel;
import com.example.elvedin.sporedimk.ui.manager.network_manager.ClientInterface;
import com.example.elvedin.sporedimk.ui.manager.network_manager.RemoteRepository;
import com.example.elvedin.sporedimk.ui.manager.persistance.IPersistance;

import retrofit2.Retrofit;

/**
 * Created by elvedin on 10/26/17.
 * Application interfaces helper
 */

public class AppHolder {

    private static AppHolder instance = null;

    private ILogger logger = null;
    private IPersistance persistance = null;
    private ClientInterface clientInterface = null;
    private RemoteRepository remoteRepository;

    private AppHolder() {
    }

    public static AppHolder getInstance() {
        if (instance == null) {
            instance = new AppHolder();
        }
        return instance;
    }

    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    private ILogger getLogger() {
        return logger;
    }

    public static void logWithToast(Context context, LogLevel logLevel, String tag, String message) {
        if (message != null) {
            getInstance().getLogger().log(logLevel, tag, message);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void log(LogLevel logLevel, String tag, String message) {
        if (message != null) {
            getInstance().getLogger().log(logLevel, tag, message);
        }
    }

    public IPersistance getPersistance() {
        return persistance;
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    public void setPersistance(IPersistance persistance) {
        this.persistance = persistance;
    }

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public RemoteRepository getRemoteRepository() {
        return remoteRepository;
    }

    public void setRemoteRepository(RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }
}

