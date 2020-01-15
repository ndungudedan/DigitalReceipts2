package com.example.dedan.digitalreceipts.Database.Week_Database.Friday;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface FriDao extends BaseDao<FriEntity> {
    @Query("select * from Friday")
    LiveData<List<FriEntity>> AllEvents();

    @Query("select * from Friday where FOREIGN_KEY= :userid")
    FriEntity getDayUserSales(String userid);

    @Query("delete from Friday")
    void deleteAll();

    }
