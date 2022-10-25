package com.example.findyoursherutleumi.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Service;
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

        FloatingActionButton deleteBtn;
        TextView serviceNameTxt;
        TextView cityTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = itemView.findViewById(R.id.delete_service_edit_btn);
            serviceNameTxt = itemView.findViewById(R.id.service_title_lst_edit);
            cityTxt = itemView.findViewById(R.id.service_city_lst_edit);
        }

    }
}
