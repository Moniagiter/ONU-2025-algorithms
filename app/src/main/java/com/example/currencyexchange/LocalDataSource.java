package com.example.currencyexchange;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalDataSource {

    private final RateDao rateDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LocalDataSource(Context context) {
        RateDatabase db = RateDatabase.getInstance(context);
        rateDao = db.rateDao();
    }

    public void storeRates(final List<Rate> rateList) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                rateDao.deleteAll();
                rateDao.insertRates(rateList);
            }
        });
    }

    public LiveData<List<Rate>> getAllRates() {
        return rateDao.getAllRatesLive();
    }
}
