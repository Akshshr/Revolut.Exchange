package com.revolutexchange.api.util;

import java.io.IOException;

import okhttp3.Interceptor;

public class UserAgentInterceptor implements Interceptor {

    private final String transformedUserAgent;

    public UserAgentInterceptor(String transformedUserAgent) {
        this.transformedUserAgent = transformedUserAgent;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Request originRequest = chain.request();
        okhttp3.Request requestWithUserAgent = originRequest.newBuilder()
                .header("User-Agent", transformedUserAgent)
                .build();
        return chain.proceed(requestWithUserAgent);
    }



}
