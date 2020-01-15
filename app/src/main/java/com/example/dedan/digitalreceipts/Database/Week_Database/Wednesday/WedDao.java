package com.example.dedan.digitalreceipts.Database.Week_Database.Wednesday;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface WedDao extends BaseDao<WedEntity> {
    @Query("select * from Wednesday")
    LiveData<List<WedEntity>> AllEvents();

    @Query("select * from Wednesday where FOREIGN_KEY= :userid")
    WedEntity getDayUserSales(String userid);

    @Query("delete from Wednesday")
    void deleteAll();
}

