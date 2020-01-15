package com.example.dedan.digitalreceipts.Database.Month_Database.January;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class JanRepo {
    private JanDao janDao;
    private LiveData<List<JanEntity>> allJanEvents;
    private JanEntity monthsale;

    public JanRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        janDao=appDatabase.janDao();
        allJanEvents=janDao.allJanEvents();
    }
    public void insert(JanEntity janEntity){
        new insertAsyncTask(janDao).execute(janEntity);
    }
    public void update(JanEntity janEntity){
        new updateAsyncTask(janDao).execute(janEntity);
    }
    public LiveData<List<JanEntity>> getJanEvents(){
    return allJanEvents;
    }

    public JanEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(janDao);
        task.janRepo=this;
        try {
            monthsale=task.execute(userid).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return monthsale;
    }
    public void deleteAll(){
        new deleteAllAsync(janDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private JanDao dao;
        public deleteAllAsync(JanDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<JanEntity,Void,Void>{
        private JanDao janDao;

        public insertAsyncTask(JanDao janDao) {
            this.janDao = janDao;
        }

        @Override
        protected Void doInBackground(JanEntity... janEntities) {
            janDao.insert(janEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<JanEntity,Void,Void>{
        private JanDao janDao;

        public updateAsyncTask(JanDao janDao) {
            this.janDao = janDao;
        }

        @Override
        protected Void doInBackground(JanEntity... janEntities) {
            janDao.update(janEntities[0]);
            return null;
        }
    }
    private static class MonthUserSalesAsync extends AsyncTask<String,Void,JanEntity>{
        private JanDao janDao;
        private JanRepo janRepo=null;

        public MonthUserSalesAsync(JanDao janDao) {
            this.janDao = janDao;
        }

        @Override
        protected JanEntity doInBackground(String... strings) {
            return janDao.getMonthUserSales(strings[0]);
        }

        @Override
        protected void onPostExecute(JanEntity janEntity) {
            super.onPostExecute(janEntity);
            janRepo.monthsale=janEntity;
        }
    }

}
