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

public class ApplicantSignUpFragment extends Fragment {

    private ApplicantSignUpViewModel mViewModel;

    public static ApplicantSignUpFragment newInstance() {
        return new ApplicantSignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_applicant_sign_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ApplicantSignUpViewModel.class);
        // TODO: Use the ViewModel
    }

}