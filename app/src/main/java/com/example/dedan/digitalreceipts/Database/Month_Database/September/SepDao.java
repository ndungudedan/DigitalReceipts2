package com.example.dedan.digitalreceipts.Database.Month_Database.September;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface SepDao extends BaseDao<SepEntity> {
    @Query("select * from September where FOREIGN_KEY= :userid")
    SepEntity getMonthUserSales(String userid);

    @Query("delete from September")
    void deleteAll();

    @Query("select * from September")
    LiveData<List<SepEntity>> AllSepEvents();
}
