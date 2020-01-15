package com.example.dedan.digitalreceipts.Database.Month_Database.January;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class JanViewModel extends AndroidViewModel {
    private JanRepo janRepo;
    public JanViewModel(@NonNull Application application) {
        super(application);
        janRepo=new JanRepo(application);
    }
    public void insert(JanEntity janEntity){
        janRepo.insert(janEntity);
    }
    public void update(JanEntity janEntity){
        janRepo.update(janEntity);
    }
    public LiveData<List<JanEntity>> getAllJanEvents(){
        return janRepo.getJanEvents();
    }
    public JanEntity getUserMonthsales(String userid){
         return janRepo.getMonthUserSales(userid);
    }
    public void deleteAll(){
        janRepo.deleteAll();
    }
}
