package com.example.dedan.digitalreceipts.Month_Database.January;

import com.example.dedan.digitalreceipts.Month_Database.BaseDao;
import com.example.dedan.digitalreceipts.Month_Database.December.DecEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface JanDao extends BaseDao<JanEntity> {

    @Query("select * from January")
    LiveData<List<JanEntity>> allJanEvents();

    @Query("select * from January where FOREIGN_KEY= :userid")
    LiveData<JanEntity> getMonthUserSales(int userid);

}
