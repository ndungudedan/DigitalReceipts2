package com.example.dedan.digitalreceipts.Database.Month_Database.January;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface JanDao extends BaseDao<JanEntity> {

    @Query("select * from January")
    LiveData<List<JanEntity>> allJanEvents();

    @Query("select * from January where FOREIGN_KEY= :userid")
    JanEntity getMonthUserSales(String userid);

    @Query("delete from January")
    void deleteAll();
}
