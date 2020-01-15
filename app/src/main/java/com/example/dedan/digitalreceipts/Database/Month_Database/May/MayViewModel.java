package com.example.dedan.digitalreceipts.Database.Month_Database.May;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MayViewModel extends AndroidViewModel {
    private MayRepo mayRepo;
    public MayViewModel(@NonNull Application application) {
        super(application);
        mayRepo=new MayRepo(application);
    }
    public void insert(MayEntity mayEntity){
        mayRepo.insert(mayEntity);
    }
    public void update(MayEntity mayEntity){
        mayRepo.update(mayEntity);
    }
    public MayEntity getUserMonthSales(String userId){
        return mayRepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        mayRepo.deleteAll();
    }
}
