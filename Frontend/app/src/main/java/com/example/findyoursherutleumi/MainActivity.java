package com.example.findyoursherutleumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Applicant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    APIInterface apiInterface;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        button1.setOnClickListener(view -> {
//            Call<Applicant> call = apiInterface.getApplicantById(1);
            Call<List<Applicant>> call = apiInterface.getAllApplicants();
            call.enqueue(new Callback<List<Applicant>>() {
                @Override
                public void onResponse(@NonNull Call<List<Applicant>> call, @NonNull Response<List<Applicant>> response) {
                    if(!response.isSuccessful()) Log.e(TAG, "onResponse: code : " + response.code());
                    if(response.isSuccessful()) {
                        Log.e(TAG, "onResponse: code : " + response.code());
                        assert response.body() != null;
                        Log.e(TAG, "onResponse: " + response.body().get(0));
                    }
                }

                @Override
                public void onFailure(Call<List<Applicant>> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });
        });

    }
}