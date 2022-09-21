package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.adapters.ServicesAdapter;
import com.example.findyoursherutleumi.models.ServicePartial;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class HomePageFragment extends Fragment {

    private int userTypeId;
    private String userEmail;

    private HomePageViewModel mViewModel;
    RecyclerView recyclerView;
    ServicesAdapter servicesAdapter;
    FloatingActionButton addServiceBtn;
    private EditText searchBar;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        mViewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        setHasOptionsMenu(true);
        addServiceBtn = view.findViewById(R.id.add_service_btn);

        assert getArguments() != null;
        userEmail = getArguments().getString("email");
        userTypeId = getArguments().getInt("typeId");
        System.out.println(userTypeId);
        if (userTypeId == 1) {
            addServiceBtn.setVisibility(View.GONE);
        }

        mViewModel.init();

        recyclerView = view.findViewById(R.id.services_lst);
        mViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<ServicePartial>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<ServicePartial> servicePartials) {
                servicesAdapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();

        addServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new AddNewServiceFragment();
                Bundle bundle = new Bundle();
                bundle.putString("email", userEmail);
                newFragment.setArguments(bundle);

                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        searchBar = view.findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                servicesAdapter.notifyDataSetChanged();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                servicesAdapter.filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        servicesAdapter.notifyDataSetChanged();
        return view;
    }

    private void initRecyclerView() {
        servicesAdapter = new ServicesAdapter(getLayoutInflater(), mViewModel.getServices().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(servicesAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter() {
        servicesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
    }


    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu,inflater);
//        inflater.inflate(R.menu.nav_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                servicesAdapter.getFilter().filter(s);
//                return false;
//            }
//        });
//
//    }
}