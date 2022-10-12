package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyoursherutleumi.FragmentToActivity;
import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is the Login fragment,
 * which the user can login to the app by email and password.
 */
public class LoginFragment extends Fragment {

    Button signInBtn;
    EditText inputEmail;
    EditText inputPassword;
    TextView signUpClickable;

    APIInterface apiInterface;

    private FragmentToActivity mCallback;

    // load context from main activity, before the fragment is created
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement FragmentToActivity");
        }
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        setHasOptionsMenu(true);

        inputEmail = view.findViewById(R.id.email_address_input);
        inputPassword = view.findViewById(R.id.password_input);

        signInBtn = view.findViewById(R.id.sign_in_btn);
        signUpClickable = view.findViewById(R.id.sign_up_clickable);

        signInBtn.setOnClickListener(view1 -> {
            if (view1.getId() == R.id.sign_in_btn){
                // checks if the user filled all the fields
                boolean isComplete = isEmpty(inputEmail.getText().toString(), inputPassword.getText().toString());
                if (isComplete) {
                    Call<User> call = apiInterface.authenticateUser(inputEmail.getText().toString(), inputPassword.getText().toString());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            if (!response.isSuccessful()) {
                                if (response.code() == 400) Toast.makeText(getContext(), R.string.email_does_not_exist, Toast.LENGTH_SHORT).show();
                                else if (response.code() == 401) Toast.makeText(getContext(), R.string.email_and_password_does_not_match, Toast.LENGTH_SHORT).show();
                                else Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            } else {
                                mCallback.communicate(response.body()); // sends user to the main activity

                                // all the fields is cleared after login
                                inputEmail.getText().clear();
                                inputPassword.getText().clear();

                                Fragment newFragment = new HomePageFragment();

                                Bundle bundle = new Bundle();
                                assert response.body() != null;

                                bundle.putInt("typeId", response.body().getUserTypeId());
                                bundle.putString("email", response.body().getEmail());
                                newFragment.setArguments(bundle);

                                assert getFragmentManager() != null;
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragmentContainerView, newFragment, "home_page_tag");
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        signUpClickable.setOnClickListener(view12 -> {
            if (view12.getId() == R.id.sign_up_clickable){
                Fragment newFragment = new SignUpUserTypeFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    /*
    this method checks if one of the fields is empty
     */
    private boolean isEmpty(String input1, String input2) {
        if (input1.isEmpty() || input2.isEmpty()) {
            Toast.makeText(getContext(), R.string.fields_not_filled, Toast.LENGTH_SHORT).show();
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