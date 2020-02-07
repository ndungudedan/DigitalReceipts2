package com.example.dedan.digitalreceipts.Database.Month_Database.May;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MayViewModel extends AndroidViewModel {
    private MayRepo mayRepo;
    public MayViewModel(@NonNull Application application) {
        super(application);
        mayRepo=new MayRepo(application);
    }
    public void insert(MayEntity mayEntity){
        mayRepo.insert(mayEntity);
    }
    public void update(MayEntity mayEntity){
        mayRepo.update(mayEntity);
    }
    public MayEntity getUserMonthSales(String userId){
        return mayRepo.getMonthUserSales(userId);
    }
    public LiveData<List<MayEntity>> AllMayEvents() {
        return mayRepo.AllMayEvents();
    }
        public void deleteAll(){
        mayRepo.deleteAll();
    }
}
