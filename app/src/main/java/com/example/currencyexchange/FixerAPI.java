package com.example.currencyexchange;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerAPI {
    @GET("api/latest")
    Call<FixerResponse> getLatestRates(
            @Query("access_key") String accessKey,
            @Query("symbols") String symbols
    );
}
