package com.example.dedan.digitalreceipts.Database.Week_Database.Sunday;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface SunDao extends BaseDao<SunEntity> {
    @Query("select * from Sunday")
    LiveData<List<SunEntity>> AllEvents();

    @Query("select * from Sunday where FOREIGN_KEY= :userid")
    SunEntity getDayUserSales(String userid);

    @Query("delete from Sunday")
    void deleteAll();
}

