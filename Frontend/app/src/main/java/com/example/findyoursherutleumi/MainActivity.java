package com.example.findyoursherutleumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.findyoursherutleumi.adapters.ServicesAdapter;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.fragments.LoginFragment;
import com.example.findyoursherutleumi.models.Applicant;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
//    APIInterface apiInterface;
//    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));






//        button1 = findViewById(R.id.button1);
//        apiInterface = APIClient.getInstance().create(APIInterface.class);
//
//        button1.setOnClickListener(view -> {
//            Call<List<Applicant>> call = apiInterface.getAllApplicants();
//            call.enqueue(new Callback<List<Applicant>>() {
//                @Override
//                public void onResponse(@NonNull Call<List<Applicant>> call, @NonNull Response<List<Applicant>> response) {
//                    if(!response.isSuccessful()) Log.e(TAG, "onResponse: code : " + response.code());
//                    if(response.isSuccessful()) {
//                        Log.e(TAG, "onResponse: code : " + response.code());
//                        assert response.body() != null;
//                        Log.e(TAG, "onResponse: " + response.body().get(1));
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<Applicant>> call, Throwable t) {
//                    Log.e(TAG, "onFailure: " + t.getMessage());
//                }
//            });
//        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        MenuItem languagesItem = menu.findItem(R.id.languages_btn);
        languagesItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                showChangeLanguageDialog();
                return true;
            }
        });
        return true;
    }
    private void showChangeLanguageDialog() {
        final String [] languagesLst = {"עברית", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Language...");
        builder.setSingleChoiceItems(languagesLst, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("iw");
                    recreate();
                }
                else if (i == 1) {
                    setLocale("en");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("My_Lang","");
        setLocale(language);
    }


}