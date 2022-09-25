package com.example.findyoursherutleumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.findyoursherutleumi.adapters.ServicesAdapter;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.fragments.AddNewServiceFragment;
import com.example.findyoursherutleumi.fragments.LoginFragment;
import com.example.findyoursherutleumi.fragments.SettingsApplicantFragment;
import com.example.findyoursherutleumi.fragments.SettingsFragment;
import com.example.findyoursherutleumi.models.Applicant;
import com.example.findyoursherutleumi.models.User;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FragmentToActivity {

    private static final String TAG = "MainActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.app_name));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        MenuItem languagesItem = menu.findItem(R.id.languages_btn);
        MenuItem logoutItem = menu.findItem(R.id.logout_item);
        MenuItem settingsItem = menu.findItem(R.id.settings_item);

        languagesItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                showChangeLanguageDialog();
                return true;
            }
        });

        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.logout_dialog);
                builder.setCancelable(true);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fragment newFragment = new LoginFragment();
                        assert getFragmentManager() != null;
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentContainerView, newFragment).disallowAddToBackStack();
                        transaction.commit();

                        // clears fragment's back stack to prevent user from going back after logging out.
                        int count = fragmentManager.getBackStackEntryCount();
                        for(int index = 0; index < count; ++index) {
                            fragmentManager.popBackStackImmediate();
                        }
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;
            }
        });

        settingsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Fragment newFragment;
                if (user.getUserTypeId() == 2){
                    newFragment = new SettingsFragment();
                }
                else {
                    newFragment = new SettingsApplicantFragment();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putString("email", user.getEmail());
                bundle.putInt("typeId", user.getUserTypeId());
                bundle.putString("password", user.getUPassword());
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

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

    @Override
    public void communicate(User user) {
        this.user = user;
    }


}