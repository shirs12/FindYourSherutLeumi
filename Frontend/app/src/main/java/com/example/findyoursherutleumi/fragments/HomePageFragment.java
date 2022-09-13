package com.example.findyoursherutleumi.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.adapters.ServicesAdapter;
import com.example.findyoursherutleumi.models.ServicePartial;

import java.util.List;

public class HomePageFragment extends Fragment {

    private HomePageViewModel mViewModel;
    RecyclerView recyclerView;
    ServicesAdapter servicesAdapter;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        recyclerView = view.findViewById(R.id.services_lst);
        mViewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        return view;
    }

    private void initRecyclerView() {
        servicesAdapter = new ServicesAdapter(getLayoutInflater(), mViewModel.getServices().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(servicesAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.init();
        mViewModel.getServices().observe(this.getViewLifecycleOwner(), new Observer<List<ServicePartial>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<ServicePartial> servicePartials) {
                servicesAdapter.notifyDataSetChanged();
            }
        });
        initRecyclerView();
    }


}