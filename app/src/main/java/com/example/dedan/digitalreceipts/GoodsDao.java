package com.example.dedan.digitalreceipts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface GoodsDao {
    @Insert
    void insert(GoodsEntity goodsEntity);

    @Update
    void update(GoodsEntity goodsEntity);

    @Delete
    void delete(GoodsEntity goodsEntity);

    @Query("select * from Goods")
    LiveData<List<GoodsEntity>> getAllGoods();

    @Query("select * from Goods ")
    List<GoodsEntity> getItems();

    @Query("select * from Goods ")
    List<GoodsEntity> getPack();

    @Query("select * from Goods ")
    List<GoodsEntity> getCost();

}