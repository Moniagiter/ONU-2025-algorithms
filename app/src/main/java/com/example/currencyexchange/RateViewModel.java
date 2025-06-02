package com.example.currencyexchange;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;
public class RateViewModel extends AndroidViewModel {
    private final Repository repository;
    private final MediatorLiveData<List<Rate>> ratesLiveData = new MediatorLiveData<>();
    private LiveData<List<Rate>> currentSource;
    public RateViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        ratesLiveData.setValue(new ArrayList<>());
    }
    public void fetchRates(String symbols) {
        LiveData<List<Rate>> newSource = repository.getRatesForSymbols(symbols);
        if (currentSource != null) {
            ratesLiveData.removeSource(currentSource);
        }

        currentSource = newSource;
        ratesLiveData.addSource(currentSource, ratesLiveData::setValue);
    }
    public LiveData<List<Rate>> getRatesLiveData() {
        return ratesLiveData;
    }
}
