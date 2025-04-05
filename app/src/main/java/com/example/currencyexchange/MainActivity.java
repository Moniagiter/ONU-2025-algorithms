package com.example.currencyexchange;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editTextCurrency;
    private Button buttonShowResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCurrency = findViewById(R.id.editTextCurrency);
        buttonShowResult = findViewById(R.id.buttonShowResult);

        buttonShowResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currency = editTextCurrency.getText().toString().trim().toUpperCase();
                if (!currency.isEmpty()) {
                    // Передаём введённый код валюты во вторую Activity
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("currency", currency);
                    startActivity(intent);
                }
            }
        });
    }
}
