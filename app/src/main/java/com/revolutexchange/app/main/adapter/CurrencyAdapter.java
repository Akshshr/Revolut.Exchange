package com.revolutexchange.app.main.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.revolutexchange.R;
import com.revolutexchange.api.model.ExchangeData;
import com.revolutexchange.api.model.Rates;
import com.revolutexchange.app.main.viewModels.CurrenyAdapterViewModel;
import com.revolutexchange.databinding.RowCurrenciesBinding;

import rx.Observable;
import rx.subjects.PublishSubject;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private final ExchangeData exchangeData;

    private PublishSubject<Rates> onCurrencySelectSubject = PublishSubject.create();
    private CurrenyAdapterViewModel mCurrencyViewModel;

    class ViewHolder extends RecyclerView.ViewHolder {

        RowCurrenciesBinding binding;

        public ViewHolder(RowCurrenciesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        private void bind(Rates rates, int position) {
            mCurrencyViewModel.setCurrencyAdapterData(CurrencyAdapter.this, rates, binding, onCurrencySelectSubject, position);
        }
    }

    public CurrencyAdapter(ExchangeData exchangeData) {
        this.exchangeData = exchangeData;
        mCurrencyViewModel = new CurrenyAdapterViewModel();
    }

    @NonNull
    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        return new CurrencyAdapter.ViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.row_currencies, parent, false));
    }

    @Override
    public void onBindViewHolder(CurrencyAdapter.ViewHolder holder, int position) {
        holder.bind(exchangeData.getRateList().get(position), position);
    }

    public void setCurrencyData(double priceValue) {
        if (exchangeData.getRateList().size() > 0) {
            exchangeData.getRateList().get(0).setPrice(priceValue);
        }
        for (int i = 1; i < exchangeData.getRateList().size(); i++) {
            double price = exchangeData.getRateList().get(i).getPrice();
            Log.d(TAG, "setCurrencyData: " + "" + price + "*" + priceValue);
            int priceValueGet = ((int) priceValue);
            exchangeData.getRateList().get(i).setPrice(price * priceValueGet);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return exchangeData.getRates().size();
    }

    public Observable<Rates> getonCurrencySelectObservable() {
        return onCurrencySelectSubject.asObservable();
    }


}