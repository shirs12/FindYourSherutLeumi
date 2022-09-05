package com.example.findyoursherutleumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Applicant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(view -> {
            APIInterface apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
            Call<Applicant> call = apiInterface.getApplicantById(1);
            call.enqueue(new Callback<Applicant>() {
                @Override
                public void onResponse(@NonNull Call<Applicant> call, @NonNull Response<Applicant> response) {
                    if(!response.isSuccessful()) Log.e(TAG, "onResponse: code : " + response.code());
                    if(response.isSuccessful()) {
                        Log.e(TAG, "onResponse: code : " + response.code());
                        assert response.body() != null;
                        Log.e(TAG, "onResponse: " + response.body());
                    }
                }

                @Override
                public void onFailure(Call<Applicant> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });
        });

    }
}