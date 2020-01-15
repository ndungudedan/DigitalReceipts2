package com.example.dedan.digitalreceipts.Database.Week_Database.Thursday;

import android.app.Application;

import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatRepo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ThurViewModel extends AndroidViewModel {
    private ThurRepo thurRepo;
    public ThurViewModel(@NonNull Application application) {
        super(application);
        thurRepo=new ThurRepo(application);
    }
    public void insert(ThurEntity thurEntity){
        thurRepo.insert(thurEntity);
    }
    public void update(ThurEntity thurEntity){
        thurRepo.update(thurEntity);
    }
    public LiveData<List<ThurEntity>> AllDayEvents(){
        return thurRepo.AllDayEvents();
    }
    public ThurEntity getUserDaySales(String userId){
        return thurRepo.getDayUserSales(userId);
    }
    public void deleteAll(){
        thurRepo.deleteAll();
    }
}
