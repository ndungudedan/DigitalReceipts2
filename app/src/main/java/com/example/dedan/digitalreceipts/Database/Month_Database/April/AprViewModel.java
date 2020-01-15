package com.example.dedan.digitalreceipts.Database.Month_Database.April;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AprViewModel extends AndroidViewModel {
    private AprRepo aprRepo;
    public AprViewModel(@NonNull Application application) {
        super(application);
        aprRepo=new AprRepo(application);
    }
    public void insert(AprEntity aprEntity){
        aprRepo.insert(aprEntity);
    }
    public void update(AprEntity aprEntity){
        aprRepo.update(aprEntity);
    }
    public LiveData<List<AprEntity>> AllAprEvents(){
        return aprRepo.AllAprEvents();
    }
    public AprEntity getUserMonthSales(String userId){
        return aprRepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        aprRepo.deleteAll();
    }
}
