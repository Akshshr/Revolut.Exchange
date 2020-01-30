package com.revolutexchange.app.main.viewModels;

import android.content.Context;
import android.icu.util.Currency;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.bumptech.glide.Glide;
import com.revolutexchange.R;
import com.revolutexchange.api.model.Rates;
import com.revolutexchange.app.main.adapter.CurrencyAdapter;
import com.revolutexchange.databinding.RowCurrenciesBinding;

import java.text.DecimalFormat;

import rx.subjects.PublishSubject;

public class CurrenyAdapterViewModel extends BaseObservable {

    private static final String FLAG_URL = "https://www.countryflags.io/%1$s/flat/64.png";
    private static final String PATTERN_2DECIMAL_ROUND = "##.00";

    public void setCurrencyAdapterData(CurrencyAdapter context, Rates rates,
                                       RowCurrenciesBinding binding,
                                       PublishSubject<Rates> onCurrencySelectSubject,
                                       int position) {

        binding.setCurrenyviewmodel(this);
        binding.currencyName.setText(rates.getName());

        DecimalFormat df = new DecimalFormat(PATTERN_2DECIMAL_ROUND);
        binding.currencyInput.setText(String.valueOf(df.format(rates.getPrice())));

        String ambitiousKey = rates.getName().substring(0,2);

        Glide.with(binding.getRoot())
                .load(String.format(FLAG_URL, ambitiousKey))
                .centerCrop()
                .error(R.drawable.ic_error)
                .into(binding.currencyFlag);

        binding.currencyFullName.setText(Currency.getInstance(rates.getName()).getDisplayName());
        binding.currencySymbol.setText(Currency.getInstance(rates.getName()).getSymbol());

        binding.rowParent.setOnClickListener(view -> onCurrencySelectSubject.onNext(rates));
        if (position == 0) {
            binding.currencyInput.setEnabled(true);
            binding.editable.setVisibility(View.VISIBLE);
            binding.currencyInput.setSelection(String.valueOf(rates.getPrice()).length());
            binding.currencyInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (position == 0 && binding.currencyInput.hasFocus()) {
                        binding.currencyInput.removeTextChangedListener(this);
                        context.setCurrencyData(setDataForFirstRom(charSequence.toString(), binding));
                        binding.currencyInput.addTextChangedListener(this);
//                        binding.currencyInput.clearFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else{
            binding.currencyInput.setEnabled(false);
        }
    }

    private Double setDataForFirstRom(String charData, RowCurrenciesBinding binding) {
        Double number = Double.parseDouble(charData);
        binding.currencyInput.setText(String.valueOf(number));
        return number;
    }

}
