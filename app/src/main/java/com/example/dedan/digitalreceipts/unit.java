package com.example.dedan.digitalreceipts;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.dedan.digitalreceipts.SqlOpenHelper.TABLE_ITEM_DETAILS;

public class unit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOAD = 0;
    TableLayout tableLayout=null;
    EditText item;
    EditText pack;
    EditText unit;
    SqlOpenHelper sqlOpenHelper;
    private Cursor mcursor;
    private CheckBox checkBox;
    private ArrayList checkid;
    Toolbar unittool;
    Boolean hideIcon=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        unittool = findViewById(R.id.unitbar);
        setSupportActionBar(unittool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        tableLayout=(TableLayout)findViewById(R.id.unitTable);
        sqlOpenHelper = new SqlOpenHelper(getApplicationContext());
        item=(EditText)findViewById(R.id.itemtxt);
        pack=(EditText)findViewById(R.id.packtxt);
        unit=(EditText)findViewById(R.id.unittxt);
        checkid = new ArrayList();

        getLoaderManager().initLoader(LOAD,null,this);
        //load();

    }
    public void refresh(){
        startActivity(getIntent());
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.del_menu, menu);
        if(hideIcon){
            menu.findItem(R.id.delete).setVisible(false);
        }
        else{
            menu.findItem(R.id.delete).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem) {
        switch(menuitem.getItemId()){
            case R.id.delete:
                delete();
                refresh();
                break;
    }
        return super.onOptionsItemSelected(menuitem);
    }

    public void add(View view) {
        String goods=item.getText().toString();
        String container=pack.getText().toString();
        String cost=unit.getText().toString();
        if(goods.isEmpty())
        {
            item.setError("empty");
            return;
        }

        sqlOpenHelper.add_item_to_database(goods,container,cost);
        refresh();
    }
    public void delete(){
        SQLiteDatabase db =sqlOpenHelper.getWritableDatabase();
        String selection=SqlOpenHelper._ID + " = ?";
        //String[] selectionArgs=new String[checkid.size()];
        for(int i=0; i<checkid.size();i++){
            String[] selectionArgs=new String[]{Integer.toString((Integer) checkid.get(i))};
            //selectionArgs[i]=Integer.toString((Integer) checkid.get(i));
            db.delete(TABLE_ITEM_DETAILS, selection,selectionArgs );
        }
        db.close();
    }

    public void load(){
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper.KEY_ITEM_ID,SqlOpenHelper.KEY_ITEM,SqlOpenHelper.KEY_PACK,SqlOpenHelper.KEY_COST};
        mcursor = db.query(SqlOpenHelper.TABLE_ITEM_DETAILS, columns, null, null,
                null, null, null);
        int IdPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_ITEM_ID);
        int itemPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_ITEM);
        int packPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_PACK);
        int costPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_COST);
        //refresh views here so that they can load again
        while (mcursor.moveToNext()) {
            String id = mcursor.getString(IdPos);
            String n = mcursor.getString(itemPos);
            String c = mcursor.getString(packPos);
            String h = mcursor.getString(costPos);
           // table_load(id,n,c,h);
        }
       // mcursor.close();
    }
    public void table_load(int count,String goods,String container,String cost){
        TableRow tableRow=new TableRow(this);
        TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(layoutParams);

        TextView no=new TextView(this);
        no.setPadding(5,5,5,5);
//        no.setText(count);
        tableRow.addView(no);

        TextView txt=new TextView(this);
        txt.setPadding(5,5,5,5);
        txt.setText(goods);
        tableRow.addView(txt);

        TextView txt1=new TextView(this);
        txt1.setText(container);
        txt1.setPadding(5 ,5,5,5);
        tableRow.addView(txt1);

        TextView txt2=new TextView(this);
        txt2.setText(cost);
        txt2.setPadding(5,5,5,5);
        tableRow.addView(txt2);

        checkBox = new CheckBox(this);
        checkBox.setChecked(false);
        checkBox.setId(count);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int cb=0;
                if(b){
                    cb=compoundButton.getId();
                    checkid.add(cb);
                    hideIcon=false;

                }
                else{
                    checkid.remove(cb);
                    if(checkid.isEmpty()){
                        hideIcon=true;
                    }

                }
                invalidateOptionsMenu();
            }
        });
//        int c=Integer.parseInt(count);
 //       checkBox.setId(c);
        tableRow.addView(checkBox);
        tableLayout.addView(tableRow);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        android.content.CursorLoader loader=null;
        if(i==LOAD)
            loader=createLoaderValues();

        return loader;
    }

    private android.content.CursorLoader createLoaderValues() {
        return new android.content.CursorLoader(this) {
            @Override
            public Cursor loadInBackground() {
                SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
                String[] columns = {SqlOpenHelper._ID,SqlOpenHelper.KEY_ITEM_ID,SqlOpenHelper.KEY_ITEM,SqlOpenHelper.KEY_PACK,SqlOpenHelper.KEY_COST};
                return db.query(SqlOpenHelper.TABLE_ITEM_DETAILS, columns, null, null,
                        null, null, null);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId()==LOAD)
            loadFinishedValues(cursor);

    }

    private void loadFinishedValues(Cursor cursor) {
        mcursor=cursor;
        int IdPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_ITEM_ID);
        int itemPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_ITEM);
        int packPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_PACK);
        int costPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_COST);
        //refresh views here so that they can load again
        int x=0;
        while (mcursor.moveToNext()) {
            String id = mcursor.getString(IdPos);
            String n = mcursor.getString(itemPos);
            String c = mcursor.getString(packPos);
            String h = mcursor.getString(costPos);
            table_load(x,n,c,h);
            x++;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId()==LOAD){
            if(mcursor!=null)
                mcursor.close();
        }

    }
}