package com.example.dedan.digitalreceipts.Database.Week_Database.Friday;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

@Dao
public class FriRepo {
    private LiveData<List<FriEntity>> allEvents;
    private FriEntity daysale;
    private FriDao Daydao;

    public FriRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        Daydao =appDatabase.friDao();
        allEvents= Daydao.AllEvents();
    }
    public void insert(FriEntity friEntity){
        new insertAsyncTask(Daydao).execute(friEntity);
    }
    public void update(FriEntity friEntity){
        new updateAsyncTask(Daydao).execute(friEntity);
    }
    public LiveData<List<FriEntity>> AllDayEvents(){
        return allEvents;
    }
    public FriEntity getDayUserSales(String userid){
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
        private FriDao dao;
        public deleteAllAsync(FriDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class DayUserSalesAsync extends AsyncTask<String,Void, FriEntity> {
        private FriDao dao;
        private FriRepo Repo=null;

        public DayUserSalesAsync(FriDao dao) {
            this.dao = dao;
        }

        @Override
        protected FriEntity doInBackground(String... strings) {
            return dao.getDayUserSales(strings[0]);
        }

    }
    private static class insertAsyncTask extends AsyncTask<FriEntity,Void,Void> {
        private FriDao dao;

        public insertAsyncTask(FriDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(FriEntity... friEntities) {
            dao.insert(friEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<FriEntity,Void,Void>{
        private FriDao dao;

        public updateAsyncTask(FriDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FriEntity... friEntities) {
            dao.update(friEntities[0]);
            return null;
        }
    }
}
