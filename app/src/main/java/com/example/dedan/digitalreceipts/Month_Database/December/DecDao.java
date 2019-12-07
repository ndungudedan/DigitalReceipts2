package com.example.dedan.digitalreceipts.Month_Database.December;

import com.example.dedan.digitalreceipts.Month_Database.BaseDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface DecDao extends BaseDao<DecEntity> {

    @Query("select * from December where FOREIGN_KEY= :userid")
    LiveData<DecEntity> getMonthUserSales(int userid);
}
