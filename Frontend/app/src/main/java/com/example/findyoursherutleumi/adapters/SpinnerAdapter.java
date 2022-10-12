package com.example.findyoursherutleumi.adapters;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.findyoursherutleumi.fragments.SignUpUserTypeFragment;

/**
 * This class is for the spinner at 'SignUpUserTypeFragment',
 * where the user can choose the user type for signing up.
 */
public class SpinnerAdapter implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        SignUpUserTypeFragment.chose = pos;
            Toast.makeText(parent.getContext(),
                    "You chose : " + parent.getItemAtPosition(pos).toString() + " sign up",
                    Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}
