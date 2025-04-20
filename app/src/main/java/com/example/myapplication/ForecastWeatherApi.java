package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastWeatherApi {
    @GET("/data/2.5/forecast")
    Call<ForecastResponse> getForecast(
            @Query("q") String city,        // название города
            @Query("units") String units,   // "metric" для Цельсия
            @Query("appid") String apiKey   // ваш API ключ
    );
}
