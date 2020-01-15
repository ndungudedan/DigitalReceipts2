package com.example.dedan.digitalreceipts.Database.Week_Database.Wednesday;

import android.app.Application;

import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatRepo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WedViewModel extends AndroidViewModel {
    private WedRepo wedRepo;
    public WedViewModel(@NonNull Application application) {
        super(application);
        wedRepo=new WedRepo(application);
    }
    public void insert(WedEntity wedEntity){
        wedRepo.insert(wedEntity);
    }
    public void update(WedEntity wedEntity){
        wedRepo.update(wedEntity);
    }
    public LiveData<List<WedEntity>> AllDayEvents(){
        return wedRepo.AllDayEvents();
    }
    public WedEntity getUserDaySales(String userId){
        return wedRepo.getDayUserSales(userId);
    }
    public void deleteAll(){
        wedRepo.deleteAll();
    }
}
