package com.example.dedan.digitalreceipts;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DailySalesDao {
    @Insert
    void insert(DailySalesEntity dailySalesEntity);

    @Update
    void update(DailySalesEntity dailySalesEntity);

    @Delete
    void delete(DailySalesEntity dailySalesEntity);

    @Query("select * from Daily_Sales")
    LiveData<List<DailySalesEntity>> getDailySales();
}
