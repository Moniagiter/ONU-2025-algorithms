package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private List<ListItem> forecastList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        forecastAdapter = new ForecastAdapter(forecastList);
        recyclerView.setAdapter(forecastAdapter);

        String city = getIntent().getStringExtra("Key");
        if (city == null) city = "Odessa"; // Значение по умолчанию

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ForecastWeatherApi api = retrofit.create(ForecastWeatherApi.class);
        String apiKey = "3d822b9dce4e57f12b9b3400d480a358";

        Call<ForecastResponse> call = api.getForecast(city, "metric", apiKey);
        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ForecastResponse forecastResponse = response.body();
                    List<ListItem> allItems = forecastResponse.getList();
                    if (allItems == null) {
                        Log.i("Forecast", "Список 'list' пуст");
                        return;
                    }
                    forecastList.clear();
                    for (int i = 0; i < 33 && i < allItems.size(); i++) {
                        forecastList.add(allItems.get(i));
                    }
                    runOnUiThread(() -> forecastAdapter.notifyDataSetChanged());
                } else {
                    Log.i("Forecast", "Ответ не успешен: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                Log.i("Forecast", "Ошибка: " + t.getMessage());
            }
        });
    }
}
