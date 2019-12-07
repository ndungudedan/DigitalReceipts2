package com.example.dedan.digitalreceipts;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ReceiptViewModel extends AndroidViewModel {
    private ReceiptsRepo receiptsRepo;
    private LiveData<List<ReceiptEntity>> allReceipts;

    public ReceiptViewModel(@NonNull Application application) {
        super(application);
        receiptsRepo =new ReceiptsRepo(application);
        allReceipts= receiptsRepo.getAllReceipts();
    }
    public void insert(ReceiptEntity receiptEntity){
        receiptsRepo.insert(receiptEntity);
    }
    public void update(ReceiptEntity receiptEntity){
        receiptsRepo.update(receiptEntity);
    }
    public void delete(ReceiptEntity receiptEntity){

    }
    public LiveData<List<ReceiptEntity>> getAllReceipts(){
        return allReceipts;
    }
}
