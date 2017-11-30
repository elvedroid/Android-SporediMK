package com.example.elvedin.sporedimk.ui.manager.network_manager;

import android.support.annotation.NonNull;

import com.example.elvedin.sporedimk.Filter;
import com.example.elvedin.sporedimk.utils.Constants;
import com.example.elvedin.sporedimk.utils.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by elvedin on 10/26/17.
 * Retrofit client
 */

public class Client {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, boolean forceInit) {
        //Keeping the builder with the latest data from the http client
        if (retrofit == null || forceInit) {

            OkHttpClient.Builder okHttpBuilder = Filter.test ? Utils.getUnsafeOkHttpClient() : new OkHttpClient.Builder();

            OkHttpClient okHttpClient = okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader(Constants.ACCEPT_HEADER, "application/json")
                            .build();
                    return chain.proceed(request);
                }
            }).connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS).build();

            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(
                                    GsonConverterFactory.create()
                            );

            retrofit = builder
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }
}
