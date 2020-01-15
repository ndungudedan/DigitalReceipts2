package com.example.dedan.digitalreceipts;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserStatsDao {
    @Insert
    void insert(UserStatsEntity userStatsEntity);

    @Update
    void update(UserStatsEntity userStatsEntity);

    @Query("select * from UserStatistics")
    LiveData<List<UserStatsEntity>> getAllUsersStats();

    @Query("delete from UserStatistics")
    void deleteAll();

}
