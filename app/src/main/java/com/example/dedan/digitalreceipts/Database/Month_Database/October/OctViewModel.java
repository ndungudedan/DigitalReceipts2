package com.example.dedan.digitalreceipts.Database.Month_Database.October;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class OctViewModel extends AndroidViewModel {
    private OctRepo octRepo;
    public OctViewModel(@NonNull Application application) {
        super(application);
        octRepo=new OctRepo(application);
    }
    public void insert(OctEntity octEntity){
        octRepo.insert(octEntity);
    }
    public void update(OctEntity octEntity){
        octRepo.update(octEntity);
    }
    public OctEntity getUserMonthSales(String userId){
        return octRepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        octRepo.deleteAll();
    }
}
