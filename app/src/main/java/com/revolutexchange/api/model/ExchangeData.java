package com.revolutexchange.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class ExchangeData {

    private String base;
    private String date;
    private String baseValue;
    private @SerializedName("rates")
    HashMap<String, Double> rates;
    private List<Rates> rateList;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public List<Rates> getRateList() {
        return rateList;
    }

    public void setRateList(List rateList) {
        this.rateList = rateList;
    }

    public String getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(String baseValue) {
        this.baseValue = baseValue;
    }

    //    {
//        "base":"EUR",
//            "date":"2018-09-06",
//            "rates":{
//        "AUD":1.6232 Euro,
//                "BGN":1.964,
//                "BRL":4.8119,
//                "CAD":1.5402,
//                "CHF":1.1322,
//                "CNY":7.9784,
//                "CZK":25.823,
//                "DKK":7.4879,
//                "GBP":0.902,
//                "HKD":9.1707,
//                "HRK":7.4652,
//                "HUF":327.86,
//                "IDR":17396.0,
//                "ILS":4.1881,
//                "INR":84.068,
//                "ISK":128.34,
//                "JPY":130.09,
//                "KRW":1310.2,
//                "MXN":22.459,
//                "MYR":4.8322,
//                "NOK":9.817,
//                "NZD":1.7707,
//                "PHP":62.854,
//                "PLN":4.3364,
//                "RON":4.6579,
//                "RUB":79.908,
//                "SEK":10.635,
//                "SGD":1.6067,
//                "THB":38.29,
//                "TRY":7.6602,
//                "USD":1.1683,
//                "ZAR":17.898
//    }
//    }


}
