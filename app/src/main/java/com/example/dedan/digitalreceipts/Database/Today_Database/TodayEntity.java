package com.example.dedan.digitalreceipts.Database.Today_Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Today")
public class TodayEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_ID;
    @ColumnInfo(name = "SALES")
    private int KEY_SALES;
    @ColumnInfo(name = "No_of_Clients")
    private int KEY_NO_OF_CLIENTS;
    @ColumnInfo(name = "FOREIGN_KEY")
    private String KEY_FOREIGN_KEY;
    @ColumnInfo(name = "IDENTIFY")
    private String KEY_TYPE;
    @ColumnInfo(name = "DAY")
    private String KEY_DAY;

    public TodayEntity(int KEY_SALES, int KEY_NO_OF_CLIENTS, String KEY_FOREIGN_KEY, String KEY_TYPE, String KEY_DAY) {
        this.KEY_SALES = KEY_SALES;
        this.KEY_NO_OF_CLIENTS = KEY_NO_OF_CLIENTS;
        this.KEY_FOREIGN_KEY = KEY_FOREIGN_KEY;
        this.KEY_TYPE = KEY_TYPE;
        this.KEY_DAY = KEY_DAY;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public int getKEY_SALES() {
        return KEY_SALES;
    }

    public void setKEY_SALES(int KEY_SALES) {
        this.KEY_SALES = KEY_SALES;
    }

    public int getKEY_NO_OF_CLIENTS() {
        return KEY_NO_OF_CLIENTS;
    }

    public void setKEY_NO_OF_CLIENTS(int KEY_NO_OF_CLIENTS) {
        this.KEY_NO_OF_CLIENTS = KEY_NO_OF_CLIENTS;
    }

    public String getKEY_FOREIGN_KEY() {
        return KEY_FOREIGN_KEY;
    }

    public void setKEY_FOREIGN_KEY(String KEY_FOREIGN_KEY) {
        this.KEY_FOREIGN_KEY = KEY_FOREIGN_KEY;
    }

    public String getKEY_TYPE() {
        return KEY_TYPE;
    }

    public void setKEY_TYPE(String KEY_TYPE) {
        this.KEY_TYPE = KEY_TYPE;
    }

    public String getKEY_DAY() {
        return KEY_DAY;
    }

    public void setKEY_DAY(String KEY_DAY) {
        this.KEY_DAY = KEY_DAY;
    }
}
