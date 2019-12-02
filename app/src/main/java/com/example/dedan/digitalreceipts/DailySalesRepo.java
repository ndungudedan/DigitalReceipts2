package com.example.dedan.digitalreceipts;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DailySalesRepo {
    private DailySalesDao dailySalesDao;
    private LiveData<List<DailySalesEntity>> allDailySales;

    public DailySalesRepo(Application application) {
        AppDatabase database=AppDatabase.getInstance(application);
        dailySalesDao=database.dailySalesDao();
        allDailySales=dailySalesDao.getDailySales();
    }
    public void insert(DailySalesEntity dailySalesEntity){

    }
    public void update(DailySalesEntity dailySalesEntity){

    }
    public void delete(DailySalesEntity dailySalesEntity){

    }
    public LiveData<List<DailySalesEntity>> getDailySales(){
        return allDailySales;
    }

    class insertDaily extends AsyncTask<DailySalesEntity,Void,Void>{
        private DailySalesDao dailySalesDao;

        public insertDaily(DailySalesDao dailySalesDao) {
            this.dailySalesDao = dailySalesDao;
        }

        @Override
        protected Void doInBackground(DailySalesEntity... dailySalesEntities) {
            return null;
        }
    }
    class updateDaily extends AsyncTask<DailySalesEntity,Void,Void>{
        private DailySalesDao dailySalesDao;

        public updateDaily(DailySalesDao dailySalesDao) {
            this.dailySalesDao = dailySalesDao;
        }

        @Override
        protected Void doInBackground(DailySalesEntity... dailySalesEntities) {
            return null;
        }
    }
}
