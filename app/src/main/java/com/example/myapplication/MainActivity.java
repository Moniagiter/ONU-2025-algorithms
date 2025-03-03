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
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            intent.putExtra("key1","Hello");
            EditText edit1=findViewById(R.id.edit1);
            intent.putExtra("key2",edit1.getText());
            startActivity(intent);
        });
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