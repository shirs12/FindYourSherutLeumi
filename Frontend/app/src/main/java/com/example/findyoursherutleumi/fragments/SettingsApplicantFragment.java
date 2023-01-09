package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
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

/**
 * This class is the SettingsApplicant fragment,
 * which applicant user type can edit it's own details
 */
public class SettingsApplicantFragment extends Fragment {

    EditText firstNameInput;
    EditText lastNameInput;
    EditText phoneInput;
    EditText emailInput;
    EditText cityInput;
    EditText passwordInput;
    Button updateDetailsBtn;
    Button deleteApplicantBtn;
    APIInterface apiInterface;
    private int applicantId;
    private String password;

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

        fetchData(); // gets user details from db

        updateDetailsBtn = view.findViewById(R.id.update_applicant_edit_details_btn);
        updateDetailsBtn.setOnClickListener(view1 -> {  // update the new details from fields
            if (passwordInput.getText().toString().length() < 6 && passwordInput.getText().toString().length() > 0)
                Toast.makeText(inflater.getContext(), R.string.pass_length_short, Toast.LENGTH_SHORT).show();
            else {
                if (passwordInput.getText().toString().length() > 0)
                    password = passwordInput.getText().toString();
                Call<Applicant> call = apiInterface.updateApplicantById(applicantId,
                        firstNameInput.getText().toString(),
                        lastNameInput.getText().toString(),
                        phoneInput.getText().toString(),
                        cityInput.getText().toString(),
                        emailInput.getText().toString(),
                        password);
                call.enqueue(new Callback<Applicant>() {
                    @Override
                    public void onResponse(@NonNull Call<Applicant> call, @NonNull Response<Applicant> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                        } else {
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

        deleteApplicantBtn.setOnClickListener(view12 -> {
            // alert dialog to make sure the user actually wants to delete the account.
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.delete_user_dialog);
            builder.setCancelable(true);

            builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {   // if the user click 'yes'

                Fragment newFragment = new LoginFragment();
                assert getFragmentManager() != null;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView, newFragment).disallowAddToBackStack();
                transaction.commit();

                // clears fragment's back stack to prevent user from going back after logging out.
                int count = fragmentManager.getBackStackEntryCount();
                for (int index = 0; index < count; ++index) {
                    fragmentManager.popBackStackImmediate();
                }

                Call<ResponseBody> call1 = apiInterface.deleteApplicantById(applicantId);
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(newFragment.getContext(), R.string.user_deleted_toast, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                    }
                });
            });

            builder.setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel()); // if the user click 'no'
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        return view;
    }

    /*
    this method gets the current user data from db
     */
    public void fetchData() {
        assert getArguments() != null;
        Call<Applicant> call1 = apiInterface.getApplicantByEmail(getArguments().getString("email"));
        call1.enqueue(new Callback<Applicant>() {
            @Override
            public void onResponse(@NonNull Call<Applicant> call, @NonNull Response<Applicant> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                } else {
                    assert response.body() != null;
                    password = response.body().getUPassword();
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