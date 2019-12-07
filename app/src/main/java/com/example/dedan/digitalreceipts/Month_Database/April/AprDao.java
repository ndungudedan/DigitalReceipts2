package com.example.dedan.digitalreceipts.Month_Database.April;

import com.example.dedan.digitalreceipts.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AprDao extends BaseDao<AprEntity> {
    @Query("select * from April")
    LiveData<List<AprEntity>> AllAprEvents();
}
