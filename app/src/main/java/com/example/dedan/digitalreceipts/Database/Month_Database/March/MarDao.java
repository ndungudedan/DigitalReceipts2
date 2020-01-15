package com.example.dedan.digitalreceipts.Database.Month_Database.March;

import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface MarDao extends BaseDao<MarEntity> {
    @Query("select * from March")
    LiveData<List<MarEntity>> AllMarEvents();

    @Query("select * from March where FOREIGN_KEY= :userid")
    MarEntity getMonthUserSales(String userid);

    @Query("delete from March")
    void deleteAll();
}
