package com.example.findyoursherutleumi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.adapters.SpinnerAdapter;

/**
 * This is the SignUpUserType fragment,
 * which the user chooses applicant/coordinator signing up.
 */
public class SignUpUserTypeFragment extends Fragment {

    Button nextBtn;
    public static int chose;

    public static SignUpUserTypeFragment newInstance() {
        return new SignUpUserTypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_user_type, container, false);

        setHasOptionsMenu(true);

        // user type options for sign up
        Spinner spinner = view.findViewById(R.id.user_type_spinner);
        spinner.setOnItemSelectedListener(new SpinnerAdapter());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.user_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        nextBtn = view.findViewById(R.id.sign_up_next_btn);
        nextBtn.setOnClickListener(view1 -> {
            if (view1.getId() == R.id.sign_up_next_btn){
                Fragment newFragment = null;
                // applicant
                if (chose == 0) newFragment = new ApplicantSignUpFragment();
                // coordinator
                else if (chose == 1) newFragment = new CoordinatorSignUpFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (newFragment != null) {
                    transaction.replace(R.id.fragmentContainerView, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.more_item);
        if(item!=null)
            item.setVisible(false);
    }



}