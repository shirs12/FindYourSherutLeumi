package com.example.findyoursherutleumi.repositories;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.findyoursherutleumi.database.APIClient;
import com.example.findyoursherutleumi.database.APIInterface;
import com.example.findyoursherutleumi.models.ServicePartial;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Singleton pattern
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
                    System.out.println("something went wrong...");
                assert response.body() != null;
                dataSet.clear();
                dataSet.addAll(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<List<ServicePartial>> call, @NonNull Throwable t) {
                System.out.println("failed");
            }
        });
    }





}
