package com.example.findyoursherutleumi.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findyoursherutleumi.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;

    TextView title;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        assert getArguments() != null;

        title = view.findViewById(R.id.settings_title);
        title.setText(getArguments().getString("email"));

        return view;
    }


}