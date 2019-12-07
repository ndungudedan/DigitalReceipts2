package com.example.dedan.digitalreceipts.Month_Database.December;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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
    public LiveData<DecEntity> getUserMonthsales(int userid){
        return decRepo.getMonthUserSales(userid);
    }
}
