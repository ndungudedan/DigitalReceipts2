package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Week_Sales")
public class WeekSalesEntity {
    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int KEY_WEEK_ID;
    @ColumnInfo(name = "Week")
    private String KEY_week;
    @ColumnInfo(name = "total")
    private int KEY_total;

    public WeekSalesEntity(String KEY_week, int KEY_total) {
        this.KEY_week = KEY_week;
        this.KEY_total = KEY_total;
    }

    public int getKEY_WEEK_ID() {
        return KEY_WEEK_ID;
    }

    public void setKEY_WEEK_ID(int KEY_WEEK_ID) {
        this.KEY_WEEK_ID = KEY_WEEK_ID;
    }

    public String getKEY_week() {
        return KEY_week;
    }

    public void setKEY_week(String KEY_week) {
        this.KEY_week = KEY_week;
    }

    public int getKEY_total() {
        return KEY_total;
    }

    public void setKEY_total(int KEY_total) {
        this.KEY_total = KEY_total;
    }
}
