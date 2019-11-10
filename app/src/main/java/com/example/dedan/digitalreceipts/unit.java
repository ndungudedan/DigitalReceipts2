package com.example.dedan.digitalreceipts;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.database.Cursor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
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
import java.util.List;
import java.util.Objects;


public class unit extends AppCompatActivity  {
    private GoodsViewModel goodsViewModel;

    public static final int LOAD = 0;
    TableLayout tableLayout=null;
    EditText item;
    EditText pack;
    EditText unit;
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

        item=(EditText)findViewById(R.id.itemtxt);
        pack=(EditText)findViewById(R.id.packtxt);
        unit=(EditText)findViewById(R.id.unittxt);
        checkid = new ArrayList();


        goodsViewModel= ViewModelProviders.of(this).get(GoodsViewModel.class);
        goodsViewModel.getAllGoods().observe(this, new Observer<List<GoodsEntity>>() {
            @Override
            public void onChanged(@Nullable List<GoodsEntity> goodsEntities) {

            }
        });
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

//        GoodsEntity goodsEntity=new GoodsEntity(goods,container,cost);
      //  goodsViewModel.insert(goodsEntity);
        //sqlOpenHelper.add_item_to_database(goods,container,cost);
    }
    public void delete(){

    }
    public void table_load(String count,String goods,String container,String cost){
        TableRow tableRow=new TableRow(this);
        TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(layoutParams);

        TextView no=new TextView(this);
        no.setPadding(10,5,10,5);
        no.setText(count);
        tableRow.addView(no);

        TextView txt=new TextView(this);
        txt.setPadding(10,5,10,5);
        txt.setText(goods);
        tableRow.addView(txt);

        TextView txt1=new TextView(this);
        txt1.setText(container);
        txt1.setPadding(10 ,5,10,5);
        tableRow.addView(txt1);

        TextView txt2=new TextView(this);
        txt2.setText(cost);
        txt2.setPadding(10,5,10,5);
        tableRow.addView(txt2);

        checkBox = new CheckBox(this);
        checkBox.setChecked(false);
        checkBox.setId(Integer.parseInt(count));
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
}
