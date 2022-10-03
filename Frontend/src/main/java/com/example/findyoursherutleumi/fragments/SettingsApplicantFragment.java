package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Applicant;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsApplicantFragment extends Fragment {

    private int applicantId;

    EditText firstNameInput;
    EditText lastNameInput;
    EditText phoneInput;
    EditText emailInput;
    EditText cityInput;
    EditText passwordInput;

    Button updateDetailsBtn;
    Button deleteApplicantBtn;

    APIInterface apiInterface;

    public static SettingsApplicantFragment newInstance() {
        return new SettingsApplicantFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_applicant, container, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        firstNameInput = view.findViewById(R.id.a_first_name_input);
        lastNameInput = view.findViewById(R.id.a_last_name_input);
        phoneInput = view.findViewById(R.id.a_phone_input);
        emailInput = view.findViewById(R.id.a_email_input);
        cityInput = view.findViewById(R.id.a_city_input);
        passwordInput = view.findViewById(R.id.a_password_input);

        deleteApplicantBtn = view.findViewById(R.id.a_delete_user_edit_details_btn);

        fetchData();

        updateDetailsBtn = view.findViewById(R.id.update_applicant_edit_details_btn);
        updateDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Applicant> call = apiInterface.updateApplicantById(applicantId,
                        firstNameInput.getText().toString(),
                        lastNameInput.getText().toString(),
                        phoneInput.getText().toString(),
                        cityInput.getText().toString(),
                        emailInput.getText().toString(),
                        passwordInput.getText().toString());
                call.enqueue(new Callback<Applicant>() {
                    @Override
                    public void onResponse(@NonNull Call<Applicant> call, @NonNull Response<Applicant> response) {
                        if(!response.isSuccessful()) {
                            Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            System.out.println("code1:        " + response.code());
                        }else {
                            Toast.makeText(getContext(), R.string.details_updated_toast, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Applicant> call, @NonNull Throwable t) {
                        Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteApplicantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.delete_user_dialog);
                builder.setCancelable(true);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Fragment newFragment = new LoginFragment();
                        assert getFragmentManager() != null;
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentContainerView, newFragment).disallowAddToBackStack();
                        transaction.commit();

                        // clears fragment's back stack to prevent user from going back after logging out.
                        int count = fragmentManager.getBackStackEntryCount();
                        for(int index = 0; index < count; ++index) {
                            fragmentManager.popBackStackImmediate();
                        }

                        Call<ResponseBody> call1 = apiInterface.deleteApplicantById(applicantId);
                        call1.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                if(!response.isSuccessful()) {
                                    Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                                    System.out.println("code1:        " + response.code());
                                }else {
                                    Toast.makeText(newFragment.getContext(), R.string.user_deleted_toast, Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                                Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    public void fetchData(){
        assert getArguments() != null;
        Call<Applicant> call1 = apiInterface.getApplicantByEmail(getArguments().getString("email"));
        call1.enqueue(new Callback<Applicant>() {
            @Override
            public void onResponse(@NonNull Call<Applicant> call, @NonNull Response<Applicant> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                    System.out.println("code1:        " + response.code());
                } else{
                    assert response.body() != null;
                    applicantId = response.body().getApplicantId();
                    firstNameInput.setText(response.body().getFirstName());
                    lastNameInput.setText(response.body().getLastName());
                    phoneInput.setText(response.body().getPhoneNumber());
                    cityInput.setText(response.body().getCity());
                    emailInput.setText(response.body().getEmail());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Applicant> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
            }
        });


    }


}