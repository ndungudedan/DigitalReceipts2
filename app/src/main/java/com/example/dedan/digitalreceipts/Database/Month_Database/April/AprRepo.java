package com.example.dedan.digitalreceipts.Database.Month_Database.April;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class AprRepo {
    private AprDao aprDao;
    private LiveData<List<AprEntity>> allEvents;
    private AprEntity monthsale;

    public AprRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        aprDao=appDatabase.aprDao();
        allEvents=aprDao.AllAprEvents();
    }
    public void insert(AprEntity aprEntity){
        new insertAsyncTask(aprDao).execute(aprEntity);
    }
    public void update(AprEntity aprEntity){
        new updateAsyncTask(aprDao).execute(aprEntity);
    }
    public LiveData<List<AprEntity>> AllAprEvents(){
        return allEvents;
    }
    public AprEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(aprDao);
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
    public void deleteAll(){
        new deleteAllAsync(aprDao).execute();
    }

    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private AprDao dao;
        public deleteAllAsync(AprDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
    private static class MonthUserSalesAsync extends AsyncTask<String,Void,AprEntity>{
        private AprDao dao;
        private AprRepo Repo=null;

        public MonthUserSalesAsync(AprDao dao) {
            this.dao = dao;
        }

        @Override
        protected AprEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<AprEntity,Void,Void> {
        private AprDao aprDao;

        public insertAsyncTask(AprDao aprDao) {
            this.aprDao = aprDao;
        }

        @Override
        protected Void doInBackground(AprEntity... aprEntities) {
            aprDao.insert(aprEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<AprEntity,Void,Void>{
        private AprDao aprDao;

        public updateAsyncTask(AprDao aprDao) {
            this.aprDao = aprDao;
        }

        @Override
        protected Void doInBackground(AprEntity... aprEntities) {
            aprDao.update(aprEntities[0]);
            return null;
        }
}
}
