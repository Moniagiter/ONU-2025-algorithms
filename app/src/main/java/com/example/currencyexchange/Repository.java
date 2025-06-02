package com.example.currencyexchange;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private static final String TAG = "Repository";

    private final LocalDataSource localDS;
    private final RemoteDataSource remoteDS;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Repository(Context context) {
        localDS  = new LocalDataSource(context);
        remoteDS = new RemoteDataSource();
    }

    public LiveData<List<Rate>> getRatesForSymbols(final String symbols) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Map<String, Double> ratesMap = remoteDS.fetchLatestRates(symbols);
                if (ratesMap != null) {
                    List<Rate> rateList = new ArrayList<>();
                    String todayDate = LocalDate.now().toString();
                    for (Map.Entry<String, Double> entry : ratesMap.entrySet()) {
                        rateList.add(new Rate(entry.getKey(), entry.getValue(), todayDate));
                    }
                    localDS.storeRates(rateList);
                    Log.i(TAG, "В БД сохранено " + rateList.size() + " записей, дата=" + todayDate);
                } else {
                    Log.e(TAG, "Не удалось получить курсы из сети");
                }
            }
        });
        return localDS.getAllRates();
    }
}
