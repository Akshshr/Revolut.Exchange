package com.revolutexchange.app.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

public class AppPreferences {

    private static final String TAG = AppPreferences.class.getSimpleName();
    private RxSharedPreferences rxSharedPreferences;

    public AppPreferences(final Context context) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
    }


//PREFERED CURRENCY
    public Preference<String> getPreferredCurrency() {
        return rxSharedPreferences.getString(Keys.PREFERED_CURRENCY, "");
    }

    public void setPreferredCurrency(final String prefered_currency) {
        rxSharedPreferences.getString(Keys.PREFERED_CURRENCY).set(prefered_currency);
    }


//IS FIRST TIME USER
    public Preference<Boolean> getIsFirstTimeUser() {
        return rxSharedPreferences.getBoolean(Keys.IS_FIRST_TIME, false);
    }

    public void setIsFirstTimeUser(final Boolean isFirstTimeUse) {
        rxSharedPreferences.getBoolean(Keys.IS_FIRST_TIME).set(isFirstTimeUse);
    }


//KEYS
    private class Keys {
        static final String PREFERED_CURRENCY = "prefered_currency";
        static final String IS_FIRST_TIME = "isFirstTimeUse";
    }


}
