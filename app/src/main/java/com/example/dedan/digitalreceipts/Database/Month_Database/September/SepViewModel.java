package com.example.dedan.digitalreceipts.Database.Month_Database.September;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SepViewModel extends AndroidViewModel {
    private SepRepo sepRepo;
    public SepViewModel(@NonNull Application application) {
        super(application);
        sepRepo=new SepRepo(application);
    }
    public void insert(SepEntity sepEntity){
        sepRepo.insert(sepEntity);
    }
    public void update(SepEntity sepEntity){
        sepRepo.update(sepEntity);
    }
    public SepEntity getUserMonthSales(String userId){
        return sepRepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        sepRepo.deleteAll();
    }
}
