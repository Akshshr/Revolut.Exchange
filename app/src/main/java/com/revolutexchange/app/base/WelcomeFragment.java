package com.revolutexchange.app.base;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.revolutexchange.R;
import com.revolutexchange.databinding.WelcomeFragmentBinding;

import java.util.HashMap;

import rx.Observable;
import rx.subjects.PublishSubject;

public class WelcomeFragment extends DialogFragment {

    public static final String TAG = WelcomeFragment.class.getSimpleName();

    private PublishSubject<String> onLanguageSelectSubject = PublishSubject.create();
    private static final String INR = "inr";
    private static final String US = "usd";

    private WelcomeFragmentBinding binding;
    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.welcome_fragment, container, false);
            return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        final Window window = dialog.getWindow();
        if (window != null) {
            window.getAttributes().windowAnimations = R.style.AppWidget_DialogTransition;
        }
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(binding.getRoot())
                .load("https://www.countryflags.io/in/flat/64.png")
                .centerCrop()
                .error(R.drawable.ic_error)
                .into(binding.flag1);
        binding.name1.setText(INR);
        binding.row1.setOnClickListener(view -> onLanguageSelectSubject.onNext(INR));

        binding.name2.setText(US);
        Glide.with(binding.getRoot())
                .load("https://www.countryflags.io/us/flat/64.png")
                .centerCrop()
                .error(R.drawable.ic_error)
                .into(binding.flag2);
        binding.row2.setOnClickListener(view -> onLanguageSelectSubject.onNext(US));

    }

    public Observable<String> getLanguageObservable() {
        return onLanguageSelectSubject.asObservable();
    }



}
