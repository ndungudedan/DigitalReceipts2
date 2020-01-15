package com.example.dedan.digitalreceipts.Database.Month_Database.October;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.PickedGoodDao;

import java.util.concurrent.ExecutionException;

public class OctRepo {
    private OctDao octDao;
    private OctEntity monthsale;

    public OctRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        octDao=appDatabase.octDao();
    }
    public void insert(OctEntity octEntity){
        new insertAsyncTask(octDao).execute(octEntity);
    }
    public void update(OctEntity octEntity){
        new updateAsyncTask(octDao).execute(octEntity);
    }
    public OctEntity getMonthUserSales(String userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(octDao);
        task.Repo=this;
        try {
            monthsale =task.execute(userid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return monthsale;
    }
    public void deleteAll(){
        new deleteAllAsync(octDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private OctDao dao;
        public deleteAllAsync(OctDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class MonthUserSalesAsync extends AsyncTask<String,Void,OctEntity> {
        private OctDao dao;
        private OctRepo Repo=null;

        public MonthUserSalesAsync(OctDao dao) {
            this.dao = dao;
        }

        @Override
        protected OctEntity doInBackground(String... strings) {

            return dao.getMonthUserSales(strings[0]);
        }
    }
    private static class insertAsyncTask extends AsyncTask<OctEntity,Void,Void> {
        private OctDao octDao;

        public insertAsyncTask(OctDao octDao) {
            this.octDao = octDao;
        }
        @Override
        protected Void doInBackground(OctEntity... octEntities) {
            octDao.insert(octEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<OctEntity,Void,Void>{
        private OctDao octDao;

        public updateAsyncTask(OctDao octDao) {
            this.octDao = octDao;
        }

        @Override
        protected Void doInBackground(OctEntity... octEntities) {
            octDao.update(octEntities[0]);
            return null;
        }
    }
}
