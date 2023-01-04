package com.example.findyoursherutleumi.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.fragments.HomePageFragment;
import com.example.findyoursherutleumi.fragments.HomePageViewModel;
import com.example.findyoursherutleumi.models.Service;
import com.example.findyoursherutleumi.models.ServiceEdit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is the adapter for the service's list in the 'SettingsFragment'.
 */
public class EditServicesAdapter extends RecyclerView.Adapter<EditServicesAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    int id;
    APIInterface apiInterface;
    private List<Service> servicesLst;

    public EditServicesAdapter(LayoutInflater inflater, List<Service> servicesLst) {
        this.inflater = inflater;
        this.servicesLst = servicesLst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_coordinator, parent, false);
        apiInterface = APIClient.getInstance().create(APIInterface.class);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.serviceNameTxt.setText(servicesLst.get(position).getServiceName());
        holder.cityTxt.setText(servicesLst.get(position).getCity());

        holder.deleteBtn.setOnClickListener(view -> {
            if (view.getId() == holder.deleteBtn.findViewById(R.id.delete_service_edit_btn).getId()) {
                id = servicesLst.get(holder.getAdapterPosition()).getServiceId();

                // alert dialog to make sure the user actually wants to delete the current item
                final AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
                builder.setMessage(R.string.delete_service_dialog);
                builder.setCancelable(true);
                builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {   // if the user click 'yes'
                    Call<ResponseBody> call = apiInterface.deleteServiceById(id);
                    call.enqueue(new Callback<ResponseBody>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            } else {
                                servicesLst.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(),servicesLst.size()-1);
                                Toast.makeText(inflater.getContext(), R.string.item_deleted_toast, Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                builder.setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel());   // if the user click 'no'
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        /**
         * edit service dialog - fields fill with all the current details,
         * which the user can change.
         */
        holder.editBtn.setOnClickListener(view1 -> {
            if (view1.getId() == holder.editBtn.findViewById(R.id.edit_service_list_btn).getId()) {
                id = servicesLst.get(holder.getAdapterPosition()).getServiceId();

                final Dialog dialog = new Dialog(inflater.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_edit_service);

                final EditText name = dialog.findViewById(R.id.edit_service_name);
                final EditText organization = dialog.findViewById(R.id.edit_service_organization);
                final EditText category = dialog.findViewById(R.id.edit_service_category);
                final EditText country = dialog.findViewById(R.id.edit_service_country);
                final EditText city = dialog.findViewById(R.id.edit_service_city);
                final EditText address = dialog.findViewById(R.id.edit_service_address);
                final EditText description = dialog.findViewById(R.id.edit_service_description);

                Button submitButton = dialog.findViewById(R.id.submit_button);
                FloatingActionButton close = dialog.findViewById(R.id.edit_service_close_btn);

                Call<Service> call = apiInterface.getServiceById(id);
                call.enqueue(new Callback<Service>() {
                    @Override
                    public void onResponse(@NonNull Call<Service> call, @NonNull Response<Service> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                        } else {
                            assert response.body() != null;
                            name.setText(response.body().getServiceName());
                            organization.setText(response.body().getOrganizationName());
                            category.setText(response.body().getCategory());
                            country.setText(response.body().getCountry());
                            city.setText(response.body().getCity());
                            address.setText(response.body().getAddress());
                            description.setText(response.body().getDescriptionService());

                            RadioGroup apartmentRadio = dialog.findViewById(R.id.edit_apartment_radio);
                            if (response.body().getHasApartment()) apartmentRadio.check(dialog.findViewById(R.id.edit_has_apartment_yes).getId());
                            else apartmentRadio.check(dialog.findViewById(R.id.edit_has_apartment_no).getId());

                            RadioGroup secondYearRadio = dialog.findViewById(R.id.edit_second_year_radio);
                            if (response.body().getIsSecondYearOnly()) secondYearRadio.check(dialog.findViewById(R.id.edit_second_year_yes).getId());
                            else secondYearRadio.check(dialog.findViewById(R.id.edit_second_year_no).getId());

                            RadioGroup morningRadio = dialog.findViewById(R.id.edit_is_morning_radio);
                            if (response.body().getIsMorningService()) morningRadio.check(dialog.findViewById(R.id.edit_morning_service_yes).getId());
                            else morningRadio.check(dialog.findViewById(R.id.edit_morning_service_no).getId());

                            RadioGroup eveningRadio = dialog.findViewById(R.id.edit_is_evening_radio);
                            if (response.body().getIsEveningService()) eveningRadio.check(dialog.findViewById(R.id.edit_evening_service_yes).getId());
                            else eveningRadio.check(dialog.findViewById(R.id.edit_evening_service_no).getId());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<Service> call, @NonNull Throwable t) {
                        Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                    }
                });

                submitButton.setOnClickListener(v -> {

                    RadioGroup apartmentRadio = dialog.findViewById(R.id.edit_apartment_radio);
                    RadioButton apartmentBtn = dialog.findViewById(apartmentRadio.getCheckedRadioButtonId());
                    RadioGroup secondYearRadio = dialog.findViewById(R.id.edit_second_year_radio);
                    RadioButton secondYearBtn = dialog.findViewById(secondYearRadio.getCheckedRadioButtonId());
                    RadioGroup morningRadio = dialog.findViewById(R.id.edit_is_morning_radio);
                    RadioButton morningBtn = dialog.findViewById(morningRadio.getCheckedRadioButtonId());
                    RadioGroup eveningRadio = dialog.findViewById(R.id.edit_is_evening_radio);
                    RadioButton eveningBtn = dialog.findViewById(eveningRadio.getCheckedRadioButtonId());

                    boolean isComplete = isEmpty(name.getText().toString(),
                            organization.getText().toString(),
                            category.getText().toString(),
                            country.getText().toString(),
                            city.getText().toString(),
                            address.getText().toString(),
                            description.getText().toString()
                    );
                    if (isComplete) {
                        Call<ServiceEdit> call1 = apiInterface.updateServiceById(id,
                                name.getText().toString(),
                                organization.getText().toString(),
                                category.getText().toString(),
                                country.getText().toString(),
                                city.getText().toString(),
                                address.getText().toString(),
                                (apartmentBtn.getId() == dialog.findViewById(R.id.edit_has_apartment_yes).getId() ? 1 : 0),
                                (secondYearBtn.getId() == dialog.findViewById(R.id.edit_second_year_yes).getId() ? 1 : 0),
                                (morningBtn.getId() == dialog.findViewById(R.id.edit_morning_service_yes).getId() ? 1 : 0),
                                (eveningBtn.getId() == dialog.findViewById(R.id.edit_evening_service_yes).getId() ? 1 : 0),
                                description.getText().toString());
                        call1.enqueue(new Callback<ServiceEdit>() {
                            @Override
                            public void onResponse(@NonNull Call<ServiceEdit> call, @NonNull Response<ServiceEdit> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                                } else {
                                    assert response.body() != null;
                                    Toast.makeText(inflater.getContext(), R.string.details_updated_toast, Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call<ServiceEdit> call, @NonNull Throwable t) {
                                Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                            }
                        });
                    dialog.dismiss();
                    }
                });

                close.setOnClickListener(view2 -> dialog.dismiss());

                dialog.show();
            }
        });
    }

    /*
    this method checks if one of the fields is empty,
    before updating a service.
     */
    private boolean isEmpty(String input1, String input2, String input3,
                            String input4, String input5, String input6,
                            String input7) {
        if (input1.isEmpty() || input2.isEmpty() || input3.isEmpty()
                || input4.isEmpty() || input5.isEmpty() || input6.isEmpty() || input7.isEmpty()) {
            Toast.makeText(inflater.getContext(), R.string.fields_not_filled, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public int getItemCount() {
        return servicesLst.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateServicesList(final List<Service> mServicesLst) {
        this.servicesLst = mServicesLst;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        FloatingActionButton editBtn;
        FloatingActionButton deleteBtn;
        TextView serviceNameTxt;
        TextView cityTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editBtn = itemView.findViewById(R.id.edit_service_list_btn);
            deleteBtn = itemView.findViewById(R.id.delete_service_edit_btn);
            serviceNameTxt = itemView.findViewById(R.id.service_title_lst_edit);
            cityTxt = itemView.findViewById(R.id.service_city_lst_edit);
        }

    }
}
