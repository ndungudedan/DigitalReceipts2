package com.example.dedan.digitalreceipts.Database.Store;

import com.example.dedan.digitalreceipts.Database.Month_Database.BaseDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface CategoryDao extends BaseDao<CategoryEntity> {
    @Query("select * from Category")
    LiveData<List<CategoryEntity>> getAllCategories();

    @Query("delete from Category")
    void deleteAll();

}
