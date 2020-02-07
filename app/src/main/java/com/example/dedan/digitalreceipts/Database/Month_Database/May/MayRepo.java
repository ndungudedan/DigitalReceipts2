package com.example.dedan.digitalreceipts.Database.Month_Database.May;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class MayRepo {
    private final MayDao mayDao;
    private MayEntity monthsale;
    private LiveData<List<MayEntity>> allEvents;

    public MayRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        mayDao =appDatabase.mayDao();
        allEvents=mayDao.AllMayEvents();
    }
    public void insert(MayEntity mayEntity){
        new insertAsyncTask(mayDao).execute(mayEntity);
    }
    public void update(MayEntity mayEntity){
        new updateAsyncTask(mayDao).execute(mayEntity);
    }
    public MayEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(mayDao);
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
    public LiveData<List<MayEntity>> AllMayEvents(){
        return allEvents;
    }
    public void deleteAll(){
        new deleteAllAsync(mayDao).execute();
    }

    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private MayDao dao;
        public deleteAllAsync(MayDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
    private static class MonthUserSalesAsync extends AsyncTask<String,Void,MayEntity> {
        private MayDao dao;
        private MayRepo Repo=null;

        public MonthUserSalesAsync(MayDao dao) {
            this.dao = dao;
        }

        @Override
        protected MayEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<MayEntity,Void,Void> {
        private MayDao mayDao;

        public insertAsyncTask(MayDao mayDao) {
            this.mayDao = mayDao;
        }

        @Override
        protected Void doInBackground(MayEntity... mayEntities) {
            mayDao.insert(mayEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<MayEntity,Void,Void>{
        private MayDao mayDao;

        public updateAsyncTask(MayDao mayDao) {
            this.mayDao = mayDao;
        }

        @Override
        protected Void doInBackground(MayEntity... mayEntities) {
            mayDao.update(mayEntities[0]);
            return null;

        }
    }
}
