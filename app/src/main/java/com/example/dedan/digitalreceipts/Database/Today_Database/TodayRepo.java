package com.example.dedan.digitalreceipts.Database.Today_Database;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriDao;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriRepo;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class TodayRepo {
    private LiveData<List<TodayEntity>> allEvents;
    private TodayEntity daysale;
    private TodayDao Daydao;

    public TodayRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        Daydao =appDatabase.todayDao();
        allEvents= Daydao.AllEvents();
    }
    public void insert(TodayEntity todayEntity){
        new insertAsyncTask(Daydao).execute(todayEntity);
    }
    public void update(TodayEntity todayEntity){
        new updateAsyncTask(Daydao).execute(todayEntity);
    }
    public LiveData<List<TodayEntity>> AllDayEvents(){
        return allEvents;
    }
    public TodayEntity getTodayUserSales(String userid){
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
        private TodayDao dao;
        public deleteAllAsync(TodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DayUserSalesAsync extends AsyncTask<String,Void, TodayEntity> {
        private TodayDao dao;
        private TodayRepo Repo=null;

        public DayUserSalesAsync(TodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected TodayEntity doInBackground(String... strings) {
            return dao.getTodayUserSales(strings[0]);
        }
    }
    private static class insertAsyncTask extends AsyncTask<TodayEntity,Void,Void> {
        private TodayDao dao;
        public insertAsyncTask(TodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TodayEntity... todayEntities) {
            dao.insert(todayEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<TodayEntity,Void,Void>{
        private TodayDao dao;

        public updateAsyncTask(TodayDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TodayEntity... todayEntities) {
            dao.update(todayEntities[0]);
            return null;
        }
    }
}
