package com.example.dedan.digitalreceipts.Database.Month_Database.June;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class JunRepo {
    private JunDao junDao;
    private JunEntity monthsale;
    private LiveData<List<JunEntity>> allEvents;

    public JunRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        junDao=appDatabase.junDao();
        allEvents=junDao.AllJunEvents();
    }
    public void insert(JunEntity junEntity){
        new insertAsyncTask(junDao).execute(junEntity);
    }
    public void update(JunEntity junEntity){
        new updateAsyncTask(junDao).execute(junEntity);
    }
    public LiveData<List<JunEntity>> AllJunEvents(){
        return allEvents;
    }
    public JunEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(junDao);
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
        new deleteAllAsync(junDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private JunDao dao;
        public deleteAllAsync(JunDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class MonthUserSalesAsync extends AsyncTask<String,Void,JunEntity> {
        private JunDao dao;
        private JunRepo Repo=null;

        public MonthUserSalesAsync(JunDao dao) {
            this.dao = dao;
        }

        @Override
        protected JunEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }
    }
    private static class insertAsyncTask extends AsyncTask<JunEntity,Void,Void> {
        private JunDao junDao;

        public insertAsyncTask(JunDao junDao) {
            this.junDao = junDao;
        }

        @Override
        protected Void doInBackground(JunEntity... junEntities) {
            junDao.insert(junEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<JunEntity,Void,Void>{
        private JunDao junDao;

        public updateAsyncTask(JunDao junDao) {
            this.junDao = junDao;
        }

        @Override
        protected Void doInBackground(JunEntity... junEntities) {
            junDao.update(junEntities[0]);
            return null;
        }
    }
}
