package com.example.dedan.digitalreceipts.Database.Week_Database.Sunday;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SunViewModel extends AndroidViewModel {
    private SunRepo sunRepo;
    public SunViewModel(@NonNull Application application) {
        super(application);
        sunRepo=new SunRepo(application);
    }
    public void insert(SunEntity sunEntity){
        sunRepo.insert(sunEntity);
    }
    public void update(SunEntity sunEntity){
        sunRepo.update(sunEntity);
    }
    public LiveData<List<SunEntity>> AllDayEvents(){
        return sunRepo.AllDayEvents();
    }
    public SunEntity getUserDaySales(String userId){
        return sunRepo.getDayUserSales(userId);
    }
    public void deleteAll(){
        sunRepo.deleteAll();
    }
}
