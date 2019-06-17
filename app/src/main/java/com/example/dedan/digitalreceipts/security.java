package com.example.dedan.digitalreceipts;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class security extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {
    SharedPreferences sharedPreferences;
    SqlOpenHelper sqlOpenHelper;
    EditText txt_user,txt_pass;
    EditText user_fName,user_scName,user_empNo,user_DOB,user_resid,user_mobileNo,user_email;
    String d;
    private int TOOLSPIN_LOAD=3;
    private int USER_LOAD=4;
    private SQLiteDatabase db;
    private SimpleCursorAdapter simpleCursorAdapter;
    public long empID=0;
    public String user;
    private Spinner spinner;
    Cursor mcursor;
    private ViewpagerAdapter adapter;
    private ViewPager viewPager;
    private com.example.dedan.digitalreceipts.secFragment secFragment;
    private transactionFragment transactionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        viewPager = findViewById(R.id.viewpager);
        if(viewPager !=null){
            adapter = new ViewpagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);

            sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
            user = sharedPreferences.getString("current_username","");
        sqlOpenHelper = new SqlOpenHelper(getApplicationContext());


        if(user.startsWith("ADMIN")){
            simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,null,new String[]
                    {SqlOpenHelper.KEY_empNO},new int[]{android.R.id.text1},0);
            simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            db=sqlOpenHelper.getReadableDatabase();
            String[] columns={SqlOpenHelper._USERID,SqlOpenHelper.KEY_empNO,SqlOpenHelper.KEY_FIRSTNAME};
            mcursor=db.query(SqlOpenHelper.TABLE_USER,columns,
                    null,null,null,null,SqlOpenHelper.KEY_empNO);
            simpleCursorAdapter.changeCursor(mcursor);
            }

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

            }
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        MenuItem item=menu.findItem(R.id.toolspinner);
        spinner = (Spinner)MenuItemCompat.getActionView(item);
        spinner.setAdapter(simpleCursorAdapter);
       // getLoaderManager().initLoader(TOOLSPIN_LOAD,null,this);
        spinner.setOnItemSelectedListener(this);
        if(!user.startsWith("ADMIN")){
            spinner.setEnabled(false);
            spinner.setActivated(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.toolspinner:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void update(){
        String select=SqlOpenHelper.KEY_ID+"=?";
        String[]selectArgs={user};
        ContentValues values=new ContentValues();
        values.put(SqlOpenHelper.KEY_NAME,txt_user.getText().toString());
        values.put(SqlOpenHelper.KEY_PASS,txt_pass.getText().toString());
        values.put(SqlOpenHelper.KEY_FIRSTNAME,user_fName.getText().toString());
        values.put(SqlOpenHelper.KEY_SECNAME,user_scName.getText().toString());
        values.put(SqlOpenHelper.KEY_MOBILENO,user_mobileNo.getText().toString());
        values.put(SqlOpenHelper.KEY_residence,user_resid.getText().toString());
        values.put(SqlOpenHelper.KEY_DOB,user_DOB.getText().toString());
        values.put(SqlOpenHelper.KEY_EMAIL,user_email.getText().toString());
        SQLiteDatabase db=sqlOpenHelper.getWritableDatabase();
        db.update(SqlOpenHelper.TABLE_USER,values,select,selectArgs);
    }
    public void invalidate(){
        String select=SqlOpenHelper.KEY_ID+"=?";
        String[]selectArgs={String.valueOf(empID)};
        ContentValues values=new ContentValues();
        values.put(SqlOpenHelper.KEY_ACCESS,"ACCESS_DENIED");
        SQLiteDatabase db=sqlOpenHelper.getWritableDatabase();
        db.update(SqlOpenHelper.TABLE_USER,values,select,selectArgs);
    }
    public void authorise(){
        String select=SqlOpenHelper.KEY_ID+"=?";
        String[]selectArgs={String.valueOf(empID)};
        ContentValues values=new ContentValues();
        values.put(SqlOpenHelper.KEY_ACCESS,"ACCESS_GRANTED");
        SQLiteDatabase db=sqlOpenHelper.getWritableDatabase();
        db.update(SqlOpenHelper.TABLE_USER,values,select,selectArgs);
    }
    public void delete(String id){
        String select=SqlOpenHelper.KEY_ID+"=?";
        String[]selectArgs={id};
        SQLiteDatabase db=sqlOpenHelper.getWritableDatabase();
        db.delete(SqlOpenHelper.TABLE_USER,select,selectArgs);

    }
    public void saveUpdate(View view) {
        update();
        notEditable();
    }

    public void edit(View view) {
        setEditable();
    }
    public void notEditable(){
        txt_user.setEnabled(false);
        txt_pass.setEnabled(false);
        user_email.setEnabled(false);
        user_DOB.setEnabled(false);
        user_resid.setEnabled(false);
        user_mobileNo.setEnabled(false);
        user_fName.setEnabled(false);
        user_scName.setEnabled(false);
        user_empNo.setEnabled(false);
    }
    public void setEditable(){
        txt_user.setEnabled(true);
        txt_pass.setEnabled(true);
        user_email.setEnabled(true);
        user_DOB.setEnabled(true);
        user_resid.setEnabled(true);
        user_mobileNo.setEnabled(true);
        user_fName.setEnabled(true);
        user_scName.setEnabled(true);
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
            empID=l;
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(adapter.getItem(0));
            secFragment =com.example.dedan.digitalreceipts.secFragment.newInstance(l);
            fragmentTransaction.replace(R.id.secfrag, secFragment);
            fragmentTransaction.commit();
            adapter.notifyDataSetChanged();
            }
        }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}