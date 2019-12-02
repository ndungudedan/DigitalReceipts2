package com.example.dedan.digitalreceipts;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WeekSalesViewModel extends AndroidViewModel {
    private WeekSalesRepo weekSalesRepo;
    LiveData<List<WeekSalesEntity>> allWeekSales;
    public WeekSalesViewModel(@NonNull Application application) {
        super(application);
        weekSalesRepo=new WeekSalesRepo(application);
        allWeekSales=weekSalesRepo.getweeksales();
    }

    public void insert(WeekSalesEntity weekSalesEntity){
        weekSalesRepo.insert(weekSalesEntity);
    }
    public void update(WeekSalesEntity weekSalesEntity){
        weekSalesRepo.update(weekSalesEntity);
    }
    public void delete(WeekSalesEntity weekSalesEntity){

    }
    public LiveData<List<WeekSalesEntity>> getWeekSales(){
        return allWeekSales;
    }
}
