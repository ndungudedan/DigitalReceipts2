package com.example.dedan.digitalreceipts.Database.Month_Database.July;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class JulViewModel extends AndroidViewModel {
    private JulRepo julRepo;
    public JulViewModel(@NonNull Application application) {
        super(application);
        julRepo=new JulRepo(application);
    }
    public void insert(JulEntity julEntity){
        julRepo.insert(julEntity);
    }
    public void update(JulEntity julEntity){
        julRepo.update(julEntity);
    }
    public JulEntity getUserMonthSales(String userId){
        return julRepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        julRepo.deleteAll();
    }
}
