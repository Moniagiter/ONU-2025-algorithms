package com.example.currencyexchange;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//121
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String BASE_URL = "https://data.fixer.io/";
    private static final String ACCESS_KEY = "2a2e0d17281101378897cb51295eceaf";

    private FixerAPI fixerAPI;
    private RateDatabase rateDatabase;

    private EditText etSymbols;
    private Button btnFetch;
    private TextView tvStatus;
    private RecyclerView rvRates;
    private RateAdapter rateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим View по ID
        etSymbols = findViewById(R.id.etSymbols);
        btnFetch   = findViewById(R.id.btnFetch);
        tvStatus   = findViewById(R.id.tvStatus);
        rvRates    = findViewById(R.id.rvRates);

        // Инициализируем Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fixerAPI = retrofit.create(FixerAPI.class);

        // Инициализируем Room
        rateDatabase = RateDatabase.getInstance(getApplicationContext());

        // Настраиваем RecyclerView
        rvRates.setLayoutManager(new LinearLayoutManager(this));
        rateAdapter = new RateAdapter(new ArrayList<>());
        rvRates.setAdapter(rateAdapter);
        rvRates.setVisibility(View.GONE);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symbolsInput = etSymbols.getText().toString().trim();

                // Проверяем, что пользователь действительно ввёл что-то
                if (TextUtils.isEmpty(symbolsInput)) {
                    Toast.makeText(MainActivity.this, "Введите хотя бы одну валюту", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Дальше вызываем метод, передав строку из EditText
                fetchAndSaveRates(symbolsInput);
            }
        });
    }

    /**
     * @param symbols Список валют через запятую, например "USD,EUR,GBP"
     */
    private void fetchAndSaveRates(String symbols) {
        Call<FixerResponse> call = fixerAPI.getLatestRates(ACCESS_KEY, symbols);
        call.enqueue(new Callback<FixerResponse>() {
            @Override
            public void onResponse(Call<FixerResponse> call, Response<FixerResponse> response) {
                if (!response.isSuccessful()) {
                    String errorMsg = "Ошибка HTTP: " + response.code();
                    tvStatus.setText(errorMsg);
                    Log.e(TAG, errorMsg);
                    return;
                }

                FixerResponse fixerResponse = response.body();
                if (fixerResponse == null || !fixerResponse.isSuccess()) {
                    String errorMsg = "Ответ API не успешен или пуст.";
                    tvStatus.setText(errorMsg);
                    Log.e(TAG, errorMsg);
                    return;
                }

                String date = fixerResponse.getDate();
                Map<String, Double> ratesMap = fixerResponse.getRates();

                // Преобразуем Map в List<Rate>
                List<Rate> rateList = new ArrayList<>();
                for (Map.Entry<String, Double> entry : ratesMap.entrySet()) {
                    String code  = entry.getKey();
                    Double value = entry.getValue();
                    rateList.add(new Rate(code, value, date));
                }

                // Сохраняем в БД и затем читаем обратно
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            rateDatabase.rateDao().deleteAll();
                            rateDatabase.rateDao().insertRates(rateList);
                            List<Rate> savedRates = rateDatabase.rateDao().getAllRates();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rvRates.setVisibility(View.VISIBLE);
                                    tvStatus.setText("Сохранено " + savedRates.size() + " записей (дата: " + date + ")");
                                    rateAdapter.updateList(savedRates);
                                }
                            });
                        } catch (Exception e) {
                            Log.e(TAG, "Ошибка при сохранении в БД: " + e.getMessage());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvStatus.setText("Ошибка при сохранении: " + e.getMessage());
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<FixerResponse> call, Throwable t) {
                String errorMsg = "Запрос не удался: " + t.getMessage();
                Log.e(TAG, errorMsg);
                tvStatus.setText(errorMsg);
            }
        });
    }
}
