package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.adapters.EditServicesAdapter;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Coordinator;
import com.example.findyoursherutleumi.models.Service;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is the SettingsApplicant fragment,
 * which coordinator user type can edit it's own details
 */
public class SettingsFragment extends Fragment {

    EditServicesAdapter editServicesAdapter;
    List<Service> cServicesLst = new ArrayList<>();
    RecyclerView recyclerView;
    TextView noItems;
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText phoneInput;
    EditText passwordInput;
    EditText organizationInput;
    Button updateDetailsBtn;
    Button deleteCoordinatorBtn;
    APIInterface apiInterface;
    private int coordinatorId;
    private String password;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }


    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);
        noItems = view.findViewById(R.id.no_items_text);

        recyclerView = view.findViewById(R.id.coordinator_services_list_edit);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        editServicesAdapter = new EditServicesAdapter(inflater, cServicesLst);
        recyclerView.setAdapter(editServicesAdapter);

        firstNameInput = view.findViewById(R.id.c_first_name_input);
        lastNameInput = view.findViewById(R.id.c_last_name_input);
        emailInput = view.findViewById(R.id.c_email_input);
        phoneInput = view.findViewById(R.id.c_phone_input);
        passwordInput = view.findViewById(R.id.c_password_input);
        organizationInput = view.findViewById(R.id.c_organization_input);

        deleteCoordinatorBtn = view.findViewById(R.id.c_delete_user_edit_details_btn);

        fetchData(); // gets user details from db

        updateDetailsBtn = view.findViewById(R.id.update_edit_details_btn);
        updateDetailsBtn.setOnClickListener(view12 -> {
            if (passwordInput.getText().toString().length() < 6 && passwordInput.getText().toString().length() > 0)
                Toast.makeText(inflater.getContext(), R.string.pass_length_short, Toast.LENGTH_SHORT).show();
            else {
                if (passwordInput.getText().toString().length() > 0)
                    password = passwordInput.getText().toString();
                Call<Coordinator> call1 = apiInterface.updateCoordinatorById(coordinatorId,
                        firstNameInput.getText().toString(),
                        lastNameInput.getText().toString(),
                        phoneInput.getText().toString(),
                        emailInput.getText().toString(),
                        password,
                        organizationInput.getText().toString());
                call1.enqueue(new Callback<Coordinator>() {
                    @Override
                    public void onResponse(@NonNull Call<Coordinator> call, @NonNull Response<Coordinator> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            System.out.println("code1:        " + response.code());
                        } else {
                            assert response.body() != null;
                            Toast.makeText(getContext(), R.string.details_updated_toast, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Coordinator> call, @NonNull Throwable t) {
                        Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteCoordinatorBtn.setOnClickListener(view1 -> {
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

                // TODO: delete successfully but by login with another account it gets an error
                Call<ResponseBody> call1 = apiInterface.deleteServicesByCoordinatorId(coordinatorId);
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(newFragment.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            System.out.println("code1:        " + response.code());
                        } else {
                            Call<ResponseBody> call2 = apiInterface.deleteCoordinatorById(coordinatorId);
                            call2.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                    if (!response.isSuccessful()) {
                                        Toast.makeText(newFragment.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                                        System.out.println("code2:        " + response.code());
                                    } else {
                                        Toast.makeText(newFragment.getContext(), R.string.user_deleted_toast, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                                    Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                                }
                            });
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

        editServicesAdapter.notifyDataSetChanged();
        return view;
    }

    /*
    this method gets the current user data from db
     */
    public void fetchData() {
        assert getArguments() != null;
        Call<Coordinator> call = apiInterface.getCoordinatorByEmail(getArguments().getString("email"));
        call.enqueue(new Callback<Coordinator>() {
            @Override
            public void onResponse(@NonNull Call<Coordinator> call, @NonNull Response<Coordinator> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                } else {
                    assert response.body() != null;
                    password = response.body().getUPassword();
                    coordinatorId = response.body().getCoordinatorId();
                    firstNameInput.setText(response.body().getFirstName());
                    lastNameInput.setText(response.body().getLastName());
                    emailInput.setText(response.body().getEmail());
                    phoneInput.setText(response.body().getPhoneNumber());
                    organizationInput.setText(response.body().getOrganizationName());

                    Call<List<Service>> call1 = apiInterface.getServicesByCoordinatorId(coordinatorId);
                    call1.enqueue(new Callback<List<Service>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(@NonNull Call<List<Service>> call, @NonNull Response<List<Service>> response) {
                            if (!response.isSuccessful())
                                Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            else {
                                assert response.body() != null;
                                cServicesLst.addAll(response.body());
                                editServicesAdapter.notifyDataSetChanged();

                                if (editServicesAdapter.getItemCount() == 0)
                                    noItems.setVisibility(View.VISIBLE);
                                else noItems.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<Service>> call, @NonNull Throwable t) {
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