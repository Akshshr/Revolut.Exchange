package com.revolutexchange.api.service;

import com.revolutexchange.api.model.ExchangeData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ExchangeApiEndpoint {


    @GET("latest")
    Observable<ExchangeData> getExchange(@Query("base") String key);

}
