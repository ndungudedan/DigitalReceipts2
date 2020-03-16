package com.example.dedan.digitalreceipts.Database.CompDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class detailsViewModel extends AndroidViewModel {
    private detailsRepository detailsRepository;
    public detailsViewModel(@NonNull Application application) {
        super(application);
        detailsRepository= new detailsRepository(application);
    }
    public void insert(detailsEntity detailsEntity){
        detailsRepository.insert(detailsEntity);
    }
    public void update(detailsEntity detailsEntity){
        detailsRepository.update(detailsEntity);
    }
    public LiveData<detailsEntity> AllDetails(){
        return detailsRepository.AllDetails();
    }
}
