package com.example.findyoursherutleumi.database;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient extends AppCompatActivity {

    private static final String SERVER = "http://10.0.2.2:3000/";

    private static Retrofit retrofit;
    private static String BASE_URL = "http://10.0.2.2:3000/";

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



}
