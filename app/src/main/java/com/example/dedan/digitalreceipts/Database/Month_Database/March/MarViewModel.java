package com.example.dedan.digitalreceipts.Database.Month_Database.March;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MarViewModel extends AndroidViewModel {
    private MarRepo marRepo;
    public MarViewModel(@NonNull Application application) {
        super(application);
        marRepo=new MarRepo(application);
    }
    public void insert(MarEntity marEntity){
        marRepo.insert(marEntity);
    }
    public void update(MarEntity marEntity){
        marRepo.update(marEntity);
    }
    public LiveData<List<MarEntity>> allMarEvents(){
        return marRepo.allMarEvents();
    }
    public MarEntity getUserMonthSales(String userId){
        return marRepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        marRepo.deleteAll();
    }
}
