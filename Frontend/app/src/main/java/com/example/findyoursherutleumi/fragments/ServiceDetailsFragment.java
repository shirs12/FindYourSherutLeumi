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

public class ServiceDetailsFragment extends Fragment {

    private ServiceDetailsViewModel mViewModel;
    private int serviceId;
    TextView serviceIdTitle;

    public static ServiceDetailsFragment newInstance() {
        return new ServiceDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_details, container, false);
        serviceIdTitle = view.findViewById(R.id.service_id_title);

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            int myInt = bundle.getInt("serviceId", serviceId);
//        }
//        serviceIdTitle.setText(serviceId);
        return view;
    }


}