package com.example.dedan.digitalreceipts.Month_Database.January;

import android.app.Application;

import com.example.dedan.digitalreceipts.Month_Database.December.DecEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class JanViewModel extends AndroidViewModel {
    private JanRepo janRepo;
    private LiveData<List<JanEntity>> allJanEvents;
    public JanViewModel(@NonNull Application application) {
        super(application);
        janRepo=new JanRepo(application);
        allJanEvents=janRepo.getJanEvents();
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
    public LiveData<JanEntity> getUserMonthsales(int userid){
        return janRepo.getMonthUserSales(userid);
    }
}
