package com.example.elvedin.sporedimk.ui.manager;

import android.content.Context;
import android.widget.Toast;

import com.example.elvedin.sporedimk.model.Device;
import com.example.elvedin.sporedimk.ui.manager.log.ILogger;
import com.example.elvedin.sporedimk.ui.manager.log.LogLevel;
import com.example.elvedin.sporedimk.ui.manager.network_manager.ClientInterface;
import com.example.elvedin.sporedimk.ui.manager.persistance.IPersistance;

/**
 * Created by elvedin on 10/26/17.
 */

public class AppHolder {

    private static AppHolder instance = null;

    private ILogger logger = null;
    private IPersistance persistance = null;
    private ClientInterface clientInterface = null;
    private Context context;

    private String deviceId = null;
    private String phoneNr = null;

    private Device device = null;

    private String language  = null;

    private AppHolder(){

    }

    public static AppHolder getInstance() {
        if(instance == null){
            instance = new AppHolder();
        }
        return instance;
    }

    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    public ILogger getLogger() {
        return logger;
    }

    public static void log(LogLevel logLevel, String message, boolean showToast){
        if(message!=null) {
            getInstance().getLogger().log(logLevel, "mWallet", message);
            if(showToast){
                Toast.makeText(getInstance().getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public IPersistance getPersistance() {
        return persistance;
    }

    public String getDeviceId() {
        if(deviceId == null)
            deviceId = "";
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }



    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public Device getDevice() {
        if(device == null)
            device = new Device();
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setPersistance(IPersistance persistance) {
        this.persistance = persistance;
    }

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

