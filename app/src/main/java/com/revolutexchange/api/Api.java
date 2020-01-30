package com.revolutexchange.api;

import com.revolutexchange.api.service.ExchangePriceApi;

public class Api {

    private ExchangePriceApi exchangePriceApi;

    public Api(String apiHost) {
        exchangePriceApi = new ExchangePriceApi(new ApiServiceFactory(apiHost));
    }


    public ExchangePriceApi getExchangePriceApi() {
        return exchangePriceApi;
    }




}
