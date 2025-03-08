package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

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

        // Отримуємо дані з Intent
        double v1 = getIntent().getDoubleExtra("v1", 0.0);
        double t1 = getIntent().getDoubleExtra("t1", 0.0);
        double v2 = getIntent().getDoubleExtra("v2", 0.0);
        double t2 = getIntent().getDoubleExtra("t2", 0.0);

        // Обчислюємо результат
        double totalVolume = v1 + v2;
        double totalTemperature = (v1 * t1 + v2 * t2) / (v1 + v2);

        // Відображаємо результат
        TextView resultText = findViewById(R.id.result_text);
        String result = String.format("Об'єм суміші: %.2f л\nТемпература суміші: %.2f °C", totalVolume, totalTemperature);
        resultText.setText(result);

        // Кнопка для повернення
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });
    }
}