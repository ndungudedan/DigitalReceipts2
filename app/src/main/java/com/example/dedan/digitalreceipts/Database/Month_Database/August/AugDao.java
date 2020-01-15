package com.example.dedan.digitalreceipts.Database.Month_Database.August;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface AugDao extends BaseDao<AugEntity> {
    @Query("select * from August")
    LiveData<List<AugEntity>> AllAugEvents();

    @Query("select * from August where FOREIGN_KEY= :userid")
    AugEntity getMonthUserSales(String userid);

    @Query("delete from August")
    void deleteAll();
}
