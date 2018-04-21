package org.emobile.myitmarket;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.webkit.WebView;

import org.emobile.myitmarket.base.di.component.AppComponent;
import org.emobile.myitmarket.base.di.component.DaggerAppComponent;
import org.emobile.myitmarket.base.di.module.AppModule;
import org.emobile.myitmarket.manager.persistence.Persistence;
import org.emobile.myitmarket.manager.persistence.SharedPreferencesPersistence;
import org.emobile.myitmarket.manager.AppHolder;
import org.emobile.myitmarket.manager.log.LogCatLogger;
import org.emobile.myitmarket.manager.log.LogLevel;
import org.emobile.myitmarket.manager.network_manager.Client;
import org.emobile.myitmarket.manager.network_manager.ClientInterface;
import org.emobile.myitmarket.manager.network_manager.RemoteRepository;
import org.emobile.myitmarket.manager.persistance.SharedPreferencesManager;
import org.emobile.myitmarket.utils.Constants;
import org.emobile.myitmarket.utils.Filter;
import org.emobile.myitmarket.utils.LocaleHelper;

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
//        appComponent = DaggerAppComponent.builder()
//                .appModule(new AppModule(this))
//                .netModule(new NetModule(Constants.BASE_URL))
//                .build();
//
        initializeInjector();
    }

    private void initializeInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, Constants.BASE_URL))
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
