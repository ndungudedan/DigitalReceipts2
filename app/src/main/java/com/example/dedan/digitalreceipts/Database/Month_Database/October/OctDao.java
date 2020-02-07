package com.example.dedan.digitalreceipts.Database.Month_Database.October;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface OctDao extends BaseDao<OctEntity> {
    @Query("select * from October where FOREIGN_KEY= :userid")
    OctEntity getMonthUserSales(String userid);

    @Query("delete from October")
    void deleteAll();

    @Query("select * from October")
    LiveData<List<OctEntity>> AllOctEvents();
}
