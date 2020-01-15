package com.example.dedan.digitalreceipts.Database.Today_Database;

import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface TodayDao extends BaseDao<TodayEntity> {
    @Query("select * from Today")
    LiveData<List<TodayEntity>> AllEvents();

    @Query("select * from Today where FOREIGN_KEY= :userid")
    TodayEntity getTodayUserSales(String userid);

    @Query("delete from Today")
    void deleteAll();
}
