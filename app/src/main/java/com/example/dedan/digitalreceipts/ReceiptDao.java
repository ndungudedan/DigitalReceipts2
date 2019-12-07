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

    @Query("select * from Receipts order by ID asc ")
    LiveData<List<ReceiptEntity>> getAllReceipts();
}
