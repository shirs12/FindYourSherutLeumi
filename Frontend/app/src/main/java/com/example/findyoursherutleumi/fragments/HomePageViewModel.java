package com.example.findyoursherutleumi.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findyoursherutleumi.models.ServicePartial;
import com.example.findyoursherutleumi.repositories.ServicesPartialRepository;

import java.util.List;

/**
 * This class is the HomePage viewmodel of the main screen,
 * to set up the services list from db.
 * Viewmodel aware of android app lifecycle, and makes it easy to handle the data.
 */
public class HomePageViewModel extends ViewModel {

    private MutableLiveData<List<ServicePartial>> servicesPartialLst;
    private ServicesPartialRepository repository;

    public void init(){
        if (servicesPartialLst != null)
            return;
        repository = ServicesPartialRepository.getInstance();
        servicesPartialLst = repository.getServices();
    }
    public LiveData<List<ServicePartial>> getServices(){
        return servicesPartialLst;
    }

    public void loadServices() {
        servicesPartialLst.postValue(repository.getServices().getValue());
    }

}