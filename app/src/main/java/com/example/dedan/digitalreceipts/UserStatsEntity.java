package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserStatistics")
public class UserStatsEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_ID;
    @ColumnInfo(name = "TODAY")
    private String KEY_TODAY;
    @ColumnInfo(name = "TODAY_SALES")
    private int KEY_TODAY_SALES;
    @ColumnInfo(name = "WEEK")
    private String KEY_WEEK;
    @ColumnInfo(name = "WEEK_SALES")
    private int KEY_WEEK_SALES;
    @ColumnInfo(name = "MONTH")
    private String KEY_MONTH;
    @ColumnInfo(name = "MONTH_SALES")
    private int KEY_MONTH_SALES;
    @ColumnInfo(name = "CLIENTS_MONTH_SERVED")
    private int KEY_M_CLIENTS_SERVED;
    @ColumnInfo(name = "CLIENTS_WEEK_SERVED")
    private int KEY_W_CLIENTS_SERVED;
    @ColumnInfo(name = "USER_ID")
    private int KEY_USERID;

    public UserStatsEntity(String KEY_TODAY, int KEY_TODAY_SALES, String KEY_WEEK, int KEY_WEEK_SALES,
                           String KEY_MONTH, int KEY_MONTH_SALES, int KEY_M_CLIENTS_SERVED, int KEY_W_CLIENTS_SERVED, int KEY_USERID) {
        this.KEY_TODAY = KEY_TODAY;
        this.KEY_TODAY_SALES = KEY_TODAY_SALES;
        this.KEY_WEEK = KEY_WEEK;
        this.KEY_WEEK_SALES = KEY_WEEK_SALES;
        this.KEY_MONTH = KEY_MONTH;
        this.KEY_MONTH_SALES = KEY_MONTH_SALES;
        this.KEY_M_CLIENTS_SERVED = KEY_M_CLIENTS_SERVED;
        this.KEY_W_CLIENTS_SERVED = KEY_W_CLIENTS_SERVED;
        this.KEY_USERID = KEY_USERID;
    }

    public int getKEY_M_CLIENTS_SERVED() {
        return KEY_M_CLIENTS_SERVED;
    }

    public void setKEY_M_CLIENTS_SERVED(int KEY_M_CLIENTS_SERVED) {
        this.KEY_M_CLIENTS_SERVED = KEY_M_CLIENTS_SERVED;
    }

    public int getKEY_W_CLIENTS_SERVED() {
        return KEY_W_CLIENTS_SERVED;
    }

    public void setKEY_W_CLIENTS_SERVED(int KEY_W_CLIENTS_SERVED) {
        this.KEY_W_CLIENTS_SERVED = KEY_W_CLIENTS_SERVED;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public String getKEY_TODAY() {
        return KEY_TODAY;
    }

    public void setKEY_TODAY(String KEY_TODAY) {
        this.KEY_TODAY = KEY_TODAY;
    }

    public int getKEY_TODAY_SALES() {
        return KEY_TODAY_SALES;
    }

    public void setKEY_TODAY_SALES(int KEY_TODAY_SALES) {
        this.KEY_TODAY_SALES = KEY_TODAY_SALES;
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

    public String getKEY_MONTH() {
        return KEY_MONTH;
    }

    public void setKEY_MONTH(String KEY_MONTH) {
        this.KEY_MONTH = KEY_MONTH;
    }

    public int getKEY_MONTH_SALES() {
        return KEY_MONTH_SALES;
    }

    public void setKEY_MONTH_SALES(int KEY_MONTH_SALES) {
        this.KEY_MONTH_SALES = KEY_MONTH_SALES;
    }

    public int getKEY_USERID() {
        return KEY_USERID;
    }

    public void setKEY_USERID(int KEY_USERID) {
        this.KEY_USERID = KEY_USERID;
    }
}
