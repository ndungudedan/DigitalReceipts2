package com.example.dedan.digitalreceipts.Database.Month_Database.September;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class SepRepo {
    private SepDao sepDao;
    private SepEntity monthsale;
    private LiveData<List<SepEntity>> allEvents;

    public SepRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        sepDao=appDatabase.sepDao();
        allEvents=sepDao.AllSepEvents();
    }
    public void insert(SepEntity sepEntity){
        new insertAsyncTask(sepDao).execute(sepEntity);
    }
    public void update(SepEntity sepEntity){
        new updateAsyncTask(sepDao).execute(sepEntity);
    }
    public LiveData<List<SepEntity>> AllSepEvents(){
        return allEvents;
    }
    public SepEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(sepDao);
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
        new deleteAllAsync(sepDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private SepDao dao;
        public deleteAllAsync(SepDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class MonthUserSalesAsync extends AsyncTask<String,Void,SepEntity> {
        private SepDao dao;
        private SepRepo Repo=null;

        public MonthUserSalesAsync(SepDao dao) {
            this.dao = dao;
        }

        @Override
        protected SepEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }
    }
    private static class insertAsyncTask extends AsyncTask<SepEntity,Void,Void> {
        private SepDao sepDao;

        public insertAsyncTask(SepDao sepDao) {
            this.sepDao = sepDao;
        }

        @Override
        protected Void doInBackground(SepEntity... sepEntities) {
            sepDao.insert(sepEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<SepEntity,Void,Void>{
        private SepDao sepDao;

        public updateAsyncTask(SepDao sepDao) {
            this.sepDao = sepDao;
        }

        @Override
        protected Void doInBackground(SepEntity... sepEntities) {
            sepDao.update(sepEntities[0]);
            return null;
        }
    }
}
