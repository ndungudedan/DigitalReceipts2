package com.example.dedan.digitalreceipts;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dedan.digitalreceipts.Database.Today_Database.TodayEntity;
import com.example.dedan.digitalreceipts.Database.Today_Database.TodayViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Toolbar maintool;
    SharedPreferences sharedPreferences;

    ReceiptViewModel receiptViewModel;
    MonthSalesViewModel monthSalesViewModel;
    WeekSalesViewModel weekSalesViewModel;
    TodayViewModel todayViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maintool = findViewById(R.id.mainActivitybar);
        setSupportActionBar(maintool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);


        sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        weekSalesViewModel=ViewModelProviders.of(this).get(WeekSalesViewModel.class);
        monthSalesViewModel=ViewModelProviders.of(this).get(MonthSalesViewModel.class);
        todayViewModel=ViewModelProviders.of(this).get(TodayViewModel.class);

        TodayEntity todayEntity=todayViewModel.getUserTodaySales(sharedPreferences.getString("current_userId","0"));
        if(todayEntity!=null){
            Date date=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
            String timeStamp= sdf.format(date);
            if(!timeStamp.contains(todayEntity.getKEY_DAY())){
                todayViewModel.deleteAll();
            }
        }

        if(!sharedPreferences.getBoolean("firstTime", false)) {
            editor.putBoolean("firstTime", true);
            editor.apply();

        }

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
                confirmdialog();
                break;
            case R.id.secmenu:
                Intent sec=new Intent(this,CompanyStats.class);
                startActivity(sec);
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public void cust(View view){
        Intent j=new Intent(this,CustomerActivity.class);
        startActivity(j);
    }
    public void stat(View view){
        Intent j=new Intent(this,security.class);
        startActivity(j);
    }

    public void confirmdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        View mview=getLayoutInflater().inflate(R.layout.confirm_dialogbox,null);
        TextView mess = mview.findViewById(R.id.conf_text);
        mess.setText("YOU ARE LOGGING OUT");
        builder.setTitle("CONFIRM");
        builder.setView(mview);
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void logout() {
        finish();
        Intent logout=new Intent(this,logIn.class);
        startActivity(logout);
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
