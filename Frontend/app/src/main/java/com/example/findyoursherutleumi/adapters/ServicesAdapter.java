package com.example.findyoursherutleumi.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.fragments.HomePageFragment;
import com.example.findyoursherutleumi.fragments.ServiceDetailsFragment;
import com.example.findyoursherutleumi.models.ServicePartial;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<ServicePartial> servicesLst;

    public ServicesAdapter(LayoutInflater inflater, List<ServicePartial> servicesLst){
        this.inflater = inflater;
        this.servicesLst = servicesLst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(servicesLst.get(position).getServiceName());
        holder.detailsTxt.setText(servicesLst.get(position).toString());
        holder.detailsBtn.setText("SHOW DETAILS");

        int id = servicesLst.get(position).getServiceId();
        holder.detailsBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (view.getId() == holder.detailsBtn.findViewById(R.id.show_details_btn).getId()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("serviceId", id);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment newFragment = new ServiceDetailsFragment();

                    newFragment.setArguments(bundle);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, newFragment)
                            .addToBackStack(null)
                            .commit();
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicesLst.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateServicesList(final List<ServicePartial> mServicesLst) {
        this.servicesLst.clear();
        this.servicesLst = mServicesLst;
//        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        Button detailsBtn;
        TextView detailsTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.service_title_lst);
            detailsBtn = itemView.findViewById(R.id.show_details_btn);
            detailsTxt = itemView.findViewById(R.id.service_details_text);

        }
    }
}
