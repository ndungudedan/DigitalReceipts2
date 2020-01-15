package com.example.dedan.digitalreceipts.Database.Month_Database.March;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class MarRepo {
    private MarDao marDao;
    private LiveData<List<MarEntity>> allEvents;
    private MarEntity monthsale;

    public MarRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        marDao=appDatabase.marDao();
        allEvents=marDao.AllMarEvents();
    }
    public void insert(MarEntity marEntity){
        new insertAsyncTask(marDao).execute(marEntity);
    }
    public void update(MarEntity marEntity){
        new updateAsyncTask(marDao).execute(marEntity);
    }
    public LiveData<List<MarEntity>> allMarEvents(){
        return allEvents;
    }
    public MarEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(marDao);
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
        new deleteAllAsync(marDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private MarDao dao;
        public deleteAllAsync(MarDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class MonthUserSalesAsync extends AsyncTask<String,Void,MarEntity>{
        private MarDao dao;
        private MarRepo Repo=null;

        public MonthUserSalesAsync(MarDao dao) {
            this.dao = dao;
        }

        @Override
        protected MarEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }

    }

    private static class insertAsyncTask extends android.os.AsyncTask<MarEntity,Void,Void> {
        private MarDao marDao;

        public insertAsyncTask(MarDao marDao) {
            this.marDao = marDao;
        }

        @Override
        protected Void doInBackground(MarEntity... marEntities) {
            marDao.insert(marEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<MarEntity,Void,Void>{
        private MarDao marDao;

        public updateAsyncTask(MarDao marDao) {
            this.marDao = marDao;
        }

        @Override
        protected Void doInBackground(MarEntity... marEntities) {
            marDao.update(marEntities[0]);
            return null;
        }
    }
}
