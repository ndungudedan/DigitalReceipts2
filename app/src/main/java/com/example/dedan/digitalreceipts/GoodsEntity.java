package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Goods")
public class GoodsEntity {

    @ColumnInfo(name="ID")
    @PrimaryKey(autoGenerate = true)
    private int KEY_GOODS_ID;
    @ColumnInfo(name="Items")
    private String KEY_ITEM;
    @ColumnInfo(name="Pack")
    private String KEY_PACK;
    @ColumnInfo(name="Cost")
    private int KEY_COST ;

    public GoodsEntity(String KEY_ITEM, String KEY_PACK, int KEY_COST) {
        this.KEY_ITEM = KEY_ITEM;
        this.KEY_PACK = KEY_PACK;
        this.KEY_COST = KEY_COST;
    }

    public int getKEY_GOODS_ID() {
        return KEY_GOODS_ID;
    }

    public void setKEY_GOODS_ID(int KEY_GOODS_ID) {
        this.KEY_GOODS_ID = KEY_GOODS_ID;
    }

    public String getKEY_ITEM() {
        return KEY_ITEM;
    }


    public void setKEY_ITEM(String KEY_ITEM) {
        this.KEY_ITEM = KEY_ITEM;
    }

    public String getKEY_PACK() {
        return KEY_PACK;
    }

    public void setKEY_PACK(String KEY_PACK) {
        this.KEY_PACK = KEY_PACK;
    }

    public int getKEY_COST() {
        return KEY_COST;
    }

    public void setKEY_COST(int KEY_COST) {
        this.KEY_COST = KEY_COST;
    }
}
