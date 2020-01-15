package com.example.dedan.digitalreceipts.Database.Week_Database.Saturday;

import android.app.Application;

import com.example.dedan.digitalreceipts.Database.Week_Database.Monday.MonEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Monday.MonRepo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SatViewModel extends AndroidViewModel {
    private SatRepo satRepo;
    public SatViewModel(@NonNull Application application) {
        super(application);
        satRepo=new SatRepo(application);
    }
    public void insert(SatEntity satEntity){
        satRepo.insert(satEntity);
    }
    public void update(SatEntity satEntity){
        satRepo.update(satEntity);
    }
    public LiveData<List<SatEntity>> AllDayEvents(){
        return satRepo.AllDayEvents();
    }
    public SatEntity getUserDaySales(String userId){
        return satRepo.getDayUserSales(userId);
    }
    public void deleteAll(){
        satRepo.deleteAll();
    }
}
