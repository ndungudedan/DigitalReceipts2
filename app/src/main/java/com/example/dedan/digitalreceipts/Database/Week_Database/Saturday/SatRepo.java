package com.example.dedan.digitalreceipts.Database.Week_Database.Saturday;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class SatRepo {
    private LiveData<List<SatEntity>> allEvents;
    private SatEntity daysale;
    private SatDao Daydao;

    public SatRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        Daydao =appDatabase.satDao();
        allEvents= Daydao.AllEvents();
    }
    public void insert(SatEntity satEntity){
        new insertAsyncTask(Daydao).execute(satEntity);
    }
    public void update(SatEntity satEntity){
        new updateAsyncTask(Daydao).execute(satEntity);
    }
    public LiveData<List<SatEntity>> AllDayEvents(){
        return allEvents;
    }
    public SatEntity getDayUserSales(String userid){
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
        private SatDao dao;
        public deleteAllAsync(SatDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DayUserSalesAsync extends AsyncTask<String,Void, SatEntity> {
        private SatDao dao;

        public DayUserSalesAsync(SatDao dao) {
            this.dao = dao;
        }

        @Override
        protected SatEntity doInBackground(String... strings) {
            return dao.getDayUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<SatEntity,Void,Void> {
        private SatDao dao;

        private insertAsyncTask(SatDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SatEntity... satEntities) {
            dao.insert(satEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<SatEntity,Void,Void>{
        private SatDao dao;

        public updateAsyncTask(SatDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SatEntity... satEntities) {
            dao.update(satEntities[0]);
            return null;
        }
    }
}
