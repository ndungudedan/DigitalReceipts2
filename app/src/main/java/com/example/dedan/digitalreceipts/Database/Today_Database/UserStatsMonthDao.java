package com.example.dedan.digitalreceipts.Database.Today_Database;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface UserStatsMonthDao extends BaseDao<UserStatsMonthEntity> {
    @Query("select * from UserStatsMonth")
    LiveData<List<UserStatsMonthEntity>> AllEvents();

    @Query("delete from UserStatsMonth")
    void deleteAll();
}
