package com.example.dedan.digitalreceipts.Database.Month_Database.April;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface AprDao extends BaseDao<AprEntity> {
    @Query("select * from April")
    LiveData<List<AprEntity>> AllAprEvents();

    @Query("select * from April where FOREIGN_KEY= :userid")
    AprEntity getMonthUserSales(String userid);

    @Query("delete from April")
    void deleteAll();
}
