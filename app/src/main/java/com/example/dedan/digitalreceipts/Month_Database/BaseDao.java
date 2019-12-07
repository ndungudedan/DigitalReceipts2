package com.example.dedan.digitalreceipts.Month_Database;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

public interface BaseDao<T> {
    @Insert
    void  insert(T object);
    @Update
    void update(T object);

}
