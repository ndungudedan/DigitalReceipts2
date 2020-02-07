package com.example.dedan.digitalreceipts.Database.Month_Database.May;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface MayDao extends BaseDao<MayEntity> {
    @Query("select * from May where FOREIGN_KEY= :userid")
    MayEntity getMonthUserSales(String userid);

    @Query("delete from May")
    void deleteAll();

    @Query("select * from May")
    LiveData<List<MayEntity>> AllMayEvents();
}
