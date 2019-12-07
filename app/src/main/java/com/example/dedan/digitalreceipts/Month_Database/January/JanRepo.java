package com.example.dedan.digitalreceipts.Month_Database.January;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.AppDatabase;
import com.example.dedan.digitalreceipts.Month_Database.December.DecDao;
import com.example.dedan.digitalreceipts.Month_Database.December.DecEntity;
import com.example.dedan.digitalreceipts.Month_Database.December.DecRepo;

import java.util.List;

import androidx.lifecycle.LiveData;

public class JanRepo {
    private JanDao janDao;
    private LiveData<List<JanEntity>> allJanEvents;
    private LiveData<JanEntity> monthsale;

    public JanRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        janDao=appDatabase.janDao();
        allJanEvents=janDao.allJanEvents();
    }
    public void insert(JanEntity janEntity){
        new insertAsyncTask(janDao).execute(janEntity);
    }
    public void update(JanEntity janEntity){
        new updateAsyncTask(janDao).execute(janEntity);
    }
    public LiveData<List<JanEntity>> getJanEvents(){
    return allJanEvents;
    }

    public LiveData<JanEntity> getMonthUserSales(int userid){
        MonthUserSalesAsync task=new MonthUserSalesAsync(janDao,userid);
        task.janRepo=this;
        task.execute();
        return monthsale;
    }


    private static class insertAsyncTask extends AsyncTask<JanEntity,Void,Void>{
        private JanDao janDao;

        public insertAsyncTask(JanDao janDao) {
            this.janDao = janDao;
        }

        @Override
        protected Void doInBackground(JanEntity... janEntities) {
            janDao.insert(janEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<JanEntity,Void,Void>{
        private JanDao janDao;

        public updateAsyncTask(JanDao janDao) {
            this.janDao = janDao;
        }

        @Override
        protected Void doInBackground(JanEntity... janEntities) {
            janDao.update(janEntities[0]);
            return null;
        }
    }
    private static class MonthUserSalesAsync extends AsyncTask<Void,Void,LiveData<JanEntity>>{
        private JanDao janDao;
        private JanRepo janRepo=null;
        private int userid;

        public MonthUserSalesAsync(JanDao janDao, int userid) {
            this.janDao = janDao;
            this.userid = userid;
        }

        @Override
        protected LiveData<JanEntity> doInBackground(Void... voids) {
            return janDao.getMonthUserSales(userid);
        }

        @Override
        protected void onPostExecute(LiveData<JanEntity> janEntityLiveData) {
            super.onPostExecute(janEntityLiveData);
            janRepo.monthsale=janEntityLiveData;
        }
    }

}
