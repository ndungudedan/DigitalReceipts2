package com.example.dedan.digitalreceipts.Database.Month_Database.February;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface FebDao extends BaseDao<FebEntity> {
    @Query("select * from February")
    LiveData<List<FebEntity>> AllFebEvents();

    @Query("select * from February where FOREIGN_KEY= :userid")
    FebEntity getMonthUserSales(String userid);

    @Query("delete from February")
    void deleteAll();
}
