package com.example.dedan.digitalreceipts.Database.Month_Database.July;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class JulRepo {
    private JulDao julDao;
    private JulEntity monthsale;
    private LiveData<List<JulEntity>> allEvents;

    public JulRepo(Application application) {
        AppDatabase database=AppDatabase.getInstance(application);
        julDao=database.julDao();
        allEvents=julDao.AllJulEvents();
    }

    public void insert(JulEntity julEntity){
        new insertAsyncTask(julDao).execute(julEntity);
    }
    public void update(JulEntity julEntity){
        new updateAsyncTask(julDao).execute(julEntity);
    }
    public JulEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(julDao);
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
    public LiveData<List<JulEntity>> AllJulEvents(){
        return allEvents;
    }
    public void deleteAll(){
        new deleteAllAsync(julDao).execute();
    }

    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private JulDao dao;
        public deleteAllAsync(JulDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class MonthUserSalesAsync extends AsyncTask<String,Void,JulEntity> {
        private JulDao dao;
        private JulRepo Repo=null;

        public MonthUserSalesAsync(JulDao dao) {
            this.dao = dao;
        }

        @Override
        protected JulEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<JulEntity,Void,Void> {
        private JulDao dao;

        public insertAsyncTask(JulDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(JulEntity... julEntities) {
            dao.insert(julEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<JulEntity,Void,Void>{
       private JulDao dao;

        public updateAsyncTask(JulDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(JulEntity... julEntities) {
            dao.update(julEntities[0]);
            return null;
        }
    }
}
