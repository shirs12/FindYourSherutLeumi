package com.example.findyoursherutleumi.fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Coordinator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;

    private int coordinatorId;

    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText phoneInput;
    EditText passwordInput;
    EditText organizationInput;

    Button updateDetailsBtn;

    APIInterface apiInterface;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        firstNameInput = view.findViewById(R.id.c_first_name_input);
        lastNameInput = view.findViewById(R.id.c_last_name_input);
        emailInput = view.findViewById(R.id.c_email_input);
        phoneInput = view.findViewById(R.id.c_phone_input);
        passwordInput = view.findViewById(R.id.c_password_input);
        organizationInput = view.findViewById(R.id.c_organization_input);

        assert getArguments() != null;
        Call<Coordinator> call = apiInterface.getCoordinatorByEmail(getArguments().getString("email"));
        call.enqueue(new Callback<Coordinator>() {
            @Override
            public void onResponse(@NonNull Call<Coordinator> call, @NonNull Response<Coordinator> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                    System.out.println("code1:        " + response.code());
                }else {
                    assert response.body() != null;
                    coordinatorId = response.body().getCoordinatorId();
                    firstNameInput.setText(response.body().getFirstName());
                    lastNameInput.setText(response.body().getLastName());
                    emailInput.setText(response.body().getEmail());
                    phoneInput.setText(response.body().getPhoneNumber());
                    organizationInput.setText(response.body().getOrganizationName());
                }
            }
            @Override
            public void onFailure(Call<Coordinator> call, Throwable t) {
                Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
            }
        });

        updateDetailsBtn = view.findViewById(R.id.update_edit_details_btn);
        updateDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Coordinator> call1 = apiInterface.updateCoordinatorById(coordinatorId,
                        firstNameInput.getText().toString(),
                        lastNameInput.getText().toString(),
                        phoneInput.getText().toString(),
                        emailInput.getText().toString(),
                        passwordInput.getText().toString(),
                        organizationInput.getText().toString());
                call1.enqueue(new Callback<Coordinator>() {
                    @Override
                    public void onResponse(Call<Coordinator> call, Response<Coordinator> response) {
                        if(!response.isSuccessful()) {
                            Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                            System.out.println("code1:        " + response.code());
                        }else {
                            assert response.body() != null;
                            Toast.makeText(getContext(), "Your details updated successfully", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Coordinator> call, Throwable t) {
                        Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        return view;
    }


}