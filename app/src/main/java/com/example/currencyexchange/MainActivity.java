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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * MainActivity — View-слой.
 * Здесь мы:
 *  1) Находим View по id (EditText, Button, TextView для статуса, RecyclerView).
 *  2) Инициализируем Repository (Controller/MVC).
 *  3) По клику на кнопку запрашиваем курсы.
 *  4) Получаем LiveData<List<Rate>> и подписываемся на него.
 *  5) Когда данные из БД обновятся, адаптер перерисует список.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText etSymbols;
    private Button btnFetch;
    private TextView tvStatus;
    private RecyclerView rvRates;
    private RateAdapter rateAdapter;

    private Repository repository;  // Controller/MVC

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 4.1) Находим View по id
        etSymbols = findViewById(R.id.etSymbols);
        btnFetch  = findViewById(R.id.btnFetch);
        tvStatus  = findViewById(R.id.tvStatus);
        rvRates   = findViewById(R.id.rvRates);

        // 4.2) Настраиваем RecyclerView (по умолчанию скрыт)
        rvRates.setLayoutManager(new LinearLayoutManager(this));
        rateAdapter = new RateAdapter(new java.util.ArrayList<>());
        rvRates.setAdapter(rateAdapter);
        rvRates.setVisibility(View.GONE); // пока скрываем

        // 4.3) Инициализируем Repository
        repository = new Repository(this);

        // 4.4) Обработчик нажатия кнопки
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String symbolsInput = etSymbols.getText().toString().trim();
                if (TextUtils.isEmpty(symbolsInput)) {
                    Toast.makeText(MainActivity.this, "Введите хотя бы одну валюту", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Обновляем статус
                tvStatus.setText("Запрос курсов: " + symbolsInput + "...");

                // Вызываем репозиторий, который в фоне достанет курс и сохранит его в БД
                // repository.getRatesForSymbols вернёт LiveData<List<Rate>>
                repository.getRatesForSymbols(symbolsInput)
                        .observe(MainActivity.this, new Observer<List<Rate>>() {
                            @Override
                            public void onChanged(@Nullable List<Rate> rates) {
                                if (rates != null && !rates.isEmpty()) {
                                    // Данные пришли из БД (либо они сохранились, либо уже были)
                                    rvRates.setVisibility(View.VISIBLE);
                                    tvStatus.setText("Найдено курсов: " + rates.size());
                                    rateAdapter.updateList(rates);
                                } else {
                                    // Пока нет данных (ещё не успели сохранить или таблица пуста)
                                    tvStatus.setText("Данные пока отсутствуют");
                                    rvRates.setVisibility(View.GONE);
                                    Log.i(TAG, "LiveData с курсами вернул null или пустой список");
                                }
                            }
                        });
            }
        });
    }
}
