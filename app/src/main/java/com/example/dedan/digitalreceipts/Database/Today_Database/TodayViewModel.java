package com.example.dedan.digitalreceipts.Database.Today_Database;

import android.app.Application;

import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriRepo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TodayViewModel extends AndroidViewModel {
    private TodayRepo todayRepo;
    public TodayViewModel(@NonNull Application application) {
        super(application);
        todayRepo=new TodayRepo(application);
    }
    public void insert(TodayEntity todayEntity){
        todayRepo.insert(todayEntity);
    }
    public void update(TodayEntity todayEntity){
        todayRepo.update(todayEntity);
    }
    public LiveData<List<TodayEntity>> AllTodayEvents(){
        return todayRepo.AllDayEvents();
    }
    public TodayEntity getUserTodaySales(String userId){
        return todayRepo.getTodayUserSales(userId);
    }
    public void deleteAll(){
        todayRepo.deleteAll();
    }
}
