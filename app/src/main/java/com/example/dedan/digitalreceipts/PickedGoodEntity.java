package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "picked_Goods")
public class PickedGoodEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_ID;
    @ColumnInfo(name = "quantity")
    private int KEY_Quantity;
    @ColumnInfo(name = "pack")
    private String KEY_Pack;
    @ColumnInfo(name = "Item")
    private String KEY_Item;
    @ColumnInfo(name = "Cost")
    private int KEY_Cost;
    @ColumnInfo(name = "total")
    private int KEY_total;
    @ColumnInfo(name = "foreign_id")
    private int KEY_picked_id;


    public PickedGoodEntity(int KEY_Quantity, String KEY_Pack, String KEY_Item, int KEY_Cost, int KEY_total, int KEY_picked_id) {
        this.KEY_Quantity = KEY_Quantity;
        this.KEY_Pack = KEY_Pack;
        this.KEY_Item = KEY_Item;
        this.KEY_Cost = KEY_Cost;
        this.KEY_picked_id=KEY_picked_id;
        this.KEY_total=KEY_total;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public int getKEY_Quantity() {
        return KEY_Quantity;
    }

    public void setKEY_Quantity(int KEY_Quantity) {
        this.KEY_Quantity = KEY_Quantity;
    }

    public String getKEY_Pack() {
        return KEY_Pack;
    }

    public void setKEY_Pack(String KEY_Pack) {
        this.KEY_Pack = KEY_Pack;
    }

    public String getKEY_Item() {
        return KEY_Item;
    }

    public void setKEY_Item(String KEY_Item) {
        this.KEY_Item = KEY_Item;
    }

    public int getKEY_Cost() {
        return KEY_Cost;
    }

    public void setKEY_Cost(int KEY_Cost) {
        this.KEY_Cost = KEY_Cost;
    }

    public int getKEY_total() {
        return KEY_total;
    }

    public void setKEY_total(int KEY_total) {
        this.KEY_total = KEY_total;
    }

    public int getKEY_picked_id() {
        return KEY_picked_id;
    }

    public void setKEY_picked_id(int KEY_picked_id) {
        this.KEY_picked_id = KEY_picked_id;
    }
}
