package com.example.dedan.digitalreceipts;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DailySalesViewModel extends AndroidViewModel {
    private DailySalesRepo dailySalesRepo;
    private LiveData<List<DailySalesEntity>> allDailySales;

    public DailySalesViewModel(@NonNull Application application) {
        super(application);
        dailySalesRepo=new DailySalesRepo(application);
        allDailySales=dailySalesRepo.getDailySales();
    }
    public void insert(DailySalesEntity dailySalesEntity){
        dailySalesRepo.insert(dailySalesEntity);
    }
    public void update(DailySalesEntity dailySalesEntity){
        dailySalesRepo.update(dailySalesEntity);
    }
    public void delete(DailySalesEntity dailySalesEntity){

    }
    public LiveData<List<DailySalesEntity>> getDailySales(){
        return allDailySales;
    }
}
