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
    private static final String ACCESS_KEY = "2a2e0d17281101378897cb51295eceaf";
    private TextView textView; // для вывода результата

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

        textView = findViewById(R.id.textView);

        // Получаем код валюты из Intent
        String currencyFromIntent = getIntent().getStringExtra("currency");
        if (currencyFromIntent == null || currencyFromIntent.isEmpty()) {
            currencyFromIntent = "USD";
        }

        // Объявляем финальную переменную (либо больше не меняем currencyFromIntent)
        final String currency = currencyFromIntent;

        // Настраиваем Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FixerAPI fixerAPI = retrofit.create(FixerAPI.class);

        // Делаем запрос
        Call<FixerResponse> call = fixerAPI.getLatestRates(ACCESS_KEY, currency);
        call.enqueue(new Callback<FixerResponse>() {
            @Override
            public void onResponse(Call<FixerResponse> call, Response<FixerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FixerResponse fixerResponse = response.body();
                    String resultText = "Базовая валюта: " + fixerResponse.getBase() +
                            "\nДата: " + fixerResponse.getDate() +
                            "\nКурс " + currency + ": " + fixerResponse.getRates().get(currency);
                    textView.setText(resultText);
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
