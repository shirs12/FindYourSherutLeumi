package com.example.findyoursherutleumi.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findyoursherutleumi.R;

public class CoordinatorSignUpFragment extends Fragment {

    private CoordinatorSignUpViewModel mViewModel;

    public static CoordinatorSignUpFragment newInstance() {
        return new CoordinatorSignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coordinator_sign_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CoordinatorSignUpViewModel.class);
        // TODO: Use the ViewModel
    }

}