package com.example.currencyexchange;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText etSymbols;
    private Button btnFetch;
    private TextView tvStatus;
    private RecyclerView rvRates;
    private RateAdapter rateAdapter;

    // ViewModel-инстанс
    private RateViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1) Устанавливаем layout
        setContentView(R.layout.activity_main);

        // 2) Находим View-поля
        etSymbols = findViewById(R.id.etSymbols);
        btnFetch  = findViewById(R.id.btnFetch);
        tvStatus  = findViewById(R.id.tvStatus);
        rvRates   = findViewById(R.id.rvRates);

        // 3) Настраиваем RecyclerView (менеджер + пустой адаптер)
        rvRates.setLayoutManager(new LinearLayoutManager(this));
        rateAdapter = new RateAdapter(new java.util.ArrayList<>());
        rvRates.setAdapter(rateAdapter);
        rvRates.setVisibility(View.GONE);

        // 4) Получаем ViewModel через ViewModelProvider
        viewModel = new ViewModelProvider(this).get(RateViewModel.class);

        // 5) Подписываемся на LiveData<List<Rate>> из ViewModel
        //    Заметьте: до вызова fetchRates() liveData может быть null,
        //    поэтому подписываемся с проверкой null внутри onChanged.
        viewModel.getRatesLiveData().observe(this, new Observer<List<Rate>>() {
            @Override
            public void onChanged(@Nullable List<Rate> rates) {
                if (rates != null && !rates.isEmpty()) {
                    rvRates.setVisibility(View.VISIBLE);
                    tvStatus.setText("Найдено курсов: " + rates.size());
                    rateAdapter.updateList(rates);
                } else {
                    tvStatus.setText("Дані поки відсутні");
                    rvRates.setVisibility(View.GONE);
                    Log.i(TAG, "LiveData<List<Rate>> пустий або null");
                }
            }
        });

        // 6) Устанавливаем OnClickListener на кнопку
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String symbolsInput = etSymbols.getText().toString().trim();
                if (TextUtils.isEmpty(symbolsInput)) {
                    Toast.makeText(MainActivity.this, "Введіть хоча б одну валюту", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Обновляем статус
                tvStatus.setText("Запит курсу: " + symbolsInput + "...");

                // Просим ViewModel начать загрузку и сохранение курсу
                viewModel.fetchRates(symbolsInput);
            }
        });
    }
}
