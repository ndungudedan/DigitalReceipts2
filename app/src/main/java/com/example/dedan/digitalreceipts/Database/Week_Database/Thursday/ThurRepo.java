package com.example.dedan.digitalreceipts.Database.Week_Database.Thursday;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class ThurRepo {
    private LiveData<List<ThurEntity>> allEvents;
    private ThurEntity daysale;
    private ThurDao Daydao;

    public ThurRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        Daydao =appDatabase.thurDao();
        allEvents= Daydao.AllEvents();
    }
    public void insert(ThurEntity thurEntity){
        new insertAsyncTask(Daydao).execute(thurEntity);
    }
    public void update(ThurEntity thurEntity){
        new updateAsyncTask(Daydao).execute(thurEntity);
    }
    public LiveData<List<ThurEntity>> AllDayEvents(){
        return allEvents;
    }
    public ThurEntity getDayUserSales(String userid){
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
        private ThurDao dao;
        public deleteAllAsync(ThurDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DayUserSalesAsync extends AsyncTask<String,Void, ThurEntity> {
        private ThurDao dao;

        public DayUserSalesAsync(ThurDao dao) {
            this.dao = dao;
        }
        @Override
        protected ThurEntity doInBackground(String... strings) {
            return dao.getDayUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<ThurEntity,Void,Void> {
        private ThurDao dao;

        private insertAsyncTask(ThurDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ThurEntity... thurEntities) {
            dao.insert(thurEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<ThurEntity,Void,Void>{
        private ThurDao dao;

        public updateAsyncTask(ThurDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ThurEntity... thurEntities) {
            dao.update(thurEntities[0]);
            return null;
        }
    }
}
