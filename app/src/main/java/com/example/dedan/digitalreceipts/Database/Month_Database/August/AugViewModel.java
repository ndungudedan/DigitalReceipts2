package com.example.dedan.digitalreceipts.Database.Month_Database.August;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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
    public AugEntity getUserMonthSales(String userId){
        return augRepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        augRepo.deleteAll();
    }
}
