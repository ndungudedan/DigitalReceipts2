package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Daily_Sales")
public class DailySalesEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_ID;
    @ColumnInfo(name = "date")
    private String KEY_date;
    @ColumnInfo(name = "total")
    private int KEY_total;

    public DailySalesEntity() {
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public String getKEY_date() {
        return KEY_date;
    }

    public void setKEY_date(String KEY_date) {
        this.KEY_date = KEY_date;
    }

    public int getKEY_total() {
        return KEY_total;
    }

    public void setKEY_total(int KEY_total) {
        this.KEY_total = KEY_total;
    }
}
