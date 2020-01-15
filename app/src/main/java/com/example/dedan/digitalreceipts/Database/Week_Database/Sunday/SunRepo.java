package com.example.dedan.digitalreceipts.Database.Week_Database.Sunday;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class SunRepo {
    private LiveData<List<SunEntity>> allEvents;
    private SunEntity daysale;
    private SunDao Daydao;

    public SunRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        Daydao =appDatabase.sunDao();
        allEvents= Daydao.AllEvents();
    }
    public void insert(SunEntity sunEntity){
        new insertAsyncTask(Daydao).execute(sunEntity);
    }
    public void update(SunEntity sunEntity){
        new updateAsyncTask(Daydao).execute(sunEntity);
    }
    public LiveData<List<SunEntity>> AllDayEvents(){
        return allEvents;
    }
    public SunEntity getDayUserSales(String userid){
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
        private SunDao dao;
        public deleteAllAsync(SunDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DayUserSalesAsync extends AsyncTask<String,Void, SunEntity> {
        private SunDao dao;

        public DayUserSalesAsync(SunDao dao) {
            this.dao = dao;
        }

        @Override
        protected SunEntity doInBackground(String... strings) {
            return dao.getDayUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<SunEntity,Void,Void> {
        private SunDao dao;

        private insertAsyncTask(SunDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(SunEntity... sunEntities) {
            dao.insert(sunEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<SunEntity,Void,Void>{
        private SunDao dao;

        public updateAsyncTask(SunDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(SunEntity... sunEntities) {
            dao.update(sunEntities[0]);
            return null;
        }
    }
}
