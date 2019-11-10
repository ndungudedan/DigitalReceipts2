package com.example.dedan.digitalreceipts;

import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class security extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    UserViewModel userViewModel;
    List<UserEntity> userList;
    ArrayAdapter<UserEntity> useradapter;

    SharedPreferences sharedPreferences;
    EditText txt_user, txt_pass;
    EditText user_fName, user_scName, user_empNo, user_DOB, user_resid, user_mobileNo, user_email;
    String d;
    private int TOOLSPIN_LOAD = 3;
    private int USER_LOAD = 4;
    private SQLiteDatabase db;

    public static long empID = 0;
    public static long check_empNo=0;  //to be used in analytics_user fragment
    public static String checkuser="";
    public String user;
    private Spinner spinner;
    Cursor mcursor;
    private FragmentStatePagerAdapter adapter;
    private ViewPager viewPager;
    PagerTabStrip pagerTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        viewPager = findViewById(R.id.viewpager);
        if (viewPager != null) {
            pagerTabStrip=findViewById(R.id.pager_header);
            adapter = new ViewpagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            pagerTabStrip=findViewById(R.id.pager_header);
            //adapter = new ViewpagerAdapter(getSupportFragmentManager());
            //viewPager.setAdapter(adapter);

            sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPreferences.edit();
            user = sharedPreferences.getString("current_username", "");

            userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
       //     userList=userViewModel.getAllUsers();
            if (user.startsWith("ADMIN")) {
                useradapter= new ArrayAdapter<UserEntity>(this,android.R.layout.simple_spinner_dropdown_item,userList);

            }
        }
    }

    public void log_info() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        MenuItem item = menu.findItem(R.id.toolspinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setAdapter(useradapter);
        // getLoaderManager().initLoader(TOOLSPIN_LOAD,null,this);
        spinner.setOnItemSelectedListener(this);
        if (!user.startsWith("ADMIN")) {
            spinner.setEnabled(false);
            spinner.setActivated(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolspinner:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void delete(String id) {
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(user.startsWith("ADMIN")){
            String z=null;
            long e=0;

            empID=l;
            //checkuser=z;
            check_empNo=e;
            adapter.notifyDataSetChanged();
            }
        }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}