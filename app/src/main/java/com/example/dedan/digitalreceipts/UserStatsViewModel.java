package com.example.dedan.digitalreceipts;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserStatsViewModel extends AndroidViewModel {
    UserStatsRepo userStatsRepo;
    private LiveData<List<UserStatsEntity>> allStats;

    public UserStatsViewModel(@NonNull Application application) {
        super(application);
        userStatsRepo=new UserStatsRepo(application);
        allStats=userStatsRepo.getAllUsersStats();
    }

    public void insert(UserStatsEntity userStatsEntity){
        userStatsRepo.insert(userStatsEntity);
    }

    public void update(UserStatsEntity userStatsEntity){
        userStatsRepo.update(userStatsEntity);
    }
    public LiveData<List<UserStatsEntity>> getAllstats(){
        return allStats;
    }
    public void deleteAll(){
        userStatsRepo.deleteAll();
    }

}
