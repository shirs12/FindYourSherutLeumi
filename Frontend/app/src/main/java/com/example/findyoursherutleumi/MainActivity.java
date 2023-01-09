package com.example.findyoursherutleumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.findyoursherutleumi.fragments.LoginFragment;
import com.example.findyoursherutleumi.fragments.SettingsApplicantFragment;
import com.example.findyoursherutleumi.fragments.SettingsFragment;
import com.example.findyoursherutleumi.models.User;

import java.util.Locale;

/**
 * This class is the main activity,
 * which all the fragments is shown
 */
public class MainActivity extends AppCompatActivity implements FragmentToActivity {

    private static User user;

    // get user from activity to homepage fragment
    public static User getUser() {
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();   // load default language
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // sets the app name as the title
            actionBar.setTitle(getResources().getString(R.string.app_name));

            // sets user object details from saved instance state - in case the activity is restarting
            if (user == null && savedInstanceState != null) {
                user = new User();
                user.setEmail(savedInstanceState.getString("email"));
                user.setUserTypeId(savedInstanceState.getInt("typeId"));
                user.setUPassword("");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // saves bundles after configuration changes
        super.onSaveInstanceState(outState);
        if (user != null) {
            outState.putString("email", user.getEmail());
            outState.putInt("typeId", user.getUserTypeId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // create menu
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        MenuItem languagesItem = menu.findItem(R.id.languages_btn);
        MenuItem logoutItem = menu.findItem(R.id.logout_item);
        MenuItem settingsItem = menu.findItem(R.id.settings_item);

        languagesItem.setOnMenuItemClickListener(menuItem -> {
            showChangeLanguageDialog();
            return true;
        });

        // logout dialog
        logoutItem.setOnMenuItemClickListener(menuItem -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.logout_dialog);
            builder.setCancelable(true);

            builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                assert getFragmentManager() != null;
                FragmentManager fragmentManager = getSupportFragmentManager();

                // clears fragment's back stack to prevent user from going back after logging out.
                int count = fragmentManager.getBackStackEntryCount();
                for (int index = 0; index < count; ++index) {
                    fragmentManager.popBackStackImmediate();
                }
                Fragment newFragment = new LoginFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView, newFragment).disallowAddToBackStack();
                transaction.commit();

            });
            builder.setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        });

        // user settings dialog
        settingsItem.setOnMenuItemClickListener(menuItem -> {
            Fragment newFragment;
            if (user.getUserTypeId() == 2) {
                newFragment = new SettingsFragment();
            } else {
                newFragment = new SettingsApplicantFragment();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();

            Bundle bundle = new Bundle();
            bundle.putString("email", user.getEmail());
            bundle.putInt("typeId", user.getUserTypeId());
            newFragment.setArguments(bundle);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainerView, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        });
        return true;
    }

    // change language dialog
    private void showChangeLanguageDialog() {
        final String[] languagesLst = {"עברית", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.choose_language);
        builder.setSingleChoiceItems(languagesLst, -1, (dialogInterface, i) -> {
            if (i == 0) {
                setLocale("iw");
                recreate();
            } else if (i == 1) {
                setLocale("en");
                recreate();
            }
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // sets the chosen language
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    // loads the language
    public void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
    }

    /* communicate with fragments, to get user details from login fragment,
    by the FragmentToActivity interface
     */
    @Override
    public void communicate(User user) {

        this.user = user;
    }

}
