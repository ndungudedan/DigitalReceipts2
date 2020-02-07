package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Customers")
public class CustomerEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_id;
    @ColumnInfo(name="first_name")
    private String KEY_first_name;
    @ColumnInfo(name="sec_name")
    private String KEY_sec_name;
    @ColumnInfo(name="phone")
    private String KEY_phone;
    @ColumnInfo(name="location")
    private String KEY_location;
    @ColumnInfo(name="address")
    private String KEY_address;
    @ColumnInfo(name="PO_BOX")
    private String KEY_po_box;
    @ColumnInfo(name="email")
    private String KEY_email;

    public CustomerEntity(String KEY_first_name, String KEY_sec_name, String KEY_phone, String KEY_location,
                          String KEY_address, String KEY_po_box, String KEY_email) {
        this.KEY_first_name = KEY_first_name;
        this.KEY_sec_name = KEY_sec_name;
        this.KEY_phone = KEY_phone;
        this.KEY_location = KEY_location;
        this.KEY_address = KEY_address;
        this.KEY_po_box = KEY_po_box;
        this.KEY_email = KEY_email;
    }

    public int getKEY_id() {
        return KEY_id;
    }

    public void setKEY_id(int KEY_id) {
        this.KEY_id = KEY_id;
    }

    public String getKEY_first_name() {
        return KEY_first_name;
    }

    public void setKEY_first_name(String KEY_first_name) {
        this.KEY_first_name = KEY_first_name;
    }

    public String getKEY_sec_name() {
        return KEY_sec_name;
    }

    public void setKEY_sec_name(String KEY_sec_name) {
        this.KEY_sec_name = KEY_sec_name;
    }

    public String getKEY_phone() {
        return KEY_phone;
    }

    public void setKEY_phone(String KEY_phone) {
        this.KEY_phone = KEY_phone;
    }

    public String getKEY_location() {
        return KEY_location;
    }

    public void setKEY_location(String KEY_location) {
        this.KEY_location = KEY_location;
    }

    public String getKEY_address() {
        return KEY_address;
    }

    public void setKEY_address(String KEY_address) {
        this.KEY_address = KEY_address;
    }

    public String getKEY_po_box() {
        return KEY_po_box;
    }

    public void setKEY_po_box(String KEY_po_box) {
        this.KEY_po_box = KEY_po_box;
    }

    public String getKEY_email() {
        return KEY_email;
    }

    public void setKEY_email(String KEY_email) {
        this.KEY_email = KEY_email;
    }
}
