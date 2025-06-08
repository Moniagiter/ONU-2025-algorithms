package com.example.currencyexchange;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements RateAdapter.OnRateClickListener {

    private EditText etSymbols;
    private Button btnFetch;
    private TextView tvStatus;
    private RecyclerView rvRates;
    private RateAdapter rateAdapter;
    private RateViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Android сам выберет файл из layout/ или layout-land/ (или layout-land/)
        setContentView(R.layout.activity_main);

        // 1) Находим View по id
        etSymbols = findViewById(R.id.etSymbols);
        btnFetch  = findViewById(R.id.btnFetch);
        tvStatus  = findViewById(R.id.tvStatus);
        rvRates   = findViewById(R.id.rvRates);

        // 2) Настраиваем RecyclerView
        rvRates.setLayoutManager(new LinearLayoutManager(this));
        rateAdapter = new RateAdapter(new ArrayList<>(), this);
        rvRates.setAdapter(rateAdapter);
        rvRates.setVisibility(RecyclerView.GONE);

        // 3) Берём ViewModel
        viewModel = new ViewModelProvider(this).get(RateViewModel.class);

        // 4) Подписываемся на LiveData один раз
        viewModel.getRatesLiveData().observe(this, rates -> {
            if (rates != null && !rates.isEmpty()) {
                rvRates.setVisibility(RecyclerView.VISIBLE);
                tvStatus.setText("Найдено курсов: " + rates.size());
                rateAdapter.updateList(rates);
            } else {
                rvRates.setVisibility(RecyclerView.GONE);
                tvStatus.setText("Дані поки відсутні");
            }
        });

        // 5) По клику кнопки запускаем загрузку
        btnFetch.setOnClickListener(v -> {
            String symbols = etSymbols.getText().toString().trim();
            if (TextUtils.isEmpty(symbols)) {
                Toast.makeText(
                        MainActivity.this,
                        "Введіть хоча б одну валюту",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
            tvStatus.setText("Запит курсу: " + symbols + "...");
            viewModel.fetchRates(symbols);
        });
    }

    @Override
    public void onRateClick(Rate rate) {
        if (findViewById(R.id.detail_container) != null) {
            RateDetailFragment frag = RateDetailFragment.newInstance(
                    rate.getCurrencyCode(),
                    rate.getRateValue(),
                    rate.getDate()
            );
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_container, frag)
                    .commit();
        } else {
            Toast.makeText(
                    this,
                    rate.getCurrencyCode() + ": " + rate.getRateValue(),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
