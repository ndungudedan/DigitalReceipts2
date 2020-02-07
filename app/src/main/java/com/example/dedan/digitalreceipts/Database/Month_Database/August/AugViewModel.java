package com.example.dedan.digitalreceipts.Database.Month_Database.August;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AugViewModel extends AndroidViewModel {
    private AugRepo augRepo;
    public AugViewModel(@NonNull Application application) {
        super(application);
        augRepo=new AugRepo(application);
    }
    public void insert(AugEntity augEntity){
        augRepo.insert(augEntity);
    }
    public void update(AugEntity augEntity){
        augRepo.update(augEntity);
    }
    public LiveData<List<AugEntity>> AllAugEvents(){
        return augRepo.AllAugEvents();
    }
    public AugEntity getUserMonthSales(String userId){
        return augRepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        augRepo.deleteAll();
    }
}
