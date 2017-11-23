package com.example.elvedin.sporedimk;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.webkit.WebView;

import com.example.elvedin.sporedimk.managers.persistence.Persistence;
import com.example.elvedin.sporedimk.managers.persistence.SharedPreferencesPersistence;
import com.example.elvedin.sporedimk.ui.manager.AppHolder;
import com.example.elvedin.sporedimk.ui.manager.log.LogCatLogger;
import com.example.elvedin.sporedimk.ui.manager.log.LogLevel;
import com.example.elvedin.sporedimk.ui.manager.network_manager.Client;
import com.example.elvedin.sporedimk.ui.manager.network_manager.ClientInterface;
import com.example.elvedin.sporedimk.ui.manager.persistance.SharedPreferencesManager;
import com.example.elvedin.sporedimk.utils.Constants;
import com.example.elvedin.sporedimk.utils.LocaleHelper;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by elvedin on 10/26/17.
 */

public class MainApplication extends MultiDexApplication {
    private static MainApplication mainApplication;
    public static final String LOG_TAG = "mWallet";
    public static final boolean isTest = true;


    public static MainApplication getInstance(){
        return mainApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
        new WebView(this).destroy();
//        new FontsOverride(this).loadFonts();

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("font/telefonts.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );


        if (isTest)
            initTrustManagerForAllHTTPSServers();

        AppHolder.getInstance().setContext(getApplicationContext());
        AppHolder.getInstance().setLogger(new LogCatLogger());
        AppHolder.getInstance().setPersistance(new SharedPreferencesManager(getApplicationContext()));
        AppHolder.getInstance().setClientInterface(Client.getClient(Constants.BASE_URL, false).create(ClientInterface.class));

        Persistence.getInstance().init(new SharedPreferencesPersistence(getApplicationContext()));
    }


    /**
     * This method is for testing purposes only.
     * It initializes the networking requests to accept all types of certificates
     */
    private void initTrustManagerForAllHTTPSServers() {
        SSLContext ctx;
        try {
            ctx = SSLContext.getInstance("TLS");

            ctx.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {

                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) {

                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            }, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            AppHolder.log(LogLevel.ERROR, e.getMessage(), false);
        } catch (KeyManagementException e) {
            AppHolder.log(LogLevel.ERROR, e.getMessage(), false);
        }
        HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    }

    @Override
    protected void attachBaseContext(Context base) {
        String lang = "en";
        AppHolder.getInstance().setLanguage(lang);
        super.attachBaseContext(LocaleHelper.onAttach(base, lang));
        MultiDex.install(this);
    }
}
