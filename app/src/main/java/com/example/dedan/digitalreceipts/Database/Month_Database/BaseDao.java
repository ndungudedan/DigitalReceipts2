package com.example.dedan.digitalreceipts.Database.Month_Database;

import androidx.room.Insert;
import androidx.room.Update;

public interface BaseDao<T> {
    @Insert
    void  insert(T object);
    @Update
    void update(T object);

}
