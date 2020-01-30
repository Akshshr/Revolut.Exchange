package com.revolutexchange.app;

import android.app.Activity;
import android.app.Application;

import com.revolutexchange.BuildConfig;
import com.revolutexchange.api.Api;
import com.revolutexchange.app.storage.AppPreferences;

import net.danlew.android.joda.JodaTimeAndroid;

public class RevolutExchangeApplication extends Application {

    public static final String TAG = RevolutExchangeApplication.class.getSimpleName();

    public AppPreferences appPreferences;
    private Api api;
    private Activity mActivity;
    private static RevolutExchangeApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initThirdPartyLibraries();
        this.appPreferences = new AppPreferences(this);
        this.api = new Api(BuildConfig.API_HOST);
    }

    public void setActivityInstance(Activity activityInstance) {
        mActivity = activityInstance;
    }

    public Activity getActivityInstance() {
        return mActivity;
    }

    public static RevolutExchangeApplication getApplicationInstance() {
        return mInstance;
    }

    private void initThirdPartyLibraries() {
        JodaTimeAndroid.init(this);
    }

    public Api getApi() {
        return api;
    }

    public AppPreferences getAppPreferences() {
        return appPreferences;
    }


}
