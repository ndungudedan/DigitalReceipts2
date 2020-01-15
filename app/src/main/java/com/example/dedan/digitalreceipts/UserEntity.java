package com.example.dedan.digitalreceipts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users")
public class UserEntity {

    @ColumnInfo(name="ID")
    @PrimaryKey(autoGenerate = true)
    private  int KEY_USER_ID;
    @ColumnInfo(name = "first_name")
    private  String KEY_FIRSTNAME;
    @ColumnInfo(name="sec_name")
    private  String KEY_SECNAME;
    @ColumnInfo(name="empNO")
    private  int KEY_empNO;
    @ColumnInfo(name = "dob")
    private  String KEY_DOB ;
    @ColumnInfo(name="residence")
    private  String KEY_residence;
    @ColumnInfo(name="mobileNo")
    private  String KEY_MOBILENO;
    @ColumnInfo(name="username")
    private  String KEY_NAME;
    @ColumnInfo(name="email")
    private  String KEY_EMAIL;
    @ColumnInfo(name="password")
    private  String KEY_PASS;
    @ColumnInfo(name="national_ID")
    private  String KEY_NATNLID;
    @ColumnInfo(name="last_log")
    private  String KEY_LOG;
    @ColumnInfo(name="accessbtn")
    private  String KEY_ACCESS;
    @ColumnInfo(name="profilepic")
    private  String KEY_PIC;


    public UserEntity(String KEY_FIRSTNAME, String KEY_SECNAME, int KEY_empNO, String KEY_DOB, String KEY_residence, String KEY_MOBILENO, String KEY_NAME,
                      String KEY_EMAIL, String KEY_PASS, String KEY_NATNLID, String KEY_LOG, String KEY_ACCESS, String KEY_PIC) {
        this.KEY_FIRSTNAME = KEY_FIRSTNAME;
        this.KEY_SECNAME = KEY_SECNAME;
        this.KEY_empNO = KEY_empNO;
        this.KEY_DOB = KEY_DOB;
        this.KEY_residence = KEY_residence;
        this.KEY_MOBILENO = KEY_MOBILENO;
        this.KEY_NAME = KEY_NAME;
        this.KEY_EMAIL = KEY_EMAIL;
        this.KEY_PASS = KEY_PASS;
        this.KEY_NATNLID = KEY_NATNLID;
        this.KEY_LOG = KEY_LOG;
        this.KEY_ACCESS = KEY_ACCESS;
        this.KEY_PIC = KEY_PIC;
    }

    public String getKEY_PIC() {
        return KEY_PIC;
    }

    public void setKEY_PIC(String KEY_PIC) {
        this.KEY_PIC = KEY_PIC;
    }

    public int getKEY_USER_ID() {
        return KEY_USER_ID;
    }

    public void setKEY_USER_ID(int KEY_USER_ID) {
        this.KEY_USER_ID = KEY_USER_ID;
    }

    public String getKEY_FIRSTNAME() {
        return KEY_FIRSTNAME;
    }

    public void setKEY_FIRSTNAME(String KEY_FIRSTNAME) {
        this.KEY_FIRSTNAME = KEY_FIRSTNAME;
    }

    public String getKEY_SECNAME() {
        return KEY_SECNAME;
    }

    public void setKEY_SECNAME(String KEY_SECNAME) {
        this.KEY_SECNAME = KEY_SECNAME;
    }

    public int getKEY_empNO() {
        return KEY_empNO;
    }

    public void setKEY_empNO(int KEY_empNO) {
        this.KEY_empNO = KEY_empNO;
    }

    public String getKEY_DOB() {
        return KEY_DOB;
    }

    public void setKEY_DOB(String KEY_DOB) {
        this.KEY_DOB = KEY_DOB;
    }

    public String getKEY_residence() {
        return KEY_residence;
    }

    public void setKEY_residence(String KEY_residence) {
        this.KEY_residence = KEY_residence;
    }

    public String getKEY_MOBILENO() {
        return KEY_MOBILENO;
    }

    public void setKEY_MOBILENO(String KEY_MOBILENO) {
        this.KEY_MOBILENO = KEY_MOBILENO;
    }

    public String getKEY_NAME() {
        return KEY_NAME;
    }

    public void setKEY_NAME(String KEY_NAME) {
        this.KEY_NAME = KEY_NAME;
    }

    public String getKEY_EMAIL() {
        return KEY_EMAIL;
    }

    public void setKEY_EMAIL(String KEY_EMAIL) {
        this.KEY_EMAIL = KEY_EMAIL;
    }

    public String getKEY_PASS() {
        return KEY_PASS;
    }

    public void setKEY_PASS(String KEY_PASS) {
        this.KEY_PASS = KEY_PASS;
    }

    public String getKEY_NATNLID() {
        return KEY_NATNLID;
    }

    public void setKEY_NATNLID(String KEY_NATNLID) {
        this.KEY_NATNLID = KEY_NATNLID;
    }

    public String getKEY_LOG() {
        return KEY_LOG;
    }

    public void setKEY_LOG(String KEY_LOG) {
        this.KEY_LOG = KEY_LOG;
    }

    public String getKEY_ACCESS() {
        return KEY_ACCESS;
    }

    public void setKEY_ACCESS(String KEY_ACCESS) {
        this.KEY_ACCESS = KEY_ACCESS;
    }
}
