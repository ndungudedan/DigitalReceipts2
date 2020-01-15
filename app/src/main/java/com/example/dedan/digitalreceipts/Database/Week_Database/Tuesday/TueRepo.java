package com.example.dedan.digitalreceipts.Database.Week_Database.Tuesday;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class TueRepo {
    private LiveData<List<TueEntity>> allEvents;
    private TueEntity daysale;
    private TueDao Daydao;

    public TueRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        Daydao =appDatabase.tueDao();
        allEvents= Daydao.AllEvents();
    }
    public void insert(TueEntity tueEntity){
        new insertAsyncTask(Daydao).execute(tueEntity);
    }
    public void update(TueEntity tueEntity){
        new updateAsyncTask(Daydao).execute(tueEntity);
    }
    public LiveData<List<TueEntity>> AllDayEvents(){
        return allEvents;
    }
    public TueEntity getDayUserSales(String userid){
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
        private TueDao dao;
        public deleteAllAsync(TueDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DayUserSalesAsync extends AsyncTask<String,Void, TueEntity> {
        private TueDao dao;

        public DayUserSalesAsync(TueDao dao) {
            this.dao = dao;
        }
        @Override
        protected TueEntity doInBackground(String... strings) {
            return dao.getDayUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<TueEntity,Void,Void> {
        private TueDao dao;

        private insertAsyncTask(TueDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TueEntity... tueEntities) {
            dao.insert(tueEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<TueEntity,Void,Void>{
        private TueDao dao;

        public updateAsyncTask(TueDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TueEntity... tueEntities) {
            dao.update(tueEntities[0]);
            return null;
        }
    }
}
