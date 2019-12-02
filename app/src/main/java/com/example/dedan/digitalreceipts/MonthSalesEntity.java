package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MonthSalesEntity")
public class MonthSalesEntity {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    private int KEY_ID;
    @ColumnInfo(name = "Jan")
    private int KEY_Jan;
    @ColumnInfo(name = "Feb")
    private int KEY_Feb;
    @ColumnInfo(name = "March")
    private int KEY_March;
    @ColumnInfo(name = "Apr")
    private int KEY_Apr;
    @ColumnInfo(name = "May")
    private int KEY_May;
    @ColumnInfo(name = "June")
    private int KEY_June;
    @ColumnInfo(name = "July")
    private int KEY_July;
    @ColumnInfo(name = "Aug")
    private int KEY_Aug;
    @ColumnInfo(name = "Sep")
    private int KEY_Sep;
    @ColumnInfo(name = "Oct")
    private int KEY_Oct;
    @ColumnInfo(name = "Nov")
    private int KEY_Nov;
    @ColumnInfo(name = "Dec")
    private int KEY_Dec;
    @ColumnInfo(name = "date")
    private String KEY_date;
    @ColumnInfo(name = "total")
    private int KEY_total;

    public MonthSalesEntity(int KEY_Jan, int KEY_Feb, int KEY_March, int KEY_Apr, int KEY_May,
                            int KEY_June, int KEY_July, int KEY_Aug, int KEY_Sep, int KEY_Oct,
                            int KEY_Nov, int KEY_Dec, String KEY_date, int KEY_total) {
        this.KEY_Jan = KEY_Jan;
        this.KEY_Feb = KEY_Feb;
        this.KEY_March = KEY_March;
        this.KEY_Apr = KEY_Apr;
        this.KEY_May = KEY_May;
        this.KEY_June = KEY_June;
        this.KEY_July = KEY_July;
        this.KEY_Aug = KEY_Aug;
        this.KEY_Sep = KEY_Sep;
        this.KEY_Oct = KEY_Oct;
        this.KEY_Nov = KEY_Nov;
        this.KEY_Dec = KEY_Dec;
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

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public int getKEY_Jan() {
        return KEY_Jan;
    }

    public void setKEY_Jan(int KEY_Jan) {
        this.KEY_Jan = KEY_Jan;
    }

    public int getKEY_Feb() {
        return KEY_Feb;
    }

    public void setKEY_Feb(int KEY_Feb) {
        this.KEY_Feb = KEY_Feb;
    }

    public int getKEY_March() {
        return KEY_March;
    }

    public void setKEY_March(int KEY_March) {
        this.KEY_March = KEY_March;
    }

    public int getKEY_Apr() {
        return KEY_Apr;
    }

    public void setKEY_Apr(int KEY_Apr) {
        this.KEY_Apr = KEY_Apr;
    }

    public int getKEY_May() {
        return KEY_May;
    }

    public void setKEY_May(int KEY_May) {
        this.KEY_May = KEY_May;
    }

    public int getKEY_June() {
        return KEY_June;
    }

    public void setKEY_June(int KEY_June) {
        this.KEY_June = KEY_June;
    }

    public int getKEY_July() {
        return KEY_July;
    }

    public void setKEY_July(int KEY_July) {
        this.KEY_July = KEY_July;
    }

    public int getKEY_Aug() {
        return KEY_Aug;
    }

    public void setKEY_Aug(int KEY_Aug) {
        this.KEY_Aug = KEY_Aug;
    }

    public int getKEY_Sep() {
        return KEY_Sep;
    }

    public void setKEY_Sep(int KEY_Sep) {
        this.KEY_Sep = KEY_Sep;
    }

    public int getKEY_Oct() {
        return KEY_Oct;
    }

    public void setKEY_Oct(int KEY_Oct) {
        this.KEY_Oct = KEY_Oct;
    }

    public int getKEY_Nov() {
        return KEY_Nov;
    }

    public void setKEY_Nov(int KEY_Nov) {
        this.KEY_Nov = KEY_Nov;
    }

    public int getKEY_Dec() {
        return KEY_Dec;
    }

    public void setKEY_Dec(int KEY_Dec) {
        this.KEY_Dec = KEY_Dec;
    }
}
