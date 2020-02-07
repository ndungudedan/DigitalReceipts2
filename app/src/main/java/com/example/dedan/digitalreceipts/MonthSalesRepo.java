package com.example.dedan.digitalreceipts;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;
import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayDao;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayRepo;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class MonthSalesRepo {
    private MonthSalesDao monthSalesDao;
    private LiveData<List<MonthSalesEntity>> AllMonthSales;
    private MonthSalesEntity totalSale;
    private LiveData<List<MonthSalesEntity>> weekSales;
    private LiveData<List<MonthSalesEntity>> monthSales;

    public MonthSalesRepo(Application application) {
        AppDatabase database=AppDatabase.getInstance(application);
        monthSalesDao=database.monthSalesDao();
        AllMonthSales=monthSalesDao.getMonthSales();
    }
    public void insert(MonthSalesEntity monthSalesEntity){
        new insertAsync(monthSalesDao).execute(monthSalesEntity);
    }
    public void update(MonthSalesEntity monthSalesEntity){
        new updateAsync(monthSalesDao).execute(monthSalesEntity);
    }
    public LiveData<List<MonthSalesEntity>> getMonthSales(){
        return AllMonthSales;
    }
    public MonthSalesEntity getTotalSale(String time){
        MonthTotalSalesAsync task=new MonthTotalSalesAsync(monthSalesDao);
        task.Repo=this;
        try {
            totalSale=task.execute(time).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return totalSale;
    }
    public LiveData<List<MonthSalesEntity>> getWeekSale(String ssn){
        WeekSalesAsync task=new WeekSalesAsync(monthSalesDao);
        task.Repo=this;
        try {
            weekSales=task.execute(ssn).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return weekSales;
    }
    public LiveData<List<MonthSalesEntity>> getMonthSale(String ssn){
        MonthSalesAsync task=new MonthSalesAsync(monthSalesDao);
        task.Repo=this;
        try {
            monthSales=task.execute(ssn).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return monthSales;
    }
    public void deleteAll(){
        new deleteAllAsync(monthSalesDao).execute();
    }


    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private MonthSalesDao dao;
        public deleteAllAsync(MonthSalesDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class MonthTotalSalesAsync extends AsyncTask<String,Void,MonthSalesEntity> {
        private MonthSalesDao dao;
        private MonthSalesRepo Repo = null;

        public MonthTotalSalesAsync(MonthSalesDao dao) {
            this.dao = dao;
        }

        @Override
        protected MonthSalesEntity doInBackground(String... strings) {
            return dao.getSale(strings[0]);
        }
    }
    private static class WeekSalesAsync extends AsyncTask<String,Void,LiveData<List<MonthSalesEntity>>> {
        private MonthSalesDao dao;
        private MonthSalesRepo Repo = null;

        public WeekSalesAsync(MonthSalesDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<MonthSalesEntity>> doInBackground(String... strings) {
            return dao.getWeekSales(strings[0]);
        }
    }
    private static class MonthSalesAsync extends AsyncTask<String,Void,LiveData<List<MonthSalesEntity>>> {
        private MonthSalesDao dao;
        private MonthSalesRepo Repo = null;

        public MonthSalesAsync(MonthSalesDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<MonthSalesEntity>> doInBackground(String... strings) {
            return dao.getMonthSales(strings[0]);
        }
    }


    private static class insertAsync extends AsyncTask<MonthSalesEntity,Void,Void>{
        private MonthSalesDao monthSalesDao;

        public insertAsync(MonthSalesDao monthSalesDao) {
            this.monthSalesDao = monthSalesDao;
        }

        @Override
        protected Void doInBackground(MonthSalesEntity... monthSalesEntities) {
            monthSalesDao.insert(monthSalesEntities[0]);
            return null;
        }
    }
    private static class updateAsync extends AsyncTask<MonthSalesEntity,Void,Void>{
        private MonthSalesDao monthSalesDao;

        public updateAsync(MonthSalesDao monthSalesDao) {
            this.monthSalesDao = monthSalesDao;
        }

        @Override
        protected Void doInBackground(MonthSalesEntity... monthSalesEntities) {
            monthSalesDao.update(monthSalesEntities[0]);
            return null;
        }
    }

}
