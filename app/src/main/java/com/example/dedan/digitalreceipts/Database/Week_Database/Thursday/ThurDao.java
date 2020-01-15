package com.example.dedan.digitalreceipts.Database.Week_Database.Thursday;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface ThurDao extends BaseDao<ThurEntity> {
    @Query("select * from Thursday")
    LiveData<List<ThurEntity>> AllEvents();

    @Query("select * from Thursday where FOREIGN_KEY= :userid")
    ThurEntity getDayUserSales(String userid);

    @Query("delete from Thursday")
    void deleteAll();

}

