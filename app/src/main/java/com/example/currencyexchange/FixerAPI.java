package com.example.currencyexchange;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FixerAPI {
    @GET("api/latest?access_key=2a2e0d17281101378897cb51295eceaf&symbols=USD,GBP")
    Call<FixerResponse> getLatestRates();
}
