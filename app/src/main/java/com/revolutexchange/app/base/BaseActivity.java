package com.revolutexchange.app.base;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.revolutexchange.R;
import com.revolutexchange.api.Api;
import com.revolutexchange.app.RevolutExchangeApplication;
import com.revolutexchange.app.storage.AppPreferences;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    public enum ActivityEvent {
        CREATE,
        DESTROY,
        START,
        STOP,
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        RevolutExchangeApplication.getApplicationInstance().setActivityInstance(this);
    }

    public Api getApi() {
        return ((RevolutExchangeApplication) getApplication()).getApi();
    }

    public AppPreferences getAppPreferences() {
        return ((RevolutExchangeApplication) getApplication()).getAppPreferences();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }

    @Keep
    public void handleError(Throwable throwable) {
        Log.w(TAG, "An error occurred and handled by generating an error message", throwable);
        showSnackBar();
    }

    public Observable<ActivityEvent> getLifecycleEvents(ActivityEvent event) {
        return lifecycleSubject.filter(new Func1<ActivityEvent, Boolean>() {
            @Override
            public Boolean call(ActivityEvent activityEvent) {
                return activityEvent == event;
            }
        });
    }

    public void showSnackBar() {
        View contextView = findViewById(android.R.id.content);
        final Snackbar snackBar = Snackbar.make(contextView, getResources().getText(R.string.error_generic), Snackbar.LENGTH_INDEFINITE);

        snackBar.setAction(this.getResources().getString(R.string.button_dismiss), v -> {
        });
        snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.red300));
        snackBar.show();
    }


}
