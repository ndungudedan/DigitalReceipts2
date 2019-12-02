package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Week_Sales")
public class WeekSalesEntity {
    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int KEY_WEEK_ID;
    @ColumnInfo(name = "Mon")
    private int KEY_WEEK_Mon;
    @ColumnInfo(name = "Tue")
    private int KEY_WEEK_Tue;
    @ColumnInfo(name = "Wed")
    private int KEY_WEEK_Wed;
    @ColumnInfo(name = "Thur")
    private int KEY_WEEK_Thur;
    @ColumnInfo(name = "Fri")
    private int KEY_WEEK_Fri;
    @ColumnInfo(name = "Sat")
    private int KEY_WEEK_Sat;
    @ColumnInfo(name = "Sun")
    private int KEY_WEEK_Sun;
    @ColumnInfo(name = "date")
    private String KEY_date;
    @ColumnInfo(name = "total")
    private int KEY_total;

    public WeekSalesEntity(int KEY_WEEK_Mon, int KEY_WEEK_Tue, int KEY_WEEK_Wed,
                           int KEY_WEEK_Thur, int KEY_WEEK_Fri, int KEY_WEEK_Sat, int KEY_WEEK_Sun, String KEY_date, int KEY_total) {
        this.KEY_WEEK_Mon = KEY_WEEK_Mon;
        this.KEY_WEEK_Tue = KEY_WEEK_Tue;
        this.KEY_WEEK_Wed = KEY_WEEK_Wed;
        this.KEY_WEEK_Thur = KEY_WEEK_Thur;
        this.KEY_WEEK_Fri = KEY_WEEK_Fri;
        this.KEY_WEEK_Sat = KEY_WEEK_Sat;
        this.KEY_WEEK_Sun = KEY_WEEK_Sun;
        this.KEY_date = KEY_date;
        this.KEY_total = KEY_total;
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

    public int getKEY_WEEK_ID() {
        return KEY_WEEK_ID;
    }

    public void setKEY_WEEK_ID(int KEY_WEEK_ID) {
        this.KEY_WEEK_ID = KEY_WEEK_ID;
    }

    public int getKEY_WEEK_Mon() {
        return KEY_WEEK_Mon;
    }

    public void setKEY_WEEK_Mon(int KEY_WEEK_Mon) {
        this.KEY_WEEK_Mon = KEY_WEEK_Mon;
    }

    public int getKEY_WEEK_Tue() {
        return KEY_WEEK_Tue;
    }

    public void setKEY_WEEK_Tue(int KEY_WEEK_Tue) {
        this.KEY_WEEK_Tue = KEY_WEEK_Tue;
    }

    public int getKEY_WEEK_Wed() {
        return KEY_WEEK_Wed;
    }

    public void setKEY_WEEK_Wed(int KEY_WEEK_Wed) {
        this.KEY_WEEK_Wed = KEY_WEEK_Wed;
    }

    public int getKEY_WEEK_Thur() {
        return KEY_WEEK_Thur;
    }

    public void setKEY_WEEK_Thur(int KEY_WEEK_Thur) {
        this.KEY_WEEK_Thur = KEY_WEEK_Thur;
    }

    public int getKEY_WEEK_Fri() {
        return KEY_WEEK_Fri;
    }

    public void setKEY_WEEK_Fri(int KEY_WEEK_Fri) {
        this.KEY_WEEK_Fri = KEY_WEEK_Fri;
    }

    public int getKEY_WEEK_Sat() {
        return KEY_WEEK_Sat;
    }

    public void setKEY_WEEK_Sat(int KEY_WEEK_Sat) {
        this.KEY_WEEK_Sat = KEY_WEEK_Sat;
    }

    public int getKEY_WEEK_Sun() {
        return KEY_WEEK_Sun;
    }

    public void setKEY_WEEK_Sun(int KEY_WEEK_Sun) {
        this.KEY_WEEK_Sun = KEY_WEEK_Sun;
    }
}
