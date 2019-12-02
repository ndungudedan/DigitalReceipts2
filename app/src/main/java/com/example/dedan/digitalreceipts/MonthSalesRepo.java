package com.example.dedan.digitalreceipts;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MonthSalesRepo {
    private MonthSalesDao monthSalesDao;
    private LiveData<List<MonthSalesEntity>> AllMonthSales;

    public MonthSalesRepo(Application application) {
        AppDatabase database=AppDatabase.getInstance(application);
        monthSalesDao=database.monthSalesDao();
        AllMonthSales=monthSalesDao.getMonthSales();

    }
    public void insert(MonthSalesEntity monthSalesEntity){

    }
    public void update(MonthSalesEntity monthSalesEntity){

    }
    public void delete(MonthSalesEntity monthSalesEntity){

    }
    public LiveData<List<MonthSalesEntity>> getMonthSales(){
        return AllMonthSales;
    }

    class insertAsync extends AsyncTask<MonthSalesEntity,Void,Void>{
        private MonthSalesDao monthSalesDao;

        public insertAsync(MonthSalesDao monthSalesDao) {
            this.monthSalesDao = monthSalesDao;
        }

        @Override
        protected Void doInBackground(MonthSalesEntity... monthSalesEntities) {
            return null;
        }
    }
    class updateAsync extends AsyncTask<MonthSalesEntity,Void,Void>{
        private MonthSalesDao monthSalesDao;

        public updateAsync(MonthSalesDao monthSalesDao) {
            this.monthSalesDao = monthSalesDao;
        }

        @Override
        protected Void doInBackground(MonthSalesEntity... monthSalesEntities) {
            return null;
        }
    }

}
