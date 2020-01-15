package com.example.dedan.digitalreceipts.Database.Month_Database.July;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface JulDao extends BaseDao<JulEntity> {
    @Query("select * from July where FOREIGN_KEY= :userid")
    JulEntity getMonthUserSales(String userid);

    @Query("delete from July")
    void deleteAll();
}
