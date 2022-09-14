package com.example.findyoursherutleumi.repositories;

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

// singleton pattern
public class ServicesPartialRepository {

    private static ServicesPartialRepository instance;
//    private final ArrayList<ServicePartial> dataSet = new ArrayList<>();
    private final MutableLiveData<List<ServicePartial>> data = new MutableLiveData<>();

    APIInterface apiInterface;

    public static ServicesPartialRepository getInstance(){
        if (instance == null)
            instance = new ServicesPartialRepository();
        return instance;
    }

    public MutableLiveData<List<ServicePartial>> getServices(){
        apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<ServicePartial>> call = apiInterface.getAllServicesPartially();
        call.enqueue(new Callback<List<ServicePartial>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServicePartial>> call, @NonNull Response<List<ServicePartial>> response) {
                assert response.body() != null;
                data.setValue(response.body());
                //TODO: delete prints
                System.out.println(response.body());
                System.out.println(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<List<ServicePartial>> call, @NonNull Throwable t) {
                System.out.println("failed");
            }
        });

//        data.setValue(dataSet);
        System.out.println("repooooo: " + data.getValue());
        return data;
    }




}
