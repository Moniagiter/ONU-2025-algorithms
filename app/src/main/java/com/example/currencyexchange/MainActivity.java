package com.example.currencyexchange;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "FixerDemo";
    private static final String BASE_URL = "https://data.fixer.io/";

    private TextView textView;  // TextView для вывода данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView); // Находим TextView по ID

        // Настраиваем Retrofit с GSON-конвертером (чтобы работать с POJO)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Создаём экземпляр API
        FixerAPI fixerAPI = retrofit.create(FixerAPI.class);

        // Выполняем запрос
        Call<FixerResponse> call = fixerAPI.getLatestRates();
        call.enqueue(new Callback<FixerResponse>() {
            @Override
            public void onResponse(Call<FixerResponse> call, Response<FixerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FixerResponse fixerResponse = response.body();

                    // Выводим в лог (как было раньше)
                    Log.i(TAG, "Base: " + fixerResponse.getBase());
                    Log.i(TAG, "Date: " + fixerResponse.getDate());
                    Log.i(TAG, "USD Rate: " + fixerResponse.getRates().get("USD"));

                    // А также выводим эти же данные в TextView
                    String resultText = "Base: " + fixerResponse.getBase()
                            + "\nDate: " + fixerResponse.getDate()
                            + "\nUSD Rate: " + fixerResponse.getRates().get("USD");
                    textView.setText(resultText);

                } else {
                    Log.e(TAG, "Response error: " + response.errorBody());
                    textView.setText("Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<FixerResponse> call, Throwable t) {
                Log.e(TAG, "Request failed: " + t.getMessage());
                textView.setText("Request failed: " + t.getMessage());
            }
        });
    }
}
