package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
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
import com.example.findyoursherutleumi.MainActivity;
import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Coordinator;
import com.example.findyoursherutleumi.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    Button signInBtn;
    EditText inputEmail;
    EditText inputPassword;
    TextView signUpClickable;

    APIInterface apiInterface;

    private FragmentToActivity mCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
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


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.sign_in_btn){
                    Call<User> call = apiInterface.authenticateUser(inputEmail.getText().toString(), inputPassword.getText().toString());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(!response.isSuccessful()) {
                                Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                            }else {
                                mCallback.communicate(response.body());

                                inputEmail.getText().clear();
                                inputPassword.getText().clear();

                                Fragment newFragment = new HomePageFragment();

                                Bundle bundle = new Bundle();
                                assert response.body() != null;

                                bundle.putInt("typeId", response.body().getUserTypeId());
                                bundle.putString("email", response.body().getEmail());
                                newFragment.setArguments(bundle);

                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragmentContainerView, newFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        signUpClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.sign_up_clickable){
                    Fragment newFragment = new SignUpUserTypeFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragmentContainerView, newFragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.more_item);
        if(item!=null)
            item.setVisible(false);
    }


}