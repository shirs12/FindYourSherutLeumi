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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.adapters.SpinnerAdapter;

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

        Spinner spinner = (Spinner) view.findViewById(R.id.user_type_spinner);
        spinner.setOnItemSelectedListener(new SpinnerAdapter());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.user_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        nextBtn = view.findViewById(R.id.sign_up_next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.sign_up_next_btn){
                    Fragment newFragment = null;
                    if (chose == 0) newFragment = new ApplicantSignUpFragment();
                    else if (chose == 1) newFragment = new CoordinatorSignUpFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
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