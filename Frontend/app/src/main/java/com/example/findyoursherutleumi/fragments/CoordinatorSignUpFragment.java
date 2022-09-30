package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Coordinator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoordinatorSignUpFragment extends Fragment {

    APIInterface apiInterface;

    EditText firstNameInput;
    EditText lastNameInput;
    EditText phoneNumberInput;
    EditText emailInput;
    EditText passwordInput;
    EditText organizationInput;

    Button submitBtn;

    public static CoordinatorSignUpFragment newInstance() {
        return new CoordinatorSignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coordinator_sign_up, container, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        setHasOptionsMenu(true);

        firstNameInput = view.findViewById(R.id.first_name_input);
        lastNameInput = view.findViewById(R.id.last_name_input);
        phoneNumberInput = view.findViewById(R.id.phone_number_input);
        emailInput = view.findViewById(R.id.email_address_input);
        passwordInput = view.findViewById(R.id.password_input);
        organizationInput = view.findViewById(R.id.organization_input);

        submitBtn = view.findViewById(R.id.sign_in_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.sign_in_btn) {
                    boolean isComplete = isEmpty(firstNameInput.getText().toString(),
                            lastNameInput.getText().toString(),
                            phoneNumberInput.getText().toString(),
                            emailInput.getText().toString(),
                            passwordInput.getText().toString(),
                            organizationInput.getText().toString());
                    if (isComplete) {
                        Call<Coordinator> call = apiInterface.createNewCoordinator(firstNameInput.getText().toString(),
                                lastNameInput.getText().toString(), phoneNumberInput.getText().toString(),
                                emailInput.getText().toString(), passwordInput.getText().toString(),
                                organizationInput.getText().toString());
                        call.enqueue(new Callback<Coordinator>() {
                            @Override
                            public void onResponse(@NonNull Call<Coordinator> call, @NonNull Response<Coordinator> response) {
                                if(!response.isSuccessful()) {
                                    Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                                } else{
                                    firstNameInput.getText().clear();
                                    lastNameInput.getText().clear();
                                    phoneNumberInput.getText().clear();
                                    emailInput.getText().clear();
                                    passwordInput.getText().clear();
                                    organizationInput.getText().clear();

                                    Fragment newFragment = new LoginFragment();
                                    assert getFragmentManager() != null;
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.fragmentContainerView, newFragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Coordinator> call, @NonNull Throwable t) {
                                Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        return view;
    }

    private boolean isEmpty(String input1, String input2, String input3,
                            String email, String pass, String input5) {
        if (input1.isEmpty() || input2.isEmpty() || input3.isEmpty()
                || email.isEmpty() || input5.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getContext(), R.string.fields_not_filled, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (pass.length() < 6){
            Toast.makeText(getContext(), R.string.pass_length_short, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getContext(), R.string.email_address_not_valid, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.more_item);
        if(item != null)
            item.setVisible(false);
    }


}