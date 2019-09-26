package com.example.dedan.digitalreceipts;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Objects;

import static com.example.dedan.digitalreceipts.secFragment.newInstance;

public class security extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {
    SharedPreferences sharedPreferences;
    SqlOpenHelper sqlOpenHelper;
    EditText txt_user, txt_pass;
    EditText user_fName, user_scName, user_empNo, user_DOB, user_resid, user_mobileNo, user_email;
    String d;
    private int TOOLSPIN_LOAD = 3;
    private int USER_LOAD = 4;
    private SQLiteDatabase db;
    private SimpleCursorAdapter simpleCursorAdapter;
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
            sqlOpenHelper = new SqlOpenHelper(getApplicationContext());


            if (user.startsWith("ADMIN")) {
                simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, null, new String[]
                        {SqlOpenHelper.KEY_empNO}, new int[]{android.R.id.text1}, 0);
                simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                db = sqlOpenHelper.getReadableDatabase();
                String[] columns = {SqlOpenHelper._USERID, SqlOpenHelper.KEY_empNO, SqlOpenHelper.KEY_FIRSTNAME};
                mcursor = db.query(SqlOpenHelper.TABLE_USER, columns,
                        null, null, null, null, SqlOpenHelper.KEY_empNO);
                simpleCursorAdapter.changeCursor(mcursor);
            }
        }
    }

    public void log_info() {
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper.KEY_ID, SqlOpenHelper.KEY_NAME, SqlOpenHelper.KEY_PASS, SqlOpenHelper.KEY_EMAIL, SqlOpenHelper.KEY_LOG};
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
            if (n.startsWith("ADMIN")) {
                d = cursor.getString(IdPos);
                String p = cursor.getString(passPos);

            }
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        MenuItem item = menu.findItem(R.id.toolspinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setAdapter(simpleCursorAdapter);
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
        String select = SqlOpenHelper.KEY_ID + "=?";
        String[] selectArgs = {id};
        SQLiteDatabase db = sqlOpenHelper.getWritableDatabase();
        db.delete(SqlOpenHelper.TABLE_USER, select, selectArgs);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        android.content.CursorLoader loader=null;
        if(i==TOOLSPIN_LOAD){
            loader=createLoadervalues();
        }
        if(i==USER_LOAD){
            loader=createLoaderUservalues();
        }
        return loader;
    }

    private CursorLoader createLoaderUservalues() {
        return new android.content.CursorLoader(this){
            @Override
            public Cursor loadInBackground() {
                db=sqlOpenHelper.getReadableDatabase();
                String[] columns={SqlOpenHelper._USERID,SqlOpenHelper.KEY_empNO,SqlOpenHelper.KEY_FIRSTNAME};
                return db.query(SqlOpenHelper.TABLE_USER,columns,
                        null,null,null,null,SqlOpenHelper.KEY_empNO);
            }
        };
    }

    private CursorLoader createLoadervalues() {
        return new android.content.CursorLoader(this){
            @Override
            public Cursor loadInBackground() {
                db=sqlOpenHelper.getReadableDatabase();
                String[] columns={SqlOpenHelper._USERID,SqlOpenHelper.KEY_empNO,SqlOpenHelper.KEY_FIRSTNAME};
                return db.query(SqlOpenHelper.TABLE_USER,columns,
                        null,null,null,null,SqlOpenHelper.KEY_empNO);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor o) {
       /* if(loader.getId()==TOOLSPIN_LOAD) {
            simpleCursorAdapter.changeCursor(o);
        }
        if(loader.getId()==USER_LOAD) {
            simpleCursorAdapter.changeCursor(o);
        }*/
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
      /*  if(loader.getId()==TOOLSPIN_LOAD) {
            simpleCursorAdapter.changeCursor(null);
        }
        if(loader.getId()==USER_LOAD) {
            simpleCursorAdapter.changeCursor(null);
        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(user.startsWith("ADMIN")){
            String z=null;
            long e=0;
            SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
            String[] columns = {SqlOpenHelper.KEY_ACCESS,SqlOpenHelper._USERID,SqlOpenHelper.KEY_empNO};
            Cursor cursor = db.query(SqlOpenHelper.TABLE_USER, columns, null, null,
                    null, null, null);
            int accessPos = cursor.getColumnIndex(SqlOpenHelper.KEY_ACCESS);
            int empPos=cursor.getColumnIndex(SqlOpenHelper.KEY_empNO);
            int idPos=cursor.getColumnIndex(SqlOpenHelper._USERID);
            //refresh views here so that they can load again
            while (cursor.moveToNext()) {
                long s=cursor.getLong(idPos);
                if(s==l){
                    e=cursor.getLong(empPos);
                     z = cursor.getString(accessPos);
                    break;
                }
            }
            cursor.close();
            empID=l;
            checkuser=z;
            check_empNo=e;
            adapter.notifyDataSetChanged();
            }
        }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}