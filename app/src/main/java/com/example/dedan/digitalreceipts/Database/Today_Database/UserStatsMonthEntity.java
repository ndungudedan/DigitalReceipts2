package com.example.dedan.digitalreceipts.Database.Today_Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserStatsMonth")
public class UserStatsMonthEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_ID;
    @ColumnInfo(name = "MONTH")
    private String KEY_MONTH;
    @ColumnInfo(name = "MONTH_SALES")
    private int KEY_MONTH_SALES;
    @ColumnInfo(name = "CLIENTS_MONTH_SERVED")
    private int KEY_M_CLIENTS_SERVED;
    @ColumnInfo(name = "USER_ID")
    private int KEY_USERID;

    public UserStatsMonthEntity(String KEY_MONTH, int KEY_MONTH_SALES, int KEY_M_CLIENTS_SERVED, int KEY_USERID) {
        this.KEY_MONTH = KEY_MONTH;
        this.KEY_MONTH_SALES = KEY_MONTH_SALES;
        this.KEY_M_CLIENTS_SERVED = KEY_M_CLIENTS_SERVED;
        this.KEY_USERID = KEY_USERID;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
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

    public int getKEY_M_CLIENTS_SERVED() {
        return KEY_M_CLIENTS_SERVED;
    }

    public void setKEY_M_CLIENTS_SERVED(int KEY_M_CLIENTS_SERVED) {
        this.KEY_M_CLIENTS_SERVED = KEY_M_CLIENTS_SERVED;
    }

    public int getKEY_USERID() {
        return KEY_USERID;
    }

    public void setKEY_USERID(int KEY_USERID) {
        this.KEY_USERID = KEY_USERID;
    }
}
