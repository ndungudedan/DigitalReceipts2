package com.example.dedan.digitalreceipts.Database.Month_Database.December;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface DecDao extends BaseDao<DecEntity> {

    @Query("select * from December where FOREIGN_KEY= :userid")
    LiveData<DecEntity> getMonthUserSales(int userid);

    @Query("select * from December where FOREIGN_KEY= :userid")
    DecEntity getMonthUserSales(String userid);

    @Query("delete from December")
    void deleteAll();

    @Query("select * from December")
    LiveData<List<DecEntity>> AllDecEvents();
}
