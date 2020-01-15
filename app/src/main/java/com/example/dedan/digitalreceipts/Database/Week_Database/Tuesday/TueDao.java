package com.example.dedan.digitalreceipts.Database.Week_Database.Tuesday;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface TueDao extends BaseDao<TueEntity> {
    @Query("select * from Tuesday")
    LiveData<List<TueEntity>> AllEvents();

    @Query("select * from Tuesday where FOREIGN_KEY= :userid")
    TueEntity getDayUserSales(String userid);

    @Query("delete from Tuesday")
    void deleteAll();
}

