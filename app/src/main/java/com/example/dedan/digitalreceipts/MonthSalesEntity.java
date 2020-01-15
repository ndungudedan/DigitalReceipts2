package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MonthSalesEntity")
public class MonthSalesEntity {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    private int KEY_ID;
    @ColumnInfo(name = "Time")
    private String KEY_time;
    @ColumnInfo(name = "total")
    private int KEY_total;

    public MonthSalesEntity(String KEY_time, int KEY_total) {
        this.KEY_time = KEY_time;
        this.KEY_total = KEY_total;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public String getKEY_time() {
        return KEY_time;
    }

    public void setKEY_time(String KEY_time) {
        this.KEY_time = KEY_time;
    }

    public int getKEY_total() {
        return KEY_total;
    }

    public void setKEY_total(int KEY_total) {
        this.KEY_total = KEY_total;
    }
}
