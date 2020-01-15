package com.example.dedan.digitalreceipts.Database.Week_Database.Monday;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface MonDao extends BaseDao<MonEntity> {
    @Query("select * from Monday")
    LiveData<List<MonEntity>> AllEvents();

    @Query("select * from Monday where FOREIGN_KEY= :userid")
    MonEntity getDayUserSales(String userid);

    @Query("delete from Monday")
    void deleteAll();
}

