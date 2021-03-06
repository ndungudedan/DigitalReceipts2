package com.example.dedan.digitalreceipts.Database.Month_Database.November;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NovViewModel extends AndroidViewModel {
    private NovRepo novRepo;
    public NovViewModel(@NonNull Application application) {
        super(application);
        novRepo=new NovRepo(application);
    }
    public void insert(NovEntity novEntity){
        novRepo.insert(novEntity);
    }
    public void update(NovEntity novEntity){
        novRepo.update(novEntity);
    }
    public NovEntity getUserMonthSales(String userId){
        return novRepo.getMonthUserSales(userId);
    }
    public LiveData<List<NovEntity>> AllNovEvents() {
        return novRepo.AllNovEvents();
    }
    public void deleteAll(){
        novRepo.deleteAll();
    }
}
