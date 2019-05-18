package com.example.dedan.digitalreceipts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;

import static android.provider.BaseColumns._ID;

public class SqlOpenHelper extends SQLiteOpenHelper {
    public static final String TAG =SQLiteOpenHelper.class.getSimpleName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DigitalReceipts";
    public static final String TABLE_USER = "user";
    public static final String TABLE_ITEM_DETAILS="item_details";

    public static final String KEY_ID = "id";
    public static final String KEY_FIRSTNAME = "first_name";
    public static final String KEY_SECNAME = "sec_name";
    public static final String KEY_empNO = "empNO";
    public static final String KEY_DOB = "dob";
    public static final String KEY_residence = "residence";
    public static final String KEY_MOBILENO = "mobileNo";
    public static final String KEY_NAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASS = "password";
    public static final String KEY_LOG = "last_log";

    public static final String KEY_ITEM_ID="id";
    public static final String KEY_ITEM="Items";
    public static final String KEY_PACK ="Pack" ;
    public static final String KEY_COST ="Cost" ;
   public static String _ID=BaseColumns._ID;


    public SqlOpenHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LOGIN_TABLE="CREATE TABLE IF NOT EXISTS  "+TABLE_USER+"("+
                KEY_ID+" INTEGER PRIMARY KEY,"+KEY_FIRSTNAME+" TEXT NOT NULL,"+KEY_SECNAME+" TEXT NOT NULL,"+KEY_empNO+" VARCHAR,"
                +KEY_DOB+" VARCHAR," +KEY_residence+" VARCHAR,"+KEY_MOBILENO+" VARCHAR ,"
                +KEY_NAME+" TEXT NOT NULL,"+KEY_PASS+" VARCHAR NOT NULL,"+
                KEY_EMAIL+" TEXT NOT NULL,"+KEY_LOG+" VARCHAR"+")";

        String CREATE_ITEM_DETAILS_TABLE="CREATE TABLE IF NOT EXISTS  "+TABLE_ITEM_DETAILS+"("+_ID+" INTEGER PRIMARY KEY,"
                +KEY_ITEM_ID+" INTEGER UNIQUE,"+KEY_ITEM+" TEXT NOT NULL,"+KEY_PACK+" VARCHAR,"+
                KEY_COST+" INTEGER "+")";

        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
        sqLiteDatabase.execSQL(CREATE_ITEM_DETAILS_TABLE);

        Log.d(TAG, "Database tables created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_DETAILS);

        // Create tables again
        onCreate(sqLiteDatabase);

    }
    public void add_item_to_database(String item,String pack, String cost){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item);
        values.put(KEY_PACK, pack);
        values.put(KEY_COST, cost);

        long id = db.insert(TABLE_ITEM_DETAILS, null, values);
        db.close(); // Closing database connection

        Log.i("item_table", "New table created into sqlite: " + id);
    }
    /**
     * Storing user details in database
     * */
    public void addUser(String userfname,String userscName,String userempNo,String userDOB,String userresid,String usermobile,
            String name, String password,String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
            c = db.rawQuery("SELECT * FROM " +TABLE_USER, null);

        ContentValues values = new ContentValues();
        if (c == null || c.getCount() == 0){
            values.put(KEY_NAME, "ADMIN_"+name); // Name
            values.put(KEY_EMAIL, email); // Email
            values.put(KEY_PASS, password); // Password
            values.put(KEY_LOG,"WELCOME");
        }
        else{
            values.put(KEY_NAME, name); // Name
            values.put(KEY_EMAIL, email); // Email
            values.put(KEY_PASS, password);// Email
            values.put(KEY_LOG,"WELCOME");
        }
        values.put(KEY_FIRSTNAME,userfname);
        values.put(KEY_SECNAME,userscName);
        values.put(KEY_empNO,userempNo);
        values.put(KEY_DOB,userDOB);
        values.put(KEY_residence,userresid);
        values.put(KEY_MOBILENO,usermobile);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
           // user.put("email", cursor.getString(2));
            user.put("password", cursor.getString(2));
            user.put("created_at", cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();
        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
