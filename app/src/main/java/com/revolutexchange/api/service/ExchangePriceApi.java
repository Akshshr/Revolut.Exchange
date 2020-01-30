package com.revolutexchange.api.service;

import com.revolutexchange.api.ApiServiceFactory;
import com.revolutexchange.api.model.ExchangeData;

import rx.Observable;

public class ExchangePriceApi {

    private ExchangeApiEndpoint exchangeApiEndpoint;

    public ExchangePriceApi(ApiServiceFactory apiServiceFactory) {
        this.exchangeApiEndpoint = apiServiceFactory.createApiService(ExchangeApiEndpoint.class);
    }

    public Observable<ExchangeData> getExchange(String key) {
        return exchangeApiEndpoint.getExchange(key);
    }



}
