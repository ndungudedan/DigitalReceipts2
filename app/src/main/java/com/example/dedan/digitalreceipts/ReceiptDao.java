package com.example.dedan.digitalreceipts;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ReceiptDao {
    @Insert
    void insert(ReceiptEntity receiptEntity);

    @Update
    void update(ReceiptEntity receiptEntity);

    @Delete
    void delete(ReceiptEntity receiptEntity);

    @Query("select * from Receipts where type='sale' order by ID asc ")
    LiveData<List<ReceiptEntity>> getAllReceipts();
    @Query("select * from Receipts where type='report' order by ID asc ")
    LiveData<List<ReceiptEntity>> getAllReports();

    @Query("select * from Receipts where time !=:h order by ID asc ")
    LiveData<List<ReceiptEntity>> getTodayReceipts(String h);
}
