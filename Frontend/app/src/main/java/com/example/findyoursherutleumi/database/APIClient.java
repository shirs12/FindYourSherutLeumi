package com.example.findyoursherutleumi.database;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient extends AppCompatActivity {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:3000/";

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            Gson gson = new Gson();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }



}
