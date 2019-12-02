package com.example.dedan.digitalreceipts;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WeekSalesDao {
    @Insert
    void insert(WeekSalesEntity weekSalesEntity);

    @Update
    void update(WeekSalesEntity weekSalesEntity);

    @Delete
    void delete(WeekSalesEntity weekSalesEntity);

    @Query("select * from Week_Sales")
    LiveData<List<WeekSalesEntity>> getWeekSales();
}
