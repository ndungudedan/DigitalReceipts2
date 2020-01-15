package com.example.dedan.digitalreceipts.Database.Week_Database.Monday;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class MonRepo {
    private LiveData<List<MonEntity>> allEvents;
    private MonEntity daysale;
    private MonDao Daydao;

    public MonRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        Daydao =appDatabase.monDao();
        allEvents= Daydao.AllEvents();
    }
    public void insert(MonEntity monEntity){
        new insertAsyncTask(Daydao).execute(monEntity);
    }
    public void update(MonEntity monEntity){
        new updateAsyncTask(Daydao).execute(monEntity);
    }
    public LiveData<List<MonEntity>> AllDayEvents(){
        return allEvents;
    }
    public MonEntity getDayUserSales(String userid){
        DayUserSalesAsync task=new DayUserSalesAsync(Daydao);
        task.Repo=this;
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
        private MonDao dao;
        public deleteAllAsync(MonDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DayUserSalesAsync extends AsyncTask<String,Void, MonEntity> {
        private MonDao dao;
        private MonRepo Repo=null;

        public DayUserSalesAsync(MonDao dao) {
            this.dao = dao;
        }

        @Override
        protected MonEntity doInBackground(String... strings) {
            return dao.getDayUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<MonEntity,Void,Void> {
        private MonDao dao;

        public insertAsyncTask(MonDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MonEntity... monEntities) {
            dao.insert(monEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<MonEntity,Void,Void>{
        private MonDao dao;

        public updateAsyncTask(MonDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(MonEntity... monEntities) {
            dao.update(monEntities[0]);
            return null;
        }
    }
}
