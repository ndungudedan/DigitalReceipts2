package com.example.dedan.digitalreceipts.Database.Month_Database.December;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class DecViewModel extends AndroidViewModel {
    private DecRepo decRepo;
    public DecViewModel(@NonNull Application application) {
        super(application);
        decRepo=new DecRepo(application);
    }
    public void insert(DecEntity decEntity){
        decRepo.insert(decEntity);
    }
    public void update(DecEntity decEntity){
        decRepo.update(decEntity);
    }
    public DecEntity getUserMonthsales(String userid){
        return decRepo.getMonthUserSales(userid);
    }
    public void deleteAll(){
        decRepo.deleteAll();
    }
}
