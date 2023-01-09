package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.findyoursherutleumi.MainActivity;
import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.adapters.ServicesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This class is the HomePage fragment,
 * the main screen of the app, contains the list of all services.
 */
public class HomePageFragment extends Fragment {

    // arguments from login
    private int userTypeId;
    private String userEmail;

    HomePageViewModel mViewModel;
    RecyclerView recyclerView;
    ServicesAdapter servicesAdapter;
    FloatingActionButton addServiceBtn;
    EditText searchBar;
    TextView searchNoResult;
    SwipeRefreshLayout swipeRefreshLayout;


    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        mViewModel.init();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        setHasOptionsMenu(true);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_list);
        addServiceBtn = view.findViewById(R.id.add_service_btn);
        searchNoResult = view.findViewById(R.id.no_result_text);

        if (getArguments() != null) {
            userEmail = getArguments().getString("email");
            userTypeId = getArguments().getInt("typeId");
        }else{
            userEmail = MainActivity.getUser().getEmail();
            userTypeId = MainActivity.getUser().getUserTypeId();
        }
        if (userTypeId == 1) {  // if the user type id is 1, the user cannot add new service to the app
            addServiceBtn.setVisibility(View.GONE);
        }

        recyclerView = view.findViewById(R.id.services_lst);
        mViewModel.getServices().observe(getViewLifecycleOwner(), servicePartials -> {
            servicesAdapter.updateServicesList(servicePartials);
            servicesAdapter.notifyDataSetChanged();
        });

        initRecyclerView();

        // refresh recyclerview
        swipeRefreshLayout.setOnRefreshListener(() -> {
            refreshData();
            swipeRefreshLayout.setRefreshing(false);
        });

        addServiceBtn.setOnClickListener(view1 -> {
            Fragment newFragment = new AddNewServiceFragment();
            Bundle bundle = new Bundle();
            bundle.putString("email", userEmail);
            newFragment.setArguments(bundle);

            assert getFragmentManager() != null;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        searchBar = view.findViewById(R.id.search_bar);
        // filters items from recyclerview while user is searching by search bar
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                servicesAdapter.filter(charSequence);
                if (servicesAdapter.getItemCount() == 0)
                    searchNoResult.setVisibility(View.VISIBLE);
                else searchNoResult.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setHandler();
        servicesAdapter.notifyDataSetChanged();
        return view;
    }

    /*
    refresh the data every second, unless search bar has text
     */
    public void setHandler(){
        final Handler handler = new Handler();
        final int delay = 1000;  // 1000 milliseconds = 1 second

        handler.postDelayed(new Runnable(){
            public void run(){
                if (searchBar.getText().length() == 0) refreshData();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }


    private void refreshData() {
        mViewModel.loadServices();
        servicesAdapter.notifyItemRangeChanged(0,recyclerView.getItemDecorationCount());
        updateAdapter();
    }

    // initializing the recyclerview
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

    /* when the fragment is restarting, the viewmodel gets all services from db,
    and updating the range of the adapter.
     */
    @Override
    public void onStart() {
        super.onStart();
        mViewModel.loadServices();
        servicesAdapter.notifyItemRangeChanged(0,recyclerView.getItemDecorationCount());
        updateAdapter();
        searchBar.getText().clear();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.loadServices();
        servicesAdapter.notifyItemRangeChanged(0,recyclerView.getItemDecorationCount());
        updateAdapter();
        searchBar.getText().clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        updateAdapter();
    }

    @Override
    public void onStop() {
        super.onStop();
        updateAdapter();
    }



}
