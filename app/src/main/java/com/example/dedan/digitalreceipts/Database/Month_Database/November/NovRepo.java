package com.example.dedan.digitalreceipts.Database.Month_Database.November;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class NovRepo {
    private LiveData<List<NovEntity>> allEvents;
    private NovDao novDao;
    private NovEntity monthsale;

    public NovRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        novDao =appDatabase.novDao();
        allEvents=novDao.AllNovEvents();
    }
    public void insert(NovEntity novEntity){
        new insertAsyncTask(novDao).execute(novEntity);
    }
    public void update(NovEntity novEntity){
        new updateAsyncTask(novDao).execute(novEntity);
    }
    public LiveData<List<NovEntity>> AllNovEvents(){
        return allEvents;
    }
    public NovEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(novDao);
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
        new deleteAllAsync(novDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private NovDao dao;
        public deleteAllAsync(NovDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class MonthUserSalesAsync extends AsyncTask<String,Void,NovEntity> {
        private NovDao dao;
        private NovRepo Repo=null;

        public MonthUserSalesAsync(NovDao dao) {
            this.dao = dao;
        }

        @Override
        protected NovEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<NovEntity,Void,Void> {
        private NovDao novDao;

        public insertAsyncTask(NovDao novDao) {
            this.novDao = novDao;
        }

        @Override
        protected Void doInBackground(NovEntity... novEntities) {
            novDao.insert(novEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<NovEntity,Void,Void>{
        private NovDao novDao;

        public updateAsyncTask(NovDao novDao) {
            this.novDao = novDao;
        }

        @Override
        protected Void doInBackground(NovEntity... novEntities) {
            novDao.update(novEntities[0]);
            return null;
        }
    }
}
