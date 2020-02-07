package com.example.dedan.digitalreceipts.Database.Today_Database;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserStatsMonthViewModel extends AndroidViewModel {
    private UserStatsMonthRepo userStatsMonthRepo;
    public UserStatsMonthViewModel(@NonNull Application application) {
        super(application);
        userStatsMonthRepo =new UserStatsMonthRepo(application);
    }
    public void insert(UserStatsMonthEntity userStatsMonthEntity){
        userStatsMonthRepo.insert(userStatsMonthEntity);
    }
    public void update(UserStatsMonthEntity userStatsMonthEntity){
        userStatsMonthRepo.update(userStatsMonthEntity);
    }
    public LiveData<List<UserStatsMonthEntity>> AllTodayEvents(){
        return userStatsMonthRepo.AllDayEvents();
    }
    public void deleteAll(){
        userStatsMonthRepo.deleteAll();
    }
}
