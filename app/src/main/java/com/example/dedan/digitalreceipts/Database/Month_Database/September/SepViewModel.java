package com.example.dedan.digitalreceipts.Database.Month_Database.September;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SepViewModel extends AndroidViewModel {
    private SepRepo sepRepo;
    public SepViewModel(@NonNull Application application) {
        super(application);
        sepRepo=new SepRepo(application);
    }
    public void insert(SepEntity sepEntity){
        sepRepo.insert(sepEntity);
    }
    public void update(SepEntity sepEntity){
        sepRepo.update(sepEntity);
    }
    public SepEntity getUserMonthSales(String userId){
        return sepRepo.getMonthUserSales(userId);
    }
    public LiveData<List<SepEntity>> AllSepEvents() {
        return sepRepo.AllSepEvents();
    }
        public void deleteAll(){
        sepRepo.deleteAll();
    }
}
