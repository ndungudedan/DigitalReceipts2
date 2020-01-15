package com.example.dedan.digitalreceipts;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dedan.digitalreceipts.Database.AppDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CustomerRepo {
    CustomerDao customerDao;
    LiveData<List<CustomerEntity>> AllCustomers;

    public CustomerRepo(Application application) {
        AppDatabase appDatabase= AppDatabase.getInstance(application);
        customerDao=appDatabase.customerDao();
        AllCustomers =customerDao.getAllCustomers();
    }

    public void insert(CustomerEntity customerEntity){
        new insertasync(customerDao).execute(customerEntity);
    }

    public void update(CustomerEntity customerEntity){
        new updateAsync(customerDao).execute(customerEntity);
    }

    public void delete(CustomerEntity customerEntity){
        new deleteAsync(customerDao).execute(customerEntity);
    }

    public LiveData<List<CustomerEntity>> getAllCustomers(){
        return AllCustomers;
    }

    public void deleteAll(){
        new deleteAllAsync(customerDao).execute();
    }
    private static class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private CustomerDao dao;
        public deleteAllAsync(CustomerDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    public static class insertasync extends AsyncTask<CustomerEntity,Void,Void>{
        private CustomerDao customerDao;

        public insertasync(CustomerDao customerDao) {
            this.customerDao = customerDao;
        }

        @Override
        protected Void doInBackground(CustomerEntity... customerEntities) {
            customerDao.insert(customerEntities[0]);
            return null;
        }
    }
    public static class updateAsync extends AsyncTask<CustomerEntity,Void,Void>{
        private CustomerDao customerDao;

        public updateAsync(CustomerDao customerDao) {
            this.customerDao = customerDao;
        }

        @Override
        protected Void doInBackground(CustomerEntity... customerEntities) {
            customerDao.update(customerEntities[0]);
            return null;
        }
    }
    public class deleteAsync extends AsyncTask<CustomerEntity,Void,Void>{
        private CustomerDao customerDao;

        public deleteAsync(CustomerDao customerDao) {
            this.customerDao = customerDao;
        }

        @Override
        protected Void doInBackground(CustomerEntity... customerEntities) {
            customerDao.delete(customerEntities[0]);
            return null;
        }

    }

}
