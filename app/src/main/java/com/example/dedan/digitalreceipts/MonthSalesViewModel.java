package com.example.dedan.digitalreceipts;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MonthSalesViewModel extends AndroidViewModel {
    private MonthSalesRepo monthSalesRepo;
    private LiveData<List<MonthSalesEntity>> AllMonthSales;

        public MonthSalesViewModel(@NonNull Application application) {
        super(application);
        monthSalesRepo=new MonthSalesRepo(application);
        AllMonthSales=monthSalesRepo.getMonthSales();
        }
    public void insert(MonthSalesEntity monthSalesEntity){
            monthSalesRepo.insert(monthSalesEntity);
    }
    public void update(MonthSalesEntity monthSalesEntity){
            monthSalesRepo.update(monthSalesEntity);
    }
    public void delete(MonthSalesEntity monthSalesEntity){

    }
    public LiveData<List<MonthSalesEntity>> getMonthSales(){
            return AllMonthSales;
    }
}
