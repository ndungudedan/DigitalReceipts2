package com.example.dedan.digitalreceipts.Database.Month_Database.August;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.December.DecDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class AugRepo {
    private AugDao augDao;
    private AugEntity monthsale;
    private LiveData<List<AugEntity>> allEvents;

    public AugRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        augDao=appDatabase.augDao();
        allEvents=augDao.AllAugEvents();
    }
    public void insert(AugEntity augEntity){
        new insertAsyncTask(augDao).execute(augEntity);
    }
    public void update(AugEntity augEntity){
        new updateAsyncTask(augDao).execute(augEntity);
    }
    public AugEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(augDao);
        task.Repo=this;
        try {
            monthsale=task.execute(userid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return monthsale;
    }
    public LiveData<List<AugEntity>> AllAugEvents(){
        return allEvents;
    }
    public void deleteAll(){
        new deleteAllAsync(augDao).execute();
    }

    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private AugDao dao;
        public deleteAllAsync(AugDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
    private static class MonthUserSalesAsync extends AsyncTask<String,Void,AugEntity>{
        private AugDao dao;
        private AugRepo Repo=null;

        public MonthUserSalesAsync(AugDao dao) {
            this.dao = dao;
        }

        @Override
        protected AugEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<AugEntity,Void,Void> {
        private AugDao augDao;

        public insertAsyncTask(AugDao augDao) {
            this.augDao = augDao;
        }

        @Override
        protected Void doInBackground(AugEntity... augEntities) {
            augDao.insert(augEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<AugEntity,Void,Void>{
        private AugDao augDao;

        public updateAsyncTask(AugDao augDao) {
            this.augDao = augDao;
        }

        @Override
        protected Void doInBackground(AugEntity... augEntities) {
            augDao.update(augEntities[0]);
            return null;
        }
    }
}
