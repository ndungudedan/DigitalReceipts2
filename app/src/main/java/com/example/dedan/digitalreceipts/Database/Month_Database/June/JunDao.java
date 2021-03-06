package com.example.dedan.digitalreceipts.Database.Month_Database.June;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface JunDao extends BaseDao<JunEntity> {
    @Query("select * from June where FOREIGN_KEY= :userid")
    JunEntity getMonthUserSales(String userid);

    @Query("delete from June")
    void deleteAll();

    @Query("select * from June")
    LiveData<List<JunEntity>> AllJunEvents();
}
