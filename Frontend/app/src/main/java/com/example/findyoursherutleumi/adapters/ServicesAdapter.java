package com.example.findyoursherutleumi.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> implements Filterable {

    private final LayoutInflater inflater;
    private List<ServicePartial> servicesLst;
    private List<ServicePartial> servicesLstSearch;

    public ServicesAdapter(LayoutInflater inflater, List<ServicePartial> servicesLst){
        this.inflater = inflater;
        this.servicesLst = servicesLst;
        servicesLstSearch = new ArrayList<>(servicesLst);
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
            @Override
            public void onClick(View view) {
                if (view.getId() == holder.detailsBtn.findViewById(R.id.show_details_btn).getId()) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment newFragment = new ServiceDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    newFragment.setArguments(bundle);

                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, newFragment)
                            .addToBackStack(null)
                            .commit();
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

    @Override
    public Filter getFilter() {
        return servicesFiltered;
    }

    private Filter servicesFiltered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ServicePartial> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(servicesLst);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ServicePartial item : servicesLst){
                    if (item.getServiceName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            servicesLstSearch.clear();
            servicesLstSearch.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}