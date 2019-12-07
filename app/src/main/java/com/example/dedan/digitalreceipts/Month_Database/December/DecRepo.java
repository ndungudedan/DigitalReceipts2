package com.example.dedan.digitalreceipts.Month_Database.December;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.AppDatabase;
import com.example.dedan.digitalreceipts.UserEntity;

import androidx.lifecycle.LiveData;

public class DecRepo {
    private DecDao decDao;
    private LiveData<DecEntity> monthsale;
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

    public LiveData<DecEntity> getMonthUserSales(int userid){
            MonthUserSalesAsync task=new MonthUserSalesAsync(decDao,userid);
            task.decRepo=this;
            task.execute();
        return monthsale;
    }

    private static class MonthUserSalesAsync extends AsyncTask<Void,Void,LiveData<DecEntity>>{
        private DecDao decDao;
        private DecRepo decRepo=null;
        private int userid;

        private MonthUserSalesAsync(DecDao decDao, int userid) {
            this.decDao = decDao;
            this.userid = userid;
        }
        @Override
        protected LiveData<DecEntity> doInBackground(Void... voids) {
            LiveData<DecEntity> gg=decDao.getMonthUserSales(userid);
            return gg;
        }

        @Override
        protected void onPostExecute(LiveData<DecEntity> decEntityLiveData) {
            super.onPostExecute(decEntityLiveData);
            LiveData<DecEntity> ff=decEntityLiveData;
            decRepo.monthsale=ff;
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
