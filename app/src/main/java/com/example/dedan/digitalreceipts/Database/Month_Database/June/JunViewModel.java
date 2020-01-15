package com.example.dedan.digitalreceipts.Database.Month_Database.June;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class JunViewModel extends AndroidViewModel {
    private JunRepo junRepo;
    public JunViewModel(@NonNull Application application) {
        super(application);
        junRepo=new JunRepo(application);
    }
    public void insert(JunEntity junEntity){
        junRepo.insert(junEntity);
    }
    public void update(JunEntity junEntity){
        junRepo.update(junEntity);
    }
    public JunEntity getUserMonthSales(String userId){
        return junRepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        junRepo.deleteAll();
    }
}
