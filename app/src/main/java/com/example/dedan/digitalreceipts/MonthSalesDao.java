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

    @Query("select * from MonthSalesEntity where Time= :time")
    MonthSalesEntity getSale(String time);

    @Query("select * from MonthSalesEntity where session= :ssn")
    LiveData<List<MonthSalesEntity>> getWeekSales(String ssn);

    @Query("select * from MonthSalesEntity where session= :ssn")
    LiveData<List<MonthSalesEntity>> getMonthSales(String ssn);

    @Query("delete from MonthSalesEntity")
    void deleteAll();

}
