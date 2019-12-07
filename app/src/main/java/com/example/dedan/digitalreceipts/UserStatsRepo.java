package com.example.dedan.digitalreceipts;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class UserStatsRepo {
    private UserStatsDao userStatsDao;
    private LiveData<List<UserStatsEntity>> allStats;

    public UserStatsRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        userStatsDao=appDatabase.userStatsDao();
        allStats=userStatsDao.getAllUsersStats();
    }
    public void insert(UserStatsEntity userStatsEntity){
        new insertUserStatsAsync(userStatsDao).execute(userStatsEntity);

    }
    public void update(UserStatsEntity userStatsEntity){
        new updateUserStatsAsync(userStatsDao).execute(userStatsEntity);

    }
    public LiveData<List<UserStatsEntity>> getAllUsersStats(){
        return allStats;
    }

    private static class insertUserStatsAsync extends AsyncTask<UserStatsEntity,Void,Void>{
        private UserStatsDao userStatsDao;

        public insertUserStatsAsync(UserStatsDao userStatsDao) {
            this.userStatsDao = userStatsDao;
        }
        @Override
        protected Void doInBackground(UserStatsEntity... userStatsEntities) {
            userStatsDao.insert(userStatsEntities[0]);
            return null;
        }
    }
    private static class updateUserStatsAsync extends AsyncTask<UserStatsEntity,Void,Void>{
        private UserStatsDao userStatsDao;

        public updateUserStatsAsync(UserStatsDao userStatsDao) {
            this.userStatsDao = userStatsDao;
        }
        @Override
        protected Void doInBackground(UserStatsEntity... userStatsEntities) {
            userStatsDao.update(userStatsEntities[0]);
            return null;
        }
    }
}
