package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "https://api.openweathermap.org")// базова частина адреси
                .addConverterFactory(GsonConverterFactory. create ())// конвертер
                .build();
        WeatherOneDayApi weatherOneDayApi;
        weatherOneDayApi=retrofit.create(WeatherOneDayApi. class ); // створили об&#39;єкт, за його допомогою будемо відправляти запити
        weatherOneDayApi.getWeatherByCityName(getIntent().getExtras().get("Key").toString(), "3d822b9dce4e57f12b9b3400d480a358").enqueue(new Callback<Example>()
        { //асинхронний виклик (для синхронного був би метод execute() )
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {
                    Log.i ( "Monia", response.body().getWind().getSpeed().toString());
                    Log.i ( "Monia", response.body().getSys().getCountry());
                    Log.i ( "Monia", response.body().getMain().getTemp().toString());
                    Log.i ( "Monia", "OK");
                    //todo: textView
                } else Log.i ("Monia", "no response" );
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.i ( "Jane", "Failure" +t);
            }
        });

    }
}