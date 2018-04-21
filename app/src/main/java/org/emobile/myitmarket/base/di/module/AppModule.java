package org.emobile.myitmarket.base.di.module;

import android.app.Application;
import android.support.annotation.NonNull;

import org.emobile.myitmarket.utils.Filter;
import org.emobile.myitmarket.base.scheduler.SchedulerProvider;
import org.emobile.myitmarket.manager.network_manager.RemoteRepository;
import org.emobile.myitmarket.utils.Constants;
import org.emobile.myitmarket.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by elvedin on 12/4/17.
 */

@Module
public class AppModule {
    Application mApplication;
    private String baseUrl;

    public AppModule(Application mApplication, String baseUrl) {
        this.mApplication = mApplication;
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        OkHttpClient.Builder client = Filter.test ? Utils.getUnsafeOkHttpClient() : new OkHttpClient.Builder();
        client.cache(cache);
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader(Constants.ACCEPT_HEADER, "application/json")
                        .build();
                return chain.proceed(request);
            }
        }).connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    RemoteRepository provideRemoteRepository(Retrofit retrofit) {
        return new RemoteRepository(retrofit);
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider(SchedulerProvider schedulerProvider) {
        return schedulerProvider;
    }
}
