package com.example.dedan.digitalreceipts.Database.Month_Database.October;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface OctDao extends BaseDao<OctEntity> {
    @Query("select * from October where FOREIGN_KEY= :userid")
    OctEntity getMonthUserSales(String userid);

    @Query("delete from October")
    void deleteAll();
}
