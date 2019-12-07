package com.example.dedan.digitalreceipts;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MonthSalesDao {
    @Insert
    void insert(MonthSalesEntity monthSalesEntity);

    @Update
    void update(MonthSalesEntity monthSalesEntity );

    @Delete
    void delete(MonthSalesEntity monthSalesEntity);

    @Query("select * from MonthSalesEntity")
    LiveData<List<MonthSalesEntity>> getMonthSales();

    /*@Query("UPDATE MonthSalesEntity SET order_price=:price WHERE order_id = :id")
    void update(Float price, int id);*/
}