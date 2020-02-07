package com.example.dedan.digitalreceipts.Database.Month_Database.November;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface NovDao extends BaseDao<NovEntity> {
    @Query("select * from November where FOREIGN_KEY= :userid")
    NovEntity getMonthUserSales(String userid);

    @Query("delete from November")
    void deleteAll();

    @Query("select * from November")
    LiveData<List<NovEntity>> AllNovEvents();
}
