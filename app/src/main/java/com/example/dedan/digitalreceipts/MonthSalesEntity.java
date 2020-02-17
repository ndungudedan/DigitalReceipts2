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
    @ColumnInfo(name = "session")
    private String KEY_session;
    @ColumnInfo(name = "clients")
    private int KEY_clients;
    @ColumnInfo(name = "total")
    private int KEY_total;

    public MonthSalesEntity(String KEY_time, String KEY_session, int KEY_clients, int KEY_total) {
        this.KEY_time = KEY_time;
        this.KEY_session = KEY_session;
        this.KEY_clients = KEY_clients;
        this.KEY_total = KEY_total;
    }

    public int getKEY_clients() {
        return KEY_clients;
    }

    public void setKEY_clients(int KEY_clients) {
        this.KEY_clients = KEY_clients;
    }

    public String getKEY_session() {
        return KEY_session;
    }

    public void setKEY_session(String KEY_session) {
        this.KEY_session = KEY_session;
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
