package com.example.findyoursherutleumi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Service;
import com.example.findyoursherutleumi.models.ServicePartial;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditServicesAdapter extends RecyclerView.Adapter<EditServicesAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<ServicePartial> servicesLst;

    APIInterface apiInterface;

    public EditServicesAdapter(LayoutInflater inflater, List<ServicePartial> servicesLst){
        this.inflater = inflater;
        this.servicesLst = servicesLst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_coordinator,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.serviceNameTxt.setText(servicesLst.get(position).getServiceName());
        holder.cityTxt.setText(servicesLst.get(position).getCity());
        holder.deleteBtn.setText(R.string.delete);

        int id = servicesLst.get(position).getServiceId();
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == holder.deleteBtn.findViewById(R.id.delete_service_edit_btn).getId()) {
                    //TODO: add a dialog to make sure user really want to delete this service
                    Call<Service> call = apiInterface.deleteServiceById(servicesLst.get(position).getServiceId());
                    call.enqueue(new Callback<Service>() {
                        @Override
                        public void onResponse(Call<Service> call, Response<Service> response) {
                            // TODO: continue method
                        }

                        @Override
                        public void onFailure(Call<Service> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.servicesLst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        Button deleteBtn;
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
