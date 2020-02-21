package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserStatistics")
public class UserStatsEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_ID;
    @ColumnInfo(name = "DAY")
    private String KEY_DAY;
    @ColumnInfo(name = "WEEK")
    private String KEY_WEEK;
    @ColumnInfo(name = "WEEK_SALES")
    private int KEY_WEEK_SALES;
    @ColumnInfo(name = "CLIENTS_WEEK_SERVED")
    private int KEY_W_CLIENTS_SERVED;
    @ColumnInfo(name = "USER_ID")
    private int KEY_USERID;

    public UserStatsEntity(String KEY_DAY, String KEY_WEEK, int KEY_WEEK_SALES, int KEY_W_CLIENTS_SERVED, int KEY_USERID) {
        this.KEY_DAY = KEY_DAY;
        this.KEY_WEEK = KEY_WEEK;
        this.KEY_WEEK_SALES = KEY_WEEK_SALES;
        this.KEY_W_CLIENTS_SERVED = KEY_W_CLIENTS_SERVED;
        this.KEY_USERID = KEY_USERID;
    }

    public String getKEY_DAY() {
        return KEY_DAY;
    }

    public void setKEY_DAY(String KEY_DAY) {
        this.KEY_DAY = KEY_DAY;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public String getKEY_WEEK() {
        return KEY_WEEK;
    }

    public void setKEY_WEEK(String KEY_WEEK) {
        this.KEY_WEEK = KEY_WEEK;
    }

    public int getKEY_WEEK_SALES() {
        return KEY_WEEK_SALES;
    }

    public void setKEY_WEEK_SALES(int KEY_WEEK_SALES) {
        this.KEY_WEEK_SALES = KEY_WEEK_SALES;
    }

    public int getKEY_W_CLIENTS_SERVED() {
        return KEY_W_CLIENTS_SERVED;
    }

    public void setKEY_W_CLIENTS_SERVED(int KEY_W_CLIENTS_SERVED) {
        this.KEY_W_CLIENTS_SERVED = KEY_W_CLIENTS_SERVED;
    }

    public int getKEY_USERID() {
        return KEY_USERID;
    }

    public void setKEY_USERID(int KEY_USERID) {
        this.KEY_USERID = KEY_USERID;
    }
}
