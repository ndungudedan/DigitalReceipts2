package com.example.dedan.digitalreceipts.Database.Week_Database.Monday;

import android.app.Application;

import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriRepo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MonViewModel extends AndroidViewModel {
    private MonRepo monRepo;
    public MonViewModel(@NonNull Application application) {
        super(application);
        monRepo=new MonRepo(application);
    }
    public void insert(MonEntity monEntity){
        monRepo.insert(monEntity);
    }
    public void update(MonEntity monEntity){
        monRepo.update(monEntity);
    }
    public LiveData<List<MonEntity>> AllDayEvents(){
        return monRepo.AllDayEvents();
    }
    public MonEntity getUserDaySales(String userId){
        return monRepo.getDayUserSales(userId);
    }
    public void deleteAll(){
        monRepo.deleteAll();
    }
}
