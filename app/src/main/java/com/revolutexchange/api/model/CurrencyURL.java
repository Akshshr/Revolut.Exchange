package com.revolutexchange.api.model;

public class CurrencyURL {

    private String currency;
    private String flagUrl;

    public CurrencyURL(String currency, String flagUrl) {
        this.currency = currency;
        this.flagUrl = flagUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }


}
