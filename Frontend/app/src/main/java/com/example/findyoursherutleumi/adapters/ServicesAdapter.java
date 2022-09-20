package com.example.findyoursherutleumi.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.fragments.ServiceDetailsFragment;
import com.example.findyoursherutleumi.models.ServicePartial;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private List<ServicePartial> servicesLst;
    private List<ServicePartial> fullLst = new ArrayList<>();

    public ServicesAdapter(LayoutInflater inflater, List<ServicePartial> servicesLst){
        this.inflater = inflater;
        this.servicesLst = servicesLst;
        this.fullLst.addAll(servicesLst);
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
        holder.detailsBtn.setText(R.string.show_details);

        holder.organizationTxt.setText(servicesLst.get(position).getOrganizationName());
        holder.categoryTxt.setText(servicesLst.get(position).getCategory());
        holder.countryTxt.setText(servicesLst.get(position).getCountry());
        holder.cityTxt.setText(servicesLst.get(position).getCity());
        holder.descriptionTxt.setText(servicesLst.get(position).getDescriptionService());

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
//        this.servicesLst.clear();
        this.servicesLst = mServicesLst;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        Button detailsBtn;
        TextView organizationTxt;
        TextView categoryTxt;
        TextView countryTxt;
        TextView cityTxt;
        TextView descriptionTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.service_title_lst);
            detailsBtn = itemView.findViewById(R.id.show_details_btn);

            organizationTxt = itemView.findViewById(R.id.organization_details_txt);
            categoryTxt = itemView.findViewById(R.id.category_details_txt);
            countryTxt = itemView.findViewById(R.id.country_details_txt);
            cityTxt = itemView.findViewById(R.id.city_details_txt);
            descriptionTxt = itemView.findViewById(R.id.description_details_txt);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(CharSequence charSequence) {
        List<ServicePartial> filteredLst = new ArrayList<>();
        if (TextUtils.isEmpty(charSequence))
            filteredLst.addAll(servicesLst);
        else{
            for (ServicePartial data : servicesLst) {
                if (data.getServiceName().toLowerCase().contains(charSequence)
                    || data.getCategory().toLowerCase().contains(charSequence)
                    || data.getCity().toLowerCase().contains(charSequence))
                    filteredLst.add(data);
            }
            if (filteredLst.isEmpty()) {
                Toast.makeText(inflater.getContext(), R.string.no_result, Toast.LENGTH_SHORT).show();
                updateServicesList(servicesLst);
                System.out.println("size servicesLst0:   " + servicesLst.size());
                notifyDataSetChanged();
            }
            else updateServicesList(filteredLst);
        }
        //updateServicesList(fullLst);
        notifyDataSetChanged();
    }



//    @SuppressLint("NotifyDataSetChanged")
//    public void filter(CharSequence charSequence) {
//        List<ServicePartial> filteredLst = new ArrayList<>();
//        if (!TextUtils.isEmpty(charSequence)) {
//            for (ServicePartial data : servicesLst) {
//                if (data.getServiceName().toLowerCase().contains(charSequence)
//                    || data.getCategory().toLowerCase().contains(charSequence)
//                    || data.getCity().toLowerCase().contains(charSequence)) {
//                    filteredLst.add(data);
//                }
//            }
//
//            if (filteredLst.isEmpty()) {
//                Toast.makeText(inflater.getContext(), R.string.no_result, Toast.LENGTH_SHORT).show();
//                updateServicesList(servicesLst);
//                System.out.println("size servicesLst0:   " + servicesLst.size());
//                notifyDataSetChanged();
//            }
//            updateServicesList(filteredLst);
//            notifyDataSetChanged();
//
//        } else{
//            updateServicesList(fullLst);
//            System.out.println("size filteredLst:   " + filteredLst.size());
//            System.out.println("size servicesLst1:   " + servicesLst.size());
//            System.out.println("size fullLst:   " + fullLst.size());
//            System.out.println("size servicesLst2:   " + servicesLst.size());
//            notifyDataSetChanged();
//        }
////        updateServicesList(fullLst);
////        servicesLst.clear();
////        servicesLst.addAll(tempLst);
////            notifyDataSetChanged();
////        tempLst.clear();
//    }








//    @Override
//    public Filter getFilter() {
//        return servicesFiltered;
//    }
//
//    private Filter servicesFiltered = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            List<ServicePartial> filteredList = new ArrayList<>();
//            if (charSequence == null || charSequence.length() == 0){
//                filteredList.addAll(servicesLst);
//            } else {
//                String filterPattern = charSequence.toString().toLowerCase().trim();
//                for (ServicePartial item : servicesLst){
//                    if (item.getServiceName().toLowerCase().contains(filterPattern)){
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//
//            return results;
//        }
//
//        @SuppressLint("NotifyDataSetChanged")
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            servicesLstSearch.clear();
//            servicesLstSearch.addAll((List) filterResults.values);
//            notifyDataSetChanged();
//        }
//    };
}