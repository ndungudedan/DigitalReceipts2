package com.example.dedan.digitalreceipts;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ReceiptsRepo {
    private ReceiptDao receiptDao;
    private LiveData<List<ReceiptEntity>> allReceipts;

    public ReceiptsRepo(Application application) {
        AppDatabase database=AppDatabase.getInstance(application);
        receiptDao =database.dailySalesDao();
        allReceipts= receiptDao.getAllReceipts();
    }
    public void insert(ReceiptEntity receiptEntity){
        new insertReceipt(receiptDao).execute(receiptEntity);
    }
    public void update(ReceiptEntity receiptEntity){

    }
    public void delete(ReceiptEntity receiptEntity){

    }
    public LiveData<List<ReceiptEntity>> getAllReceipts(){
        return allReceipts;
    }

    class insertReceipt extends AsyncTask<ReceiptEntity,Void,Void>{
        private ReceiptDao receiptDao;

        public insertReceipt(ReceiptDao receiptDao) {
            this.receiptDao = receiptDao;
        }

        @Override
        protected Void doInBackground(ReceiptEntity... receiptEntities) {
            receiptDao.insert(receiptEntities[0]);
            return null;
        }
    }
    class updateDaily extends AsyncTask<ReceiptEntity,Void,Void>{
        private ReceiptDao receiptDao;

        public updateDaily(ReceiptDao receiptDao) {
            this.receiptDao = receiptDao;
        }

        @Override
        protected Void doInBackground(ReceiptEntity... dailySalesEntities) {
            return null;
        }
    }
}
