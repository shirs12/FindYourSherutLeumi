package com.example.findyoursherutleumi.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.findyoursherutleumi.R;

public class SignUpUserTypeFragment extends Fragment {

    private SignUpUserTypeViewModel mViewModel;

    public static SignUpUserTypeFragment newInstance() {
        return new SignUpUserTypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_user_type, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.user_type_spinner);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignUpUserTypeViewModel.class);
        // TODO: Use the ViewModel
    }

//
//    // Create an ArrayAdapter using the string array and a default spinner layout
//    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//            R.array.user_types_array, android.R.layout.simple_spinner_item);
//    // Specify the layout to use when the list of choices appears
//    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    // Apply the adapter to the spinner
//    spinner.setAdapter(adapter);



}