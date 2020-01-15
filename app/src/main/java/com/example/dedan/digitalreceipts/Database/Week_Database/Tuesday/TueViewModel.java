package com.example.dedan.digitalreceipts.Database.Week_Database.Tuesday;

import android.app.Application;
import android.renderscript.Float4;

import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatRepo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TueViewModel extends AndroidViewModel {
    private TueRepo tueRepo;
    public TueViewModel(@NonNull Application application) {
        super(application);
        tueRepo=new TueRepo(application);
    }
    public void insert(TueEntity tueEntity){
        tueRepo.insert(tueEntity);
    }
    public void update(TueEntity tueEntity){
        tueRepo.update(tueEntity);
    }
    public LiveData<List<TueEntity>> AllDayEvents(){
        return tueRepo.AllDayEvents();
    }
    public TueEntity getUserDaySales(String userId){
        return tueRepo.getDayUserSales(userId);
    }
    public void deleteAll(){
        tueRepo.deleteAll();
    }
}
