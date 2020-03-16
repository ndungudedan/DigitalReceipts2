package com.example.dedan.digitalreceipts.Database.CompDetails;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="Company_Details")
public class detailsEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int KEY_ID;
    @ColumnInfo(name = "Title")
    private String KEY_Title;
    @ColumnInfo(name = "Email")
    private String KEY_Email;
    @ColumnInfo(name = "Location")
    private String KEY_Location;
    @ColumnInfo(name = "Box")
    private String KEY_Box;
    @ColumnInfo(name = "Contact")
    private String KEY_Contact;
    @ColumnInfo(name = "Logo")
    private String KEY_Logo;
    @ColumnInfo(name = "FinancialMonthPos")
    private int KEY_StartMonthPos;
    @ColumnInfo(name = "FinancialMonth")
    private String KEY_StartMonth;

    public detailsEntity(String KEY_Title, String KEY_Email, String KEY_Location, String KEY_Box,
                         String KEY_Contact, String KEY_Logo, int KEY_StartMonthPos, String KEY_StartMonth) {
        this.KEY_Title = KEY_Title;
        this.KEY_Email = KEY_Email;
        this.KEY_Location = KEY_Location;
        this.KEY_Box = KEY_Box;
        this.KEY_Contact = KEY_Contact;
        this.KEY_Logo = KEY_Logo;
        this.KEY_StartMonthPos = KEY_StartMonthPos;
        this.KEY_StartMonth = KEY_StartMonth;
    }

    public String getKEY_StartMonth() {
        return KEY_StartMonth;
    }

    public void setKEY_StartMonth(String KEY_StartMonth) {
        this.KEY_StartMonth = KEY_StartMonth;
    }

    public String getKEY_Contact() {
        return KEY_Contact;
    }

    public void setKEY_Contact(String KEY_Contact) {
        this.KEY_Contact = KEY_Contact;
    }

    public int getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(int KEY_ID) {
        this.KEY_ID = KEY_ID;
    }

    public String getKEY_Title() {
        return KEY_Title;
    }

    public void setKEY_Title(String KEY_Title) {
        this.KEY_Title = KEY_Title;
    }

    public String getKEY_Email() {
        return KEY_Email;
    }

    public void setKEY_Email(String KEY_Email) {
        this.KEY_Email = KEY_Email;
    }

    public String getKEY_Location() {
        return KEY_Location;
    }

    public void setKEY_Location(String KEY_Location) {
        this.KEY_Location = KEY_Location;
    }

    public String getKEY_Box() {
        return KEY_Box;
    }

    public void setKEY_Box(String KEY_Box) {
        this.KEY_Box = KEY_Box;
    }

    public String getKEY_Logo() {
        return KEY_Logo;
    }

    public void setKEY_Logo(String KEY_Logo) {
        this.KEY_Logo = KEY_Logo;
    }

    public int getKEY_StartMonthPos() {
        return KEY_StartMonthPos;
    }

    public void setKEY_StartMonthPos(int KEY_StartMonthPos) {
        this.KEY_StartMonthPos = KEY_StartMonthPos;
    }
}
