package com.example.dedan.digitalreceipts.Database.Week_Database.Wednesday;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class WedRepo {
    private LiveData<List<WedEntity>> allEvents;
    private WedEntity daysale;
    private WedDao Daydao;

    public WedRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        Daydao =appDatabase.wedDao();
        allEvents= Daydao.AllEvents();
    }
    public void insert(WedEntity wedEntity){
        new insertAsyncTask(Daydao).execute(wedEntity);
    }
    public void update(WedEntity wedEntity){
        new updateAsyncTask(Daydao).execute(wedEntity);
    }
    public LiveData<List<WedEntity>> AllDayEvents(){
        return allEvents;
    }
    public WedEntity getDayUserSales(String userid){
        DayUserSalesAsync task=new DayUserSalesAsync(Daydao);
        try {
            daysale=task.execute(userid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return daysale;
    }
    public void deleteAll(){
        new deleteAllAsync(Daydao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private WedDao dao;
        public deleteAllAsync(WedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DayUserSalesAsync extends AsyncTask<String,Void, WedEntity> {
        private WedDao dao;

        public DayUserSalesAsync(WedDao dao) {
            this.dao = dao;
        }
        @Override
        protected WedEntity doInBackground(String... strings) {
            return dao.getDayUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<WedEntity,Void,Void> {
        private WedDao dao;

        private insertAsyncTask(WedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(WedEntity... wedEntities) {
            dao.insert(wedEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<WedEntity,Void,Void>{
        private WedDao dao;

        public updateAsyncTask(WedDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(WedEntity... wedEntities) {
            dao.update(wedEntities[0]);
            return null;
        }
    }
}
