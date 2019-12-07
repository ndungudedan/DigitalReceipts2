package com.example.dedan.digitalreceipts.Month_Database.April;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.AppDatabase;
import com.example.dedan.digitalreceipts.Month_Database.January.JanDao;
import com.example.dedan.digitalreceipts.Month_Database.January.JanEntity;

public class AprRepo {
    private AprDao aprDao;

    public AprRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        aprDao=appDatabase.aprDao();
    }
    public void insert(AprEntity aprEntity){
        new insertAsyncTask(aprDao).execute(aprEntity);
    }
    public void update(AprEntity aprEntity){
        new updateAsyncTask(aprDao).execute(aprEntity);
    }

    private static class insertAsyncTask extends AsyncTask<AprEntity,Void,Void> {
        private AprDao aprDao;

        public insertAsyncTask(AprDao aprDao) {
            this.aprDao = aprDao;
        }

        @Override
        protected Void doInBackground(AprEntity... aprEntities) {
            aprDao.insert(aprEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<AprEntity,Void,Void>{
        private AprDao aprDao;

        public updateAsyncTask(AprDao aprDao) {
            this.aprDao = aprDao;
        }

        @Override
        protected Void doInBackground(AprEntity... aprEntities) {
            aprDao.update(aprEntities[0]);
            return null;
        }
}
}
