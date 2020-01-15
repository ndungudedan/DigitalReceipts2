package com.example.dedan.digitalreceipts.Database.Week_Database.Saturday;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface SatDao extends BaseDao<SatEntity> {
    @Query("select * from Saturday")
    LiveData<List<SatEntity>> AllEvents();

    @Query("select * from Saturday where FOREIGN_KEY= :userid")
    SatEntity getDayUserSales(String userid);

    @Query("delete from Saturday")
    void deleteAll();

}

