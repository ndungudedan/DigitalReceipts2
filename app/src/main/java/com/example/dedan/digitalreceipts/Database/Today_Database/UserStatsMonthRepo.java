package com.example.dedan.digitalreceipts.Database.Today_Database;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class UserStatsMonthRepo {
    private LiveData<List<UserStatsMonthEntity>> allEvents;
    private UserStatsMonthDao Daydao;

    public UserStatsMonthRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        Daydao =appDatabase.todayDao();
        allEvents= Daydao.AllEvents();
    }
    public void insert(UserStatsMonthEntity userStatsMonthEntity){
        new insertAsyncTask(Daydao).execute(userStatsMonthEntity);
    }
    public void update(UserStatsMonthEntity userStatsMonthEntity){
        new updateAsyncTask(Daydao).execute(userStatsMonthEntity);
    }
    public LiveData<List<UserStatsMonthEntity>> AllDayEvents(){
        return allEvents;
    }

    public void deleteAll(){
        new deleteAllAsync(Daydao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private UserStatsMonthDao dao;
        public deleteAllAsync(UserStatsMonthDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
    private static class insertAsyncTask extends AsyncTask<UserStatsMonthEntity,Void,Void> {
        private UserStatsMonthDao dao;
        public insertAsyncTask(UserStatsMonthDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserStatsMonthEntity... todayEntities) {
            dao.insert(todayEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<UserStatsMonthEntity,Void,Void>{
        private UserStatsMonthDao dao;

        public updateAsyncTask(UserStatsMonthDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(UserStatsMonthEntity... todayEntities) {
            dao.update(todayEntities[0]);
            return null;
        }
    }
}
