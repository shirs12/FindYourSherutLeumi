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
import com.example.findyoursherutleumi.models.Applicant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is the ApplicantSignUp fragment,
 * which user from type - applicant can sign up to the app.
 */
public class ApplicantSignUpFragment extends Fragment {

    APIInterface apiInterface;

    EditText firstNameInput;
    EditText lastNameInput;
    EditText phoneNumberInput;
    EditText cityInput;
    EditText emailInput;
    EditText passwordInput;

    Button submitBtn;

    public static ApplicantSignUpFragment newInstance() {
        return new ApplicantSignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applicant_sign_up, container, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        setHasOptionsMenu(true);

        firstNameInput = view.findViewById(R.id.first_name_input);
        lastNameInput = view.findViewById(R.id.last_name_input);
        phoneNumberInput = view.findViewById(R.id.phone_number_input);
        cityInput = view.findViewById(R.id.city_input);
        emailInput = view.findViewById(R.id.email_address_input);
        passwordInput = view.findViewById(R.id.password_input);

        submitBtn = view.findViewById(R.id.sign_in_btn);

        submitBtn.setOnClickListener(view1 -> {
            if (view1.getId() == R.id.sign_in_btn) {
                // checks if the user filled all the fields
                boolean isComplete = isEmpty(firstNameInput.getText().toString(),
                        lastNameInput.getText().toString(),
                        phoneNumberInput.getText().toString(),
                        cityInput.getText().toString(),
                        emailInput.getText().toString(),
                        passwordInput.getText().toString());
                if (isComplete) {
                    Call<Applicant> call = apiInterface.createNewApplicant(firstNameInput.getText().toString(),
                            lastNameInput.getText().toString(), phoneNumberInput.getText().toString(),
                            cityInput.getText().toString(), emailInput.getText().toString(),
                            passwordInput.getText().toString());
                    call.enqueue(new Callback<Applicant>() {
                        @Override
                        public void onResponse(@NonNull Call<Applicant> call, @NonNull Response<Applicant> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            } else{
                                // all the fields is cleared after submission
                                firstNameInput.getText().clear();
                                lastNameInput.getText().clear();
                                phoneNumberInput.getText().clear();
                                cityInput.getText().clear();
                                emailInput.getText().clear();
                                passwordInput.getText().clear();

                                // transact back to the login screen
                                Fragment newFragment = new LoginFragment();
                                assert getFragmentManager() != null;
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragmentContainerView, newFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Applicant> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }

    /*
    this method checks if one of the fields is empty,
    or if some fields not filled correctly
    before sign up the user.
     */
    private boolean isEmpty(String input1, String input2, String input3,
                         String input4, String email, String pass) {
        if (input1.isEmpty() || input2.isEmpty() || input3.isEmpty()
                || input4.isEmpty() || email.isEmpty() || pass.isEmpty()) {
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