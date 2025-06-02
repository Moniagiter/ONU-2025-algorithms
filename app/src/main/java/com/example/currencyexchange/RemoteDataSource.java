package com.example.currencyexchange;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {

    private static final String TAG = "RemoteDataSource";
    private static final String BASE_URL   = "https://data.fixer.io/";
    private static final String ACCESS_KEY = "2a2e0d17281101378897cb51295eceaf";

    private final FixerAPI fixerAPI;

    public RemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fixerAPI = retrofit.create(FixerAPI.class);
    }

    public Map<String, Double> fetchLatestRates(String symbols) {
        Call<FixerResponse> call = fixerAPI.getLatestRates(ACCESS_KEY, symbols);
        try {
            Response<FixerResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                return response.body().getRates();
            } else {
                Log.e(TAG, "Ошибка загрузки: HTTP " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.toString());
        }
        return null;
    }
}
