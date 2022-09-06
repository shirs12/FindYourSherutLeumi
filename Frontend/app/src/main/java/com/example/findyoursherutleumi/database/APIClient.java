package com.example.findyoursherutleumi.database;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient extends AppCompatActivity {

    private static Retrofit instance;
    private static final String BASE_URL = "http://10.0.2.2:3000/";

    public static Retrofit getInstance(){
        if (instance == null){
            Gson gson = new GsonBuilder().setLenient().create();
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return instance;
    }



}
