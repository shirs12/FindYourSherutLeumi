package com.example.findyoursherutleumi.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyoursherutleumi.MainActivity;
import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.Service;
import com.example.findyoursherutleumi.models.ServicePartial;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditServicesAdapter extends RecyclerView.Adapter<EditServicesAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<Service> servicesLst;

    APIInterface apiInterface;

    public EditServicesAdapter(LayoutInflater inflater, List<Service> servicesLst){
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

        int id = servicesLst.get(position).getServiceId();
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == holder.deleteBtn.findViewById(R.id.delete_service_edit_btn).getId()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
                    builder.setMessage(R.string.delete_service_dialog);
                    builder.setCancelable(true);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItem(id);
                            System.out.println("iddddddddddd:          " + id);
//                            Call<Service> call = apiInterface.deleteServiceById(id);
//                            call.enqueue(new Callback<Service>() {
//                                @SuppressLint("NotifyDataSetChanged")
//                                @Override
//                                public void onResponse(@NonNull Call<Service> call, @NonNull Response<Service> response) {
//                                    if(!response.isSuccessful())
//                                        Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
//                                    else{
//                                        Toast.makeText(inflater.getContext(), R.string.item_deleted_toast, Toast.LENGTH_SHORT).show();
//                                        notifyDataSetChanged();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<Service> call, Throwable t) {
//                                    Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
//                                }
//                            });
                        }
                    });
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    public void deleteItem(int id){
        Call<Service> call = apiInterface.deleteServiceById(id);
        call.enqueue(new Callback<Service>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<Service> call, @NonNull Response<Service> response) {
                if(!response.isSuccessful())
                    Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                else{
                    servicesLst.remove(id);
                    updateServicesList(servicesLst);
                    Toast.makeText(inflater.getContext(), R.string.item_deleted_toast, Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Service> call, Throwable t) {
                Toast.makeText(inflater.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
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

    public class ViewHolder extends RecyclerView.ViewHolder{

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
