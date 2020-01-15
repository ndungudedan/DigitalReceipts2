package com.example.dedan.digitalreceipts.Database.Month_Database.February;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class Febrepo {
    private FebDao febDao;
    private LiveData<List<FebEntity>> allEvents;
    private FebEntity monthsale;

    public Febrepo(Application application) {
        AppDatabase database=AppDatabase.getInstance(application);
        febDao=database.febDao();
        allEvents=febDao.AllFebEvents();
    }
    public void insert(FebEntity febEntity){
        new insertAsyncTask(febDao).execute(febEntity);
    }
    public void update(FebEntity febEntity){
        new updateAsyncTask(febDao).execute(febEntity);
    }
    public LiveData<List<FebEntity>> AllFebEvents(){
        return allEvents;
    }
    public FebEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(febDao);
        task.Repo=this;
        try {
            monthsale =task.execute(userid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return monthsale;
    }
    public void deleteAll(){
        new deleteAllAsync(febDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private FebDao dao;
        public deleteAllAsync(FebDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class MonthUserSalesAsync extends AsyncTask<String,Void,FebEntity>{
        private FebDao dao;
        private Febrepo Repo=null;

        public MonthUserSalesAsync(FebDao dao) {
            this.dao = dao;
        }

        @Override
        protected FebEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }

    }

    private static class insertAsyncTask extends android.os.AsyncTask<FebEntity,Void,Void> {
        private FebDao febDao;

        public insertAsyncTask(FebDao febDao) {
            this.febDao = febDao;
        }

        @Override
        protected Void doInBackground(FebEntity... febEntities) {
            febDao.insert(febEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<FebEntity,Void,Void> {
        private FebDao febDao;

        public updateAsyncTask(FebDao febDao) {
            this.febDao = febDao;
        }

        @Override
        protected Void doInBackground(FebEntity... febEntities) {
            febDao.update(febEntities[0]);
            return null;
        }
    }
}
