package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Receipts")
public class ReceiptEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_ID;
    @ColumnInfo(name = "time")
    private String KEY_date;
    @ColumnInfo(name = "reference")
    private String KEY_rcpt_ref;
    @ColumnInfo(name = "servedby")
    private String KEY_servedby;
    @ColumnInfo(name = "customer")
    private String KEY_customer;

    public ReceiptEntity(String KEY_date, String KEY_rcpt_ref, String KEY_servedby, String KEY_customer) {
        this.KEY_date = KEY_date;
        this.KEY_rcpt_ref = KEY_rcpt_ref;
        this.KEY_servedby = KEY_servedby;
        this.KEY_customer = KEY_customer;
    }

    public String getKEY_servedby() {
        return KEY_servedby;
    }

    public void setKEY_servedby(String KEY_servedby) {
        this.KEY_servedby = KEY_servedby;
    }

    public String getKEY_customer() {
        return KEY_customer;
    }

    public void setKEY_customer(String KEY_customer) {
        this.KEY_customer = KEY_customer;
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

    public String getKEY_rcpt_ref() {
        return KEY_rcpt_ref;
    }

    public void setKEY_rcpt_ref(String KEY_rcpt_ref) {
        this.KEY_rcpt_ref = KEY_rcpt_ref;
    }
}
