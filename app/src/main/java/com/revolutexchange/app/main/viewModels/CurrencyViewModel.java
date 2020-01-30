package com.revolutexchange.app.main.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.revolutexchange.api.model.ExchangeData;
import com.revolutexchange.app.RevolutExchangeApplication;
import com.revolutexchange.app.base.BaseActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CurrencyViewModel extends ViewModel {

    private MutableLiveData<ExchangeData> mExchangeData = new MutableLiveData<>();
    private BaseActivity baseActivity;

    public CurrencyViewModel() {
        baseActivity = (BaseActivity) RevolutExchangeApplication.getApplicationInstance().getActivityInstance();
    }

    public void getExchanegDataFromApi(String key) {
        baseActivity.getApi().getExchangePriceApi()
                .getExchange(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(baseActivity.getLifecycleEvents(BaseActivity.ActivityEvent.DESTROY))
                .subscribe(
                        this::onExchangeRates,
                        throwable -> baseActivity.handleError(throwable));
    }

    private void onExchangeRates(ExchangeData exchangeData) {
        mExchangeData.postValue(exchangeData);
    }

    public MutableLiveData<ExchangeData> getExchangeData() {
        return mExchangeData;
    }



}

