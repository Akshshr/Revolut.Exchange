package com.revolutexchange.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revolutexchange.BuildConfig;
import com.revolutexchange.api.util.UserAgentInterceptor;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFactory {

//    https://revolut.duckdns.org/latest?base=EUR
    private static final String TAG = ApiServiceFactory.class.getSimpleName();

    private final Retrofit retrofit;

    public ApiServiceFactory(final String apiHost) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(apiHost)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createClient(gson))
                .build();
    }

    private OkHttpClient createClient(Gson gson) {
        final OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addNetworkInterceptor(chain -> chain.proceed(chain.request()));

        String UserAgent = System.getProperty("http.agent");
        String transformedUserAgent = String.format(
                Locale.US, "Revolut Exchange/%s (versionCode: %d%s)",
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE,
                UserAgent);

        client.interceptors().add(new UserAgentInterceptor(transformedUserAgent));
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.readTimeout(60, TimeUnit.SECONDS);

        return client.build();
    }


    public <T> T createApiService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }




}
