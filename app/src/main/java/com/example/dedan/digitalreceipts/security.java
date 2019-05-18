package com.example.dedan.digitalreceipts;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class security extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TableLayout tableLayout;
    LinearLayout linearLayout;
    SqlOpenHelper sqlOpenHelper;
    EditText txt_user;
    EditText txt_pass;
    TextView txt;
    String d;
    LinearLayout.LayoutParams tableRowParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        String user=sharedPreferences.getString("current_user","");
        sqlOpenHelper = new SqlOpenHelper(getApplicationContext());
        if(user.startsWith("ADMIN")){
            setContentView(R.layout.admin);
            txt_user=findViewById(R.id.admin_user);
            txt_pass=findViewById(R.id.admin_pass);
            linearLayout=findViewById(R.id.linear);
            tableLayout=new TableLayout(this);
            tableRowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            log_info();
        }else {
            setContentView(R.layout.activity_security);
            txt_user = findViewById(R.id.user_txt);
            txt_pass = findViewById(R.id.pass_txt);
            //txt = findViewById(R.id.log_txt);

            SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
            String[] columns = {SqlOpenHelper.KEY_ID, SqlOpenHelper.KEY_NAME,SqlOpenHelper.KEY_PASS,SqlOpenHelper.KEY_EMAIL,SqlOpenHelper.KEY_LOG};
            Cursor cursor = db.query(SqlOpenHelper.TABLE_USER, columns, null, null,
                    null, null, null);

            int IdPos = cursor.getColumnIndex(SqlOpenHelper.KEY_ID);
            int namePos = cursor.getColumnIndex(SqlOpenHelper.KEY_NAME);
            int passPos = cursor.getColumnIndex(SqlOpenHelper.KEY_PASS);
            int emailPos = cursor.getColumnIndex(SqlOpenHelper.KEY_EMAIL);
            int logPos = cursor.getColumnIndex(SqlOpenHelper.KEY_LOG);
            //refresh views here so that they can load again
            while (cursor.moveToNext()) {
                String n = cursor.getString(namePos);
                String p=cursor.getString(passPos);
                String c = cursor.getString(emailPos);
                String l = cursor.getString(logPos);
                if(n.equals(user)){
                    d=cursor.getString(IdPos);
                    txt_user.setText(n);
                    txt_pass.setText(p);
                    txt.setText(l);
                    break;
                }
            }
            cursor.close();
        }
    }
    public void log_info(){
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper.KEY_ID, SqlOpenHelper.KEY_NAME,SqlOpenHelper.KEY_PASS,SqlOpenHelper.KEY_EMAIL,SqlOpenHelper.KEY_LOG};
        Cursor cursor = db.query(SqlOpenHelper.TABLE_USER, columns, null, null,
                null, null, null);

        int IdPos = cursor.getColumnIndex(SqlOpenHelper.KEY_ID);
        int namePos = cursor.getColumnIndex(SqlOpenHelper.KEY_NAME);
        int passPos = cursor.getColumnIndex(SqlOpenHelper.KEY_PASS);
        int emailPos = cursor.getColumnIndex(SqlOpenHelper.KEY_EMAIL);
        int logPos = cursor.getColumnIndex(SqlOpenHelper.KEY_LOG);
        //refresh views here so that they can load again
        while (cursor.moveToNext()) {
            String n = cursor.getString(namePos);
            String c = cursor.getString(emailPos);
            String l = cursor.getString(logPos);
            if(n.startsWith("ADMIN")){
                d=cursor.getString(IdPos);
                String p=cursor.getString(passPos);
                txt_user.setText(n);
                txt_pass.setText(p);
            }
            dynamicTable(n,c,l);
        }
        cursor.close();
        linearLayout.addView(tableLayout);

    }
    public void dynamicTable(String name,String email,String log){
        /* create a table row */
    }
    public void update(){
        String select=SqlOpenHelper.KEY_ID+"=?";
        String[]selectArgs={d};
        ContentValues values=new ContentValues();
        values.put(SqlOpenHelper.KEY_NAME,txt_user.getText().toString());
        values.put(SqlOpenHelper.KEY_PASS,txt_pass.getText().toString());
        SQLiteDatabase db=sqlOpenHelper.getWritableDatabase();
        db.update(SqlOpenHelper.TABLE_USER,values,select,selectArgs);
    }

    public void change(View view) {
        update();
    }
    public void delete(String id){
        String select=SqlOpenHelper.KEY_ID+"=?";
        String[]selectArgs={id};
        SQLiteDatabase db=sqlOpenHelper.getWritableDatabase();
        db.delete(SqlOpenHelper.TABLE_USER,select,selectArgs);

    }
}
