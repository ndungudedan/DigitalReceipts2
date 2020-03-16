package com.example.dedan.digitalreceipts.Database.CompDetails;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;

import androidx.lifecycle.LiveData;

public class detailsRepository {
    private detailsDao dao;
    private LiveData<detailsEntity> allDetails;

    public detailsRepository(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        dao=appDatabase.detailsDao();
        allDetails=dao.companyDetails();
    }
    public void insert(detailsEntity detailsEntity){
        new insertAsyncTask(dao).execute(detailsEntity);
    }
    public void update(detailsEntity detailsEntity){
        new updateAsyncTask(dao).execute(detailsEntity);
    }
    public LiveData<detailsEntity> AllDetails(){
        return allDetails;
    }
    private static class insertAsyncTask extends AsyncTask<detailsEntity,Void,Void> {
        private detailsDao dao;

        public insertAsyncTask(detailsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(detailsEntity... detailsEntities) {
            dao.insert(detailsEntities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<detailsEntity,Void,Void>{
        private detailsDao dao;

        public updateAsyncTask(detailsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(detailsEntity... detailsEntities) {
            dao.update(detailsEntities[0]);
            return null;
        }
    }
}
