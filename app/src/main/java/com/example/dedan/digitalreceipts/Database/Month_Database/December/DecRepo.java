package com.example.dedan.digitalreceipts.Database.Month_Database.December;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.concurrent.ExecutionException;

public class DecRepo {
    private DecDao decDao;
    private DecEntity monthsale;
    public DecRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        decDao=appDatabase.decDao();
    }
    public void insert(DecEntity decEntity){
        new insertasyncTask(decDao).execute(decEntity);
    }
    public void update(DecEntity decEntity){
        new updateasyncTask(decDao).execute(decEntity);
    }

    public DecEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(decDao);
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
    public void deleteAll(){
        new deleteAllAsync(decDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private DecDao dao;
        public deleteAllAsync(DecDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class MonthUserSalesAsync extends AsyncTask<String,Void,DecEntity>{
        private DecDao dao;
        private DecRepo Repo=null;

        public MonthUserSalesAsync(DecDao dao) {
            this.dao = dao;
        }

        @Override
        protected DecEntity doInBackground(String... strings) {
            return dao.getMonthUserSales(strings[0]);
        }
    }

    private static class insertasyncTask extends AsyncTask<DecEntity,Void,Void>{
        private DecDao decDao;

        public insertasyncTask(DecDao decDao) {
            this.decDao = decDao;
        }

        @Override
        protected Void doInBackground(DecEntity... decEntities) {
            decDao.insert(decEntities[0]);
            return null;
        }
    }
    private static class updateasyncTask extends AsyncTask<DecEntity,Void,Void>{
        private DecDao decDao;

        public updateasyncTask(DecDao decDao) {
            this.decDao = decDao;
        }

        @Override
        protected Void doInBackground(DecEntity... decEntities) {
            decDao.update(decEntities[0]);
            return null;
        }
    }
}
