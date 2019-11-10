package com.example.dedan.digitalreceipts;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Toolbar maintool;
    SharedPreferences sharedPreferences;

    private String loggedIn_user;
    private String loggedIn_username;
    private int loggedIn_baseId;
    private long loggedIn_empNo;
    private Cursor mcursor;
    private String dataToday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maintool = findViewById(R.id.mainActivitybar);
        setSupportActionBar(maintool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);


        sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        try{
            loggedIn_user = getIntent().getExtras().getString("loggedIn_user");
            loggedIn_username=getIntent().getExtras().getString("loggedIn_username");
            loggedIn_baseId=getIntent().getExtras().getInt("loggedIn_baseId");
            loggedIn_empNo=getIntent().getExtras().getLong("loggedIn_empNo");

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if(loggedIn_user !=null){
            editor.putInt("current_user",0);
            editor.putInt("current_user_no.of_sales",0);
            editor.putLong("current_empNo",loggedIn_empNo);
            editor.putString("current_user",loggedIn_user);
            editor.putString("current_username",loggedIn_username);
            editor.apply();
        }

        Date date=new Date();
        String savedToday = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String savedmonth=new SimpleDateFormat("MM/yyyy").format(date);
        String savedyear=new SimpleDateFormat("yyyy").format(date);

        if(!sharedPreferences.getBoolean("firstTime", false)) {
            editor.putString("todays_date",savedToday);
            editor.putString("currentMonth",savedmonth);
            editor.putString("currentYear",savedyear);
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

        String checkDate=sharedPreferences.getString("todays_date","");
        String checkMonth=sharedPreferences.getString("currentMonth","");
        String checkYear=sharedPreferences.getString("currentYear","");
        if(!checkDate.equals(savedToday)){

            editor.putString("todays_date",checkDate);
        }
        if(!checkMonth.equals(savedmonth)){

            editor.putString("currentMonth",checkMonth);
        }
        if(!checkYear.equals(savedyear)){
            editor.putString("currentYear",checkYear);
        }
        Calendar c=Calendar.getInstance();
        String time= String.valueOf(c.getTime());

        permissions();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                finish();
                loggedIn_user=null;
                loggedIn_username=null;
                loggedIn_baseId= 0;
                Intent logout=new Intent(this,logIn.class);
                startActivity(logout);
                break;
            case R.id.secmenu:
                Intent sec=new Intent(this,security.class);
                startActivity(sec);
                break;
        }
        return super.onOptionsItemSelected(item);
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
            weekalarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeWeek,AlarmManager.INTERVAL_DAY*7, weekpendingIntent);
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
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
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


}
