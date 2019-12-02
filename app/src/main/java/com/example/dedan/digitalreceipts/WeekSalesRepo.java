package com.example.dedan.digitalreceipts;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class WeekSalesRepo {
    private WeekSalesDao weekSalesDao;
    LiveData<List<WeekSalesEntity>> allWeekSales;

    public WeekSalesRepo(Application application) {
        AppDatabase appDatabase=AppDatabase.getInstance(application);
        weekSalesDao=appDatabase.weekSalesDao();
        allWeekSales=weekSalesDao.getWeekSales();
    }
    public void insert(WeekSalesEntity weekSalesEntity){

    }
    public void update(WeekSalesEntity weekSalesEntity){

    }
    public void delete(WeekSalesEntity weekSalesEntity){

    }
    public LiveData<List<WeekSalesEntity>> getweeksales(){
        return allWeekSales;
    }

    class insertWeek extends AsyncTask<WeekSalesEntity,Void,Void>{
        private WeekSalesDao weekSalesDao;

        public insertWeek(WeekSalesDao weekSalesDao) {
            this.weekSalesDao = weekSalesDao;
        }

        @Override
        protected Void doInBackground(WeekSalesEntity... weekSalesEntities) {
            return null;
        }
    }
    class updateWeek extends AsyncTask<WeekSalesEntity,Void,Void>{
        private WeekSalesDao weekSalesDao;
        @Override
        protected Void doInBackground(WeekSalesEntity... weekSalesEntities) {
            return null;
        }
    }
}
