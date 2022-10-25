package com.example.findyoursherutleumi.database;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findyoursherutleumi.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is the connection between the server to the client
 */
public class APIClient extends AppCompatActivity {

    private static Retrofit instance;
    // TODO: switch BASE_URL if needed
//    private static final String BASE_URL = "http://10.0.2.2:3000/";   // emulator
//    private static final String BASE_URL = "http://192.168.1.108:3000/";  // home
    private static final String BASE_URL = "http://10.9.2.167:3000/";   // au

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

    public static User getUserService(){
        User userService = getInstance().create(User.class);

        return userService;
    }



}
