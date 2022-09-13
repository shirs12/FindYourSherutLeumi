package com.example.findyoursherutleumi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.models.ServicePartial;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<ServicePartial> servicesLst;

    public ServicesAdapter(LayoutInflater inflater, List<ServicePartial> servicesLst){
        this.inflater = inflater;
        this.servicesLst = servicesLst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service,parent,false);
        return new ViewHolder(rootView);
//        return new ViewHolder(inflater.inflate(R.layout.item_service, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(servicesLst.get(position).getServiceName());
        holder.detailsTxt.setText(servicesLst.get(position).toString());
        holder.detailsBtn.setText("SHOW DETAILS");
    }

    @Override
    public int getItemCount() {
        return 0;
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
