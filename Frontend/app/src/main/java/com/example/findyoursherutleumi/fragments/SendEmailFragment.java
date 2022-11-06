package com.example.findyoursherutleumi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findyoursherutleumi.R;

public class SendEmailFragment extends Fragment {

    public static SendEmailFragment newInstance() {
        return new SendEmailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_email, container, false);


        return view;
    }


}