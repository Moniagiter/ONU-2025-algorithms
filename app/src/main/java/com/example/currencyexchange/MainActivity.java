package com.example.currencyexchange; // або ваш пакет

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import com.example.currencyexchange.FixerAPI;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "FixerDemo";
    private static final String BASE_URL = "https://data.fixer.io/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        FixerAPI fixerAPI = retrofit.create(FixerAPI.class);
        Call<String> call = fixerAPI.getLatestRates();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "Fixer response: " + response.body());
                } else {
                    Log.e(TAG, "Response error: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Request failed: " + t.getMessage());
            }
        });
    }
}
