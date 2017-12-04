package com.example.elvedin.sporedimk;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.webkit.WebView;

import com.example.elvedin.sporedimk.di.component.AppComponent;
import com.example.elvedin.sporedimk.di.component.DaggerAppComponent;
import com.example.elvedin.sporedimk.di.module.AppModule;
import com.example.elvedin.sporedimk.di.module.NetModule;
import com.example.elvedin.sporedimk.managers.persistence.Persistence;
import com.example.elvedin.sporedimk.managers.persistence.SharedPreferencesPersistence;
import com.example.elvedin.sporedimk.ui.manager.AppHolder;
import com.example.elvedin.sporedimk.ui.manager.log.LogCatLogger;
import com.example.elvedin.sporedimk.ui.manager.log.LogLevel;
import com.example.elvedin.sporedimk.ui.manager.network_manager.Client;
import com.example.elvedin.sporedimk.ui.manager.network_manager.ClientInterface;
import com.example.elvedin.sporedimk.ui.manager.network_manager.RemoteRepository;
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

public class MainApplication extends MultiDexApplication {
    private static final String TAG = "MainApplication";
    private static MainApplication mainApplication;

    public static MainApplication getInstance() {
        return mainApplication;
    }
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
        new WebView(this).destroy();

        if (Filter.test)
            initTrustManagerForAllHTTPSServers();

        AppHolder.getInstance().setLogger(new LogCatLogger());
        AppHolder.getInstance().setPersistance(new SharedPreferencesManager(getApplicationContext()));
        AppHolder.getInstance().setClientInterface(Client.getClient(Constants.BASE_URL, false).create(ClientInterface.class));
        AppHolder.getInstance().setRemoteRepository(new RemoteRepository(Client.getClient(Constants.BASE_URL, false)));

        Persistence.getInstance().init(new SharedPreferencesPersistence(getApplicationContext()));
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Constants.BASE_URL))
                .build();

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
                            Log.i("MainApplication", "TrustManager-Test");
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                            Log.i("MainApplication", "TrustManager-Test");
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            }, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            AppHolder.log(LogLevel.ERROR, TAG, e.getMessage());
        } catch (KeyManagementException e) {
            AppHolder.log(LogLevel.ERROR, TAG, e.getMessage());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    }

    @Override
    protected void attachBaseContext(Context base) {
        String lang = "mk";
        super.attachBaseContext(LocaleHelper.onAttach(base, lang));
        MultiDex.install(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}
