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

public class AddNewServiceFragment extends Fragment {

    private AddNewViewModel mViewModel;

    public static AddNewServiceFragment newInstance() {
        return new AddNewServiceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new, container, false);
    }
    // TODO: on click to add a new service to the app (db).


}