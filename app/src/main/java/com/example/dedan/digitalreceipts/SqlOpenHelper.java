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

public class SqlOpenHelper extends SQLiteOpenHelper {
    public static final String TAG =SQLiteOpenHelper.class.getSimpleName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DigitalReceipts";
    public static final String TABLE_USER = "user";
    public static final String TABLE_ITEM_DETAILS="item_details";
    public static final String TABLE_SALES="sales";
    public static final String TABLE_COMPANY_ANALYSIS="company_analysis";

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
    public static final String KEY_NATNLID="national_ID";
    public static final String KEY_LOG = "last_log";
    public static final String KEY_ACCESS="access";

    public static final String KEY_ITEM_ID="id";
    public static final String KEY_ITEM="Items";
    public static final String KEY_PACK ="Pack" ;
    public static final String KEY_COST ="Cost" ;

    public static final String KEY_TODAY="today";
    public static final String KEY_YESTERDAY="yesterday";
    public static final String KEY_WEEK="week";
    public static final String KEY_LAST_WEEK="last_week";
    public static final String KEY_MONTH="month";
    public static final String KEY_LAST_MONTH="last_month";
    public static final String KEY_TALLY="sales_count";
    public static final String KEY_LAST_TALLY="last_tally";
    public static final String KEY_EMP_FOREIGN="empNo_foreignKey";


    public static final String KEY_TODAY_COMPANY="today";
    public static final String KEY_YESTERDAY_COMPANY="yesterday";
    public static final String KEY_WEEK_COMPANY="week";
    public static final String KEY_LAST_WEEK_COMPANY="last_week";
    public static final String KEY_MONTH_COMPANY="month";
    public static final String KEY_LAST_MONTH_COMPANY="last_month";
    public static final String KEY_TALLY_COMPANY="sales_count";
    public static final String KEY_LAST_TALLY_COMPANY="last_tally";

   public static String _ID=BaseColumns._ID;
    public static String _USERID=BaseColumns._ID;

    public SqlOpenHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LOGIN_TABLE="CREATE TABLE IF NOT EXISTS  "+TABLE_USER+"("+_USERID+" INTEGER PRIMARY KEY,"
                +KEY_ID+" INTEGER UNIQUE,"+KEY_FIRSTNAME+" TEXT NOT NULL,"+KEY_SECNAME+" TEXT NOT NULL,"+KEY_empNO+" INTEGER,"+KEY_NATNLID+" INTEGER,"
                +KEY_DOB+" VARCHAR," +KEY_residence+" VARCHAR,"+KEY_MOBILENO+" VARCHAR ,"
                +KEY_NAME+" TEXT NOT NULL,"+KEY_PASS+" VARCHAR NOT NULL,"+
                KEY_EMAIL+" TEXT NOT NULL,"+KEY_LOG+" VARCHAR,"+KEY_ACCESS+" TEXT "+")";

        String CREATE_ITEM_DETAILS_TABLE="CREATE TABLE IF NOT EXISTS  "+TABLE_ITEM_DETAILS+"("+_ID+" INTEGER PRIMARY KEY,"
                +KEY_ITEM_ID+" INTEGER UNIQUE,"+KEY_ITEM+" TEXT NOT NULL,"+KEY_PACK+" VARCHAR,"+
                KEY_COST+" INTEGER "+")";

        String CREATE_STATS_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_SALES+"("+KEY_EMP_FOREIGN+" INTEGER, " +KEY_TODAY+" INTEGER , "+KEY_YESTERDAY+" INTEGER ,"
                +KEY_WEEK+" INTEGER ,"+KEY_LAST_WEEK+" INTEGER ,"+KEY_MONTH+" INTEGER , "+KEY_LAST_MONTH+" INTEGER ,"+KEY_TALLY+" INTEGER, "+KEY_LAST_TALLY+" INTEGER "+")";

        String CREATE_COMPANY_ANALYTICS_TABLE="CREATE TABLE IF NOT EXISTS  "+TABLE_COMPANY_ANALYSIS+"("+_ID+" INTEGER PRIMARY KEY,"
                +KEY_TODAY_COMPANY+" INTEGER ,"+KEY_YESTERDAY_COMPANY+" INTEGER ,"+KEY_WEEK_COMPANY+" INTEGER ,"+KEY_LAST_WEEK_COMPANY+" INTEGER ,"
                +KEY_MONTH_COMPANY+" INTEGER ," +KEY_LAST_MONTH_COMPANY+" INTEGER ,"+ KEY_TALLY_COMPANY+" INTEGER ,"+KEY_LAST_TALLY_COMPANY+" INTEGER "+")";

        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
        sqLiteDatabase.execSQL(CREATE_ITEM_DETAILS_TABLE);
        sqLiteDatabase.execSQL(CREATE_STATS_TABLE);
        sqLiteDatabase.execSQL(CREATE_COMPANY_ANALYTICS_TABLE);

        Log.d(TAG, "Database tables created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_DETAILS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY_ANALYSIS);

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
    public void addUser(String userfname,String userscName,String userIDNo,String userDOB,String userresid,String usermobile,
            String name, String password,String email,int empNO) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
            c = db.rawQuery("SELECT * FROM " +TABLE_USER, null);

        ContentValues values = new ContentValues();
        if (c == null || c.getCount() == 0){
            values.put(KEY_NAME, "ADMIN_"+name); // Name
            values.put(KEY_EMAIL, email); // Email
            values.put(KEY_PASS, password); // Password
            values.put(KEY_LOG,"WELCOME");
            values.put(KEY_ACCESS,"ACCESS_GRANTED");
        }
        else{
            values.put(KEY_NAME, name); // Name
            values.put(KEY_EMAIL, email); // Email
            values.put(KEY_PASS, password);// Email
            values.put(KEY_LOG,"WELCOME");
            values.put(KEY_ACCESS,"ACCESS_DENIED");
        }
        values.put(KEY_FIRSTNAME,userfname);
        values.put(KEY_SECNAME,userscName);
        values.put(KEY_NATNLID,userIDNo);
        values.put(KEY_DOB,userDOB);
        values.put(KEY_residence,userresid);
        values.put(KEY_MOBILENO,usermobile);
        values.put(KEY_empNO,empNO);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        statsFirstInsert(db,empNO);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }
    public void statsFirstInsert(SQLiteDatabase db,int empNO){
        //sales table
        ContentValues values=new ContentValues();
        values.put(KEY_TODAY,"0");
        values.put(KEY_TALLY,"0");
        values.put(KEY_WEEK,"0");
        values.put(KEY_MONTH,"0");
        values.put(KEY_YESTERDAY,"0");
        values.put(KEY_EMP_FOREIGN,empNO);
        db.insert(TABLE_SALES,null,values);

        //company table
    ContentValues comp_values=new ContentValues();
    comp_values.put(KEY_TODAY_COMPANY,"0");
    comp_values.put(KEY_TALLY_COMPANY,"0");
    comp_values.put(KEY_WEEK_COMPANY,"0");
    comp_values.put(KEY_MONTH_COMPANY,"0");
    comp_values.put(KEY_YESTERDAY_COMPANY,"0");
    comp_values.put(KEY_LAST_WEEK_COMPANY,"0");
    comp_values.put(KEY_LAST_TALLY_COMPANY,"0");
    comp_values.put(KEY_LAST_MONTH_COMPANY,"0");
    db.insert(TABLE_COMPANY_ANALYSIS,null,comp_values);
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
