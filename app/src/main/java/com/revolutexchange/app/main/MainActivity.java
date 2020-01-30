package com.revolutexchange.app.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.transition.TransitionManager;

import com.revolutexchange.R;
import com.revolutexchange.api.model.ExchangeData;
import com.revolutexchange.api.model.Rates;
import com.revolutexchange.app.base.BaseActivity;
import com.revolutexchange.app.base.WelcomeFragment;
import com.revolutexchange.app.main.adapter.CurrencyAdapter;
import com.revolutexchange.app.main.viewModels.CurrencyViewModel;
import com.revolutexchange.databinding.ActivityMainBinding;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final String DEFAULT_CURRENCY = "usd";
    CurrencyAdapter adapter;
    private CurrencyViewModel mCurrencyViewModel;
    private WelcomeFragment welcomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.toolbar.setTitle(getResources().getString(R.string.app_name));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mCurrencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
    }

    //Run all your network calls here from the view model for this activity
    private void fetchAndObserveExchange(String currency) {
        binding.setLoading(true);
        mCurrencyViewModel.getExchanegDataFromApi(currency);
        mCurrencyViewModel.getExchangeData().observe(this, this::onExchangeRates);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAndObserveExchange(!getAppPreferences().getPreferredCurrency().get().isEmpty() ? getAppPreferences().getPreferredCurrency().get() : DEFAULT_CURRENCY);
    }

    //API Response and setting the adapter
    private void onExchangeRates(ExchangeData exchangeData) {
        binding.setLoading(false);
        TransitionManager.beginDelayedTransition(binding.recyclerView);

        ArrayList<Rates> rateList = new ArrayList<>();

        Rates rates = new Rates(exchangeData.getBase(), 1.0);
        rateList.add(rates);
        for (Map.Entry<String, Double> stringDoubleEntry : exchangeData.getRates().entrySet()) {
            rates = new Rates();
            rates.setName(String.valueOf(((Map.Entry) stringDoubleEntry).getKey()));
            rates.setPrice(stringDoubleEntry.getValue());
            rateList.add(rates);
        }
        exchangeData.setRateList(rateList);
        adapter = new CurrencyAdapter(exchangeData);
        binding.recyclerView.setAdapter(adapter);
        adapter.getonCurrencySelectObservable()
                .takeUntil(getLifecycleEvents(ActivityEvent.STOP))
                .subscribe(this::onNewCurrency, this::handleError);
    }

    private void onNewCurrency(Rates rates) {
        getAppPreferences().setPreferredCurrency(rates.getName());
        fetchAndObserveExchange(rates.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Show the welcome dialog only on first use, ideally controlled by the gateway
        if(!getAppPreferences().getIsFirstTimeUser().get()){
            welcomeFragment = WelcomeFragment.newInstance();
            welcomeFragment.show(getSupportFragmentManager(), WelcomeFragment.TAG);

            welcomeFragment.getLanguageObservable()
                    .takeUntil(getLifecycleEvents(ActivityEvent.DESTROY))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onPreferedLanguage,this::handleError);
            getAppPreferences().setIsFirstTimeUser(true);
        }
    }

    //Set new language if not default
    private void onPreferedLanguage(String preferredLanguage) {
        if(welcomeFragment!=null)
            welcomeFragment.dismiss();
        if(DEFAULT_CURRENCY.equals(preferredLanguage)){
            return;
        }
        getAppPreferences().setPreferredCurrency(preferredLanguage);
        fetchAndObserveExchange(preferredLanguage);
    }


}
