package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            EditText v1Input = findViewById(R.id.v1_input);
            EditText t1Input = findViewById(R.id.t1_input);
            EditText v2Input = findViewById(R.id.v2_input);
            EditText t2Input = findViewById(R.id.t2_input);

            double v1 = parseDoubleOrDefault(v1Input.getText().toString(), 0.0);
            double t1 = parseDoubleOrDefault(t1Input.getText().toString(), 0.0);
            double v2 = parseDoubleOrDefault(v2Input.getText().toString(), 0.0);
            double t2 = parseDoubleOrDefault(t2Input.getText().toString(), 0.0);

            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("v1", v1);
            intent.putExtra("t1", t1);
            intent.putExtra("v2", v2);
            intent.putExtra("t2", t2);
            startActivity(intent);
        });
    }

    private double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Override
    protected void onPause() {
        Log.i("Monia 1", "Pause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i("Monia 1", "Destroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i("Monia 1", "Restart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.i("Monia 1", "Start");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i("Monia 1", "Stop");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.i("Monia 1", "Resume");
        super.onResume();
    }
}