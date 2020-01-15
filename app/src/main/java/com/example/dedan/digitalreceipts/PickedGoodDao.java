package com.example.dedan.digitalreceipts;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PickedGoodDao {

    @Insert
     void insert(PickedGoodEntity pickedGoodEntity);

    @Update
    int update(PickedGoodEntity pickedGoodEntity);

    @Delete
    void delete(PickedGoodEntity pickedGoodEntity);

    @Query("select * from picked_Goods order by ID desc")
    LiveData<List<PickedGoodEntity>> getAllLivePickedGoods();

    @Query("select * from picked_Goods order by quantity desc")
    List<PickedGoodEntity> getPickedGoodsList();

    @Query("delete from PICKED_GOODS")
    void deleteAll();

   // @Query("UPDATE SET COLUMN_TITLE=:text WHERE id=:id")
   // void UpdateColumnById (String text, int id);

}
