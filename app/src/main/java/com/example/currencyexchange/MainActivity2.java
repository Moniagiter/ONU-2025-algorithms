package com.example.currencyexchange;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {
    private static final String BASE_URL = "https://data.fixer.io/";
    private TextView textView; // TextView для вывода данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Применяем отступы системы к основному контейнеру
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Получаем ссылку на TextView из activity_main2.xml
        textView = findViewById(R.id.textView);

        // Настраиваем Retrofit с Gson-конвертером
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FixerAPI fixerAPI = retrofit.create(FixerAPI.class);
        Call<FixerResponse> call = fixerAPI.getLatestRates();

        // Выполняем асинхронный запрос и обновляем UI
        call.enqueue(new Callback<FixerResponse>() {
            @Override
            public void onResponse(Call<FixerResponse> call, Response<FixerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FixerResponse fixerResponse = response.body();
                    // Формируем строку для вывода: базовая валюта, дата и курс USD
                    String displayText = "Base: " + fixerResponse.getBase() +
                            "\nDate: " + fixerResponse.getDate() +
                            "\nUSD Rate: " + fixerResponse.getRates().get("USD");
                    textView.setText(displayText);
                } else {
                    textView.setText("Ошибка ответа: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<FixerResponse> call, Throwable t) {
                textView.setText("Запрос не выполнен: " + t.getMessage());
            }
        });
    }
}
