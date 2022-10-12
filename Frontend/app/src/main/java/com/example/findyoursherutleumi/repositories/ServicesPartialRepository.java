package com.example.findyoursherutleumi.repositories;

import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.findyoursherutleumi.R;
import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.ServicePartial;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Singleton pattern - can have only one object
public class ServicesPartialRepository {

    private static ServicesPartialRepository instance;
    private final ArrayList<ServicePartial> dataSet = new ArrayList<>();
    private Button detailsBtn;

    APIInterface apiInterface;

    public static ServicesPartialRepository getInstance(){
        if (instance == null)
            instance = new ServicesPartialRepository();
        return instance;
    }

    public MutableLiveData<List<ServicePartial>> getServices(){
        setServices();
        MutableLiveData<List<ServicePartial>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    public void setServices(){
        apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<ServicePartial>> call = apiInterface.getAllServicesPartially();
        call.enqueue(new Callback<List<ServicePartial>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServicePartial>> call, @NonNull Response<List<ServicePartial>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(detailsBtn.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
                assert response.body() != null;
                dataSet.clear();
                dataSet.addAll(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<List<ServicePartial>> call, @NonNull Throwable t) {
                Toast.makeText(detailsBtn.getContext(), R.string.connection_failed_toast, Toast.LENGTH_SHORT).show();
            }
        });
    }





}
