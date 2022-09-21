package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Coordinator;
import com.example.findyoursherutleumi.models.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewServiceFragment extends Fragment {

    APIInterface apiInterface;

    private String userEmail;

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
        apartmentRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onRadioButtonClicked1(view);
            }
        });
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
        addServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.add_new_service_btn){
                    System.out.println("userEmail:    " + userEmail);
                    Call<Coordinator> call = apiInterface.getCoordinatorByEmail(userEmail);
                    call.enqueue(new Callback<Coordinator>() {
                        @Override
                        public void onResponse(Call<Coordinator> call, Response<Coordinator> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                                System.out.println("code1:        " + response.code());
                            }else {
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
                                    @Override
                                    public void onResponse(@NonNull Call<Service> call, @NonNull Response<Service> response) {
                                        if(!response.isSuccessful()) {
                                            Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                                            System.out.println("code2:        " + response.code());
                                        } else {
                                            serviceNameInput.getText().clear();
                                            organizationInput.getText().clear();
                                            categoryInput.getText().clear();
                                            countryInput.getText().clear();
                                            cityInput.getText().clear();
                                            addressInput.getText().clear();
                                            descriptionInput.getText().clear();

//                                            apartmentRadio.clearCheck();
//                                            secondYearRadio.clearCheck();
//                                            morningRadio.clearCheck();
//                                            eveningRadio.clearCheck();

                                            System.out.println(response.code());
                                            Toast.makeText(getContext(), "New service added successfully", Toast.LENGTH_SHORT).show();

                                            //TODO: optional - trans to homepage fragment
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Service> call, Throwable t) {
                                        Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                                        System.out.println("failllllllll1");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Coordinator> call, Throwable t) {
                            Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                            System.out.println("failllllllll2");
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

}

