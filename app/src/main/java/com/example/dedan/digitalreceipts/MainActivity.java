package com.example.dedan.digitalreceipts;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import static java.util.Calendar.PM;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView total,yesterday,thisweek,thismonth;
    String user;
    SqlOpenHelper sqlOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        total=(TextView)findViewById(R.id.textView6);
        yesterday=(TextView)findViewById(R.id.textView7);
        thisweek=(TextView)findViewById(R.id.textView8);
        thismonth=(TextView)findViewById(R.id.textView9);

        sqlOpenHelper = new SqlOpenHelper(getApplicationContext());
        sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){

        }
        else{
            user=bundle.getString("logged_On");
            editor.putString("current_user",user);
            editor.apply();
        }

        if(!sharedPreferences.getBoolean("firstTime", false)) {
            // run your one time code
            editor.putInt("today",0);
            editor.putInt("yesterday",0);
            editor.putInt("thisWeek",0);
            editor.putInt("thisMonth",0);

            editor.putBoolean("firstTime", true);
            editor.apply();
            //today
            todayAlarm();
            //week
            weeklyAlarm();
            //Monthly
            monthlyAlarm();
        }
        Log.i("mesooi", " main called");

        total.setText(""+sharedPreferences.getInt("today",0));
        yesterday.setText(""+sharedPreferences.getInt("yesterday",0));
        thisweek.setText(""+sharedPreferences.getInt("thisWeek",0));
        thismonth.setText(""+sharedPreferences.getInt("thisMonth",0));

        Calendar c=Calendar.getInstance();
        String time= String.valueOf(c.getTime());
        update(time);
        permissions();
    }

    private void weeklyAlarm() {
        Intent weekIntent=new Intent(this,weekReceiver.class);
        PendingIntent weekpendingIntent=PendingIntent.getBroadcast(this,0,weekIntent,0);
        AlarmManager weekalarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        Calendar weekcalendar = Calendar.getInstance();
        weekcalendar.setTimeInMillis(System.currentTimeMillis());
        weekcalendar.set(Calendar.DAY_OF_WEEK, 1);
        weekcalendar.set(Calendar.HOUR_OF_DAY, 0);
        long timeWeek=weekcalendar.getTimeInMillis();

        if (weekalarmManager != null) {
            weekalarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeWeek,AlarmManager.INTERVAL_DAY*7, weekpendingIntent);
        }
    }
    private void monthlyAlarm() {
        Intent monthIntent=new Intent(this,monthReceiver.class);
        PendingIntent monthpendingIntent=PendingIntent.getBroadcast(this,0,monthIntent,0);
        AlarmManager monthalarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        Calendar monthcalendar = Calendar.getInstance();
        monthcalendar.setTimeInMillis(System.currentTimeMillis());
        monthcalendar.set(Calendar.WEEK_OF_MONTH, 1);
        monthcalendar.set(Calendar.DAY_OF_WEEK, 1);
        monthcalendar.set(Calendar.HOUR_OF_DAY,0);
        long timeMonth=monthcalendar.getTimeInMillis();

        if (monthalarmManager != null) {
            monthalarmManager.set(AlarmManager.RTC_WAKEUP,timeMonth,monthpendingIntent);
        }
    }
    private void todayAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.AM_PM, Calendar.PM);
        Intent myIntent = new Intent(this, receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent,0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void open(View view) {
        Intent intent=new Intent(this,createReceipt.class);
        startActivity(intent);
    }

    public void temp(View view) {
        Intent temp=new Intent(this,homeActivity.class);
        startActivity(temp);
    }


    public void drop(View view) {
        Intent drop=new Intent(this,unit.class);
        startActivity(drop);
    }

    public void list(View view) {
        Intent l=new Intent(this,rcptList.class);
        startActivity(l);
    }

    public void permissions(){
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
/*        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //permission not granted
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            //show explanation for need of permisssion
        }
        else{

        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if((grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                   // Toast.makeText(getApplicationContext(),"welcome",Toast.LENGTH_SHORT).show();


                }
                else{
                   // Toast.makeText(getApplicationContext(),"Please allow permissions",Toast.LENGTH_SHORT).show();

                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    public void update(String time){
        String select=SqlOpenHelper.KEY_ID+"=?";
        String[]selectArgs={user};
        ContentValues values=new ContentValues();
        values.put(SqlOpenHelper.KEY_LOG,time);
        SQLiteDatabase db=sqlOpenHelper.getWritableDatabase();
        db.update(SqlOpenHelper.TABLE_USER,values,select,selectArgs);
    }


}
