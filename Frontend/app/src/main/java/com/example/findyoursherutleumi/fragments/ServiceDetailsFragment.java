package com.example.findyoursherutleumi.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Coordinator;
import com.example.findyoursherutleumi.models.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is the ServiceDetails fragment,
 * where all service details is shown.
 */
public class ServiceDetailsFragment extends Fragment {

    private int serviceId;
    private Coordinator serviceCoordinator = null;

    TextView serviceTxt;
    TextView organizationTxt;
    TextView categoryTxt;
    TextView countryTxt;
    TextView cityTxt;
    TextView addressTxt;
    TextView hasApartmentTxt;
    TextView isSecondYearOnlyTxt;
    TextView isMorningServiceTxt;
    TextView isEveningServiceTxt;
    TextView descriptionTxt;
    TextView coordinatorNameTxt;
    TextView coordinatorContactTxt;

    Button sendEmailBtn;

    APIInterface apiInterface;

    public static ServiceDetailsFragment newInstance() {
        return new ServiceDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_details, container, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        serviceTxt = view.findViewById(R.id.service_details_title);
        organizationTxt = view.findViewById(R.id.organization_txt_details);
        categoryTxt = view.findViewById(R.id.category_txt);
        countryTxt = view.findViewById(R.id.country_txt);
        cityTxt = view.findViewById(R.id.city_txt);
        addressTxt = view.findViewById(R.id.address_txt);
        hasApartmentTxt = view.findViewById(R.id.has_apartment_txt);
        isSecondYearOnlyTxt = view.findViewById(R.id.is_second_year_only_txt);
        isMorningServiceTxt = view.findViewById(R.id.is_morning_service_txt);
        isEveningServiceTxt = view.findViewById(R.id.is_evening_service_txt);
        descriptionTxt = view.findViewById(R.id.description_service_txt);
        coordinatorNameTxt = view.findViewById(R.id.coordinator_name_txt);
        coordinatorContactTxt = view.findViewById(R.id.coordinator_contact_txt);

        sendEmailBtn = view.findViewById(R.id.send_email_nav_btn);

        // arguments from ServicesAdapter
        if (getArguments() != null)
            serviceId = getArguments().getInt("id");

        Call<Service> call = apiInterface.getServiceById(serviceId);
        call.enqueue(new Callback<Service>() {
            @Override
            public void onResponse(@NonNull Call<Service> call, @NonNull Response<Service> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                else {
                    // sets service details from db
                    assert response.body() != null;
                    serviceTxt.setText(response.body().getServiceName());
                    organizationTxt.setText(response.body().getOrganizationName());
                    categoryTxt.setText(response.body().getCategory());
                    countryTxt.setText(response.body().getCountry());
                    cityTxt.setText(response.body().getCity());
                    addressTxt.setText(response.body().getAddress());
                    hasApartmentTxt.setText(response.body().getHasApartment() ? R.string.yes : R.string.no);
                    isSecondYearOnlyTxt.setText(response.body().getIsSecondYearOnly() ? R.string.yes : R.string.no);
                    isMorningServiceTxt.setText(response.body().getIsMorningService() ? R.string.yes : R.string.no);
                    isEveningServiceTxt.setText(response.body().getIsEveningService() ? R.string.yes : R.string.no);
                    descriptionTxt.setText(response.body().getDescriptionService());

                    Call<Coordinator> call1 = apiInterface.getCoordinatorById(response.body().getCoordinatorId());
                    call1.enqueue(new Callback<Coordinator>() {
                        @SuppressLint("SetTextI18n")  // -> Internationalization
                        @Override
                        public void onResponse(@NonNull Call<Coordinator> call, @NonNull Response<Coordinator> response) {
                            if (!response.isSuccessful()){
                                Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.body() != null) {
                                    serviceCoordinator = response.body();
                                    coordinatorNameTxt.setText(response.body().getFirstName() + " " + response.body().getLastName());  // annotation
                                    coordinatorContactTxt.setText(response.body().getPhoneNumber());
                                }
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<Coordinator> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(@NonNull Call<Service> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
            }
        });

        // goes to the 'SendEmailFragment' fragment with arguments
        sendEmailBtn.setOnClickListener(view1 -> {
            Fragment newFragment = new SendEmailFragment();
            if (serviceCoordinator != null){
                Bundle bundle = new Bundle();
                bundle.putString("coordinator_email", serviceCoordinator.getEmail());
                bundle.putString("service_title", serviceTxt.getText().toString());
                newFragment.setArguments(bundle);
            }
            assert getFragmentManager() != null;
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // if coordinator phone number the dial options will appear
        coordinatorContactTxt.setOnClickListener(view12 -> {
            dialCoordinator();
        });

        return view;
    }

    // opens dial options
    private void dialCoordinator() {
        if (coordinatorContactTxt.getText().length() != 0) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + coordinatorContactTxt.getText()));
            startActivity(intent);
        }
    }


}