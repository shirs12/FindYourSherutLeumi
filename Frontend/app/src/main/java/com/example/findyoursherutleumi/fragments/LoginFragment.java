package com.example.findyoursherutleumi.fragments;

import androidx.fragment.app.FragmentTransaction;

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
import com.example.findyoursherutleumi.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    Button signInBtn;
    EditText inputEmail;
    EditText inputPassword;
    TextView signUpClickable;

    APIInterface apiInterface;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        inputEmail = view.findViewById(R.id.email_address_input);
        inputPassword = view.findViewById(R.id.password_input);

        signInBtn = view.findViewById(R.id.sign_in_btn);
        signUpClickable = view.findViewById(R.id.sign_up_clickable);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.sign_in_btn){
                    JSONObject paramObject = new JSONObject();
                    try {
                        paramObject.put("email", inputEmail.toString());
                        paramObject.put("password", inputPassword.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Call<User> call = apiInterface.authenticateUser(paramObject.toString());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(!response.isSuccessful()) {
                                System.out.println("1");
                                System.out.println(response.body());
                                System.out.println(paramObject);
                                Toast.makeText(getContext(), "Connection Failed. try again later...", Toast.LENGTH_SHORT).show();
                            }else {
                                Fragment newFragment = new HomePageFragment();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragmentContainerView, newFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            System.out.println("2");
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


}