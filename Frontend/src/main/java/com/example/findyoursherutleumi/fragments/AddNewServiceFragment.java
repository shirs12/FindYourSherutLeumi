package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.findyoursherutleumi.MainActivity;
import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Coordinator;
import com.example.findyoursherutleumi.models.Service;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewServiceFragment extends Fragment {

    APIInterface apiInterface;
    Button addServiceBtn;
    EditText serviceNameInput;
    EditText organizationInput;
    EditText categoryInput;
    EditText countryInput;
    EditText cityInput;
    EditText addressInput;
    EditText descriptionInput;
    RadioGroup apartmentRadio;
    RadioButton apartmentBtn;
    RadioGroup secondYearRadio;
    RadioButton secondYearBtn;
    RadioGroup morningRadio;
    RadioButton morningBtn;
    RadioGroup eveningRadio;
    RadioButton eveningBtn;
    private String userEmail;
    private int hasApartment = 0;
    private int isSecondYear = 0;
    private int isMorning = 0;
    private int isEvening = 0;


    public static AddNewServiceFragment newInstance() {
        return new AddNewServiceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new, container, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        serviceNameInput = view.findViewById(R.id.service_input_add);
        organizationInput = view.findViewById(R.id.organization_input_add);
        categoryInput = view.findViewById(R.id.category_input_add);
        countryInput = view.findViewById(R.id.country_input_add);
        cityInput = view.findViewById(R.id.city_input_add);
        addressInput = view.findViewById(R.id.address_input_add);
        descriptionInput = view.findViewById(R.id.description_input_add);

        apartmentRadio = view.findViewById(R.id.apartment_radio);
        apartmentRadio.setOnCheckedChangeListener((radioGroup, i) -> onRadioButtonClicked1(view));
        secondYearRadio = view.findViewById(R.id.second_year_radio);
        secondYearRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onRadioButtonClicked2(view);
            }
        });
        morningRadio = view.findViewById(R.id.is_morning_radio);
        morningRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onRadioButtonClicked3(view);
            }
        });
        eveningRadio = view.findViewById(R.id.is_evening_radio);
        eveningRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onRadioButtonClicked4(view);
            }
        });

        assert getArguments() != null;
        userEmail = getArguments().getString("email");

        addServiceBtn = view.findViewById(R.id.add_new_service_btn);
        addServiceBtn.setOnClickListener(view1 -> {
            if (view1.getId() == R.id.add_new_service_btn) {
                boolean isComplete = isEmpty(serviceNameInput.getText().toString(),
                        organizationInput.getText().toString(),
                        categoryInput.getText().toString(),
                        countryInput.getText().toString(),
                        cityInput.getText().toString(),
                        addressInput.getText().toString(),
                        descriptionInput.getText().toString()
                );
                if (isComplete) {
                    Call<Coordinator> call = apiInterface.getCoordinatorByEmail(userEmail);
                    call.enqueue(new Callback<Coordinator>() {
                        @Override
                        public void onResponse(@NonNull Call<Coordinator> call, @NonNull Response<Coordinator> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            } else {
                                assert response.body() != null;
                                Call<Service> call2 = apiInterface.createNewService(
                                        serviceNameInput.getText().toString(),
                                        organizationInput.getText().toString(),
                                        categoryInput.getText().toString(),
                                        countryInput.getText().toString(),
                                        cityInput.getText().toString(),
                                        addressInput.getText().toString(),
                                        hasApartment, isSecondYear, isMorning, isEvening,
                                        descriptionInput.getText().toString(),
                                        response.body().getFirstName() + " " + response.body().getLastName(),
                                        response.body().getCoordinatorId());

                                call2.enqueue(new Callback<Service>() {
                                    @SuppressLint("NotifyDataSetChanged")
                                    @Override
                                    public void onResponse(@NonNull Call<Service> call, @NonNull Response<Service> response) {
                                        if (!response.isSuccessful()) {
                                            Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                                        } else {
                                            serviceNameInput.getText().clear();
                                            organizationInput.getText().clear();
                                            categoryInput.getText().clear();
                                            countryInput.getText().clear();
                                            cityInput.getText().clear();
                                            addressInput.getText().clear();
                                            descriptionInput.getText().clear();

                                            assert getFragmentManager() != null;
                                            Fragment newFragment = new HomePageFragment();
                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                            transaction.replace(R.id.fragmentContainerView, newFragment)
                                                    .addToBackStack(null)
                                                    .commit();

                                            Toast.makeText(getContext(), R.string.new_service_added, Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<Service> call, @NonNull Throwable t) {
                                        Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }


                        @Override
                        public void onFailure(@NonNull Call<Coordinator> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }

    public void onRadioButtonClicked1(View view) {
        int radioId = apartmentRadio.getCheckedRadioButtonId();
        apartmentBtn = view.findViewById(radioId);
        if (apartmentBtn.getId() == R.id.has_apartment_yes_radio)
            hasApartment = 1;
    }

    public void onRadioButtonClicked2(View view) {
        int radioId = secondYearRadio.getCheckedRadioButtonId();
        secondYearBtn = view.findViewById(radioId);
        if (secondYearBtn.getId() == R.id.second_year_yes_radio)
            isSecondYear = 1;
    }

    public void onRadioButtonClicked3(View view) {
        int radioId = morningRadio.getCheckedRadioButtonId();
        morningBtn = view.findViewById(radioId);
        if (morningBtn.getId() == R.id.morning_service_yes_radio)
            isMorning = 1;
    }

    public void onRadioButtonClicked4(View view) {
        int radioId = eveningRadio.getCheckedRadioButtonId();
        eveningBtn = view.findViewById(radioId);
        if (eveningBtn.getId() == R.id.evening_service_yes_radio)
            isEvening = 1;
    }

    private boolean isEmpty(String input1, String input2, String input3,
                            String input4, String input5, String input6,
                            String input7) {
        if (input1.isEmpty() || input2.isEmpty() || input3.isEmpty()
                || input4.isEmpty() || input5.isEmpty() || input6.isEmpty() || input7.isEmpty()) {
            Toast.makeText(getContext(), R.string.fields_not_filled, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}

