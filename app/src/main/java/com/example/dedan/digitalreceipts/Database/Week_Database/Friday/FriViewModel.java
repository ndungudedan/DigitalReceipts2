package com.example.dedan.digitalreceipts.Database.Week_Database.Friday;

import android.app.Application;

import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprRepo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FriViewModel extends AndroidViewModel {
    private FriRepo friRepo;
    public FriViewModel(@NonNull Application application) {
        super(application);
        friRepo=new FriRepo(application);
    }
    public void insert(FriEntity friEntity){
        friRepo.insert(friEntity);
    }
    public void update(FriEntity friEntity){
        friRepo.update(friEntity);
    }
    public LiveData<List<FriEntity>> AllDayEvents(){
        return friRepo.AllDayEvents();
    }
    public FriEntity getUserDaySales(String userId){
        return friRepo.getDayUserSales(userId);
    }
    public void deleteAll(){
        friRepo.deleteAll();
    }
}
