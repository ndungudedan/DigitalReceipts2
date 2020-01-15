package com.example.dedan.digitalreceipts.Database.Month_Database.February;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FebViewModel extends AndroidViewModel {
    private Febrepo febrepo;
    public FebViewModel(@NonNull Application application) {
        super(application);
        febrepo=new Febrepo(application);
    }
    public void insert(FebEntity febEntity){
        febrepo.insert(febEntity);
    }
    public void update(FebEntity febEntity){
        febrepo.update(febEntity);
    }
    public LiveData<List<FebEntity>> AllFebEvents(){
        return febrepo.AllFebEvents();
    }
    public FebEntity getUserMonthSales(String userId){
        return febrepo.getMonthUserSales(userId);
    }
    public void deleteAll(){
        febrepo.deleteAll();
    }
}
