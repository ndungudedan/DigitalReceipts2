package com.example.dedan.digitalreceipts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class unit extends AppCompatActivity  {
    private GoodsViewModel goodsViewModel;
    private String item;
    private int cost;
    private String pack;

    Toolbar unittool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        unittool = findViewById(R.id.unitbar);
        setSupportActionBar(unittool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FloatingActionButton floatingActionButton=findViewById(R.id.floating_unit_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputDialog();
            }
        });


        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final UnitAdapter adapter=new UnitAdapter();
        recyclerView.setAdapter(adapter);

        goodsViewModel= ViewModelProviders.of(this).get(GoodsViewModel.class);
        goodsViewModel.getAllGoods().observe(this, new Observer<List<GoodsEntity>>() {
            @Override
            public void onChanged(@Nullable List<GoodsEntity> goodsEntities) {
                adapter.submitList(goodsEntities);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                goodsViewModel.delete(adapter.getGoodAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new UnitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsEntity goodsEntity) {
                editinputDialog(goodsEntity.getKEY_GOODS_ID(),goodsEntity.getKEY_ITEM(),goodsEntity.getKEY_PACK(),goodsEntity.getKEY_COST());
            }
        });

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem) {
        switch(menuitem.getItemId()){
            case R.id.delete:
                confirmdialog();
                break;
    }
        return super.onOptionsItemSelected(menuitem);
    }
    public void confirmdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(unit.this);
        View mview=getLayoutInflater().inflate(R.layout.confirm_dialogbox,null);
        TextView mess = mview.findViewById(R.id.conf_text);
        mess.setText("DELETE ALL ITEMS");
        builder.setTitle("CONFIRM");
        builder.setView(mview);
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAll();
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

    private void deleteAll() {
        goodsViewModel.deleteAll();
    }


    public void inputDialog(){
        final EditText item_edit,cost_edit,pack_edit;
        AlertDialog.Builder builder = new AlertDialog.Builder(unit.this);
        View mview=getLayoutInflater().inflate(R.layout.unit_goods_dialog,null);
        item_edit=mview.findViewById(R.id.itemtxt);
        cost_edit=mview.findViewById(R.id.unittxt);
        pack_edit=mview.findViewById(R.id.packtxt);
        builder.setView(mview);
        builder.setTitle("Item");
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                item=item_edit.getText().toString();
                cost= Integer.parseInt(cost_edit.getText().toString());
                pack=pack_edit.getText().toString();

                GoodsEntity goodsEntity=new GoodsEntity(item,pack,cost);
                goodsViewModel.insert(goodsEntity);

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    public void editinputDialog(final int i, String it, String pa, int co) {
        final EditText item_edit, cost_edit, pack_edit;
        AlertDialog.Builder builder = new AlertDialog.Builder(unit.this);
        View mview = getLayoutInflater().inflate(R.layout.unit_goods_dialog, null);
        item_edit = mview.findViewById(R.id.itemtxt);
        cost_edit = mview.findViewById(R.id.unittxt);
        pack_edit = mview.findViewById(R.id.packtxt);
        item_edit.setText(it);
        pack_edit.setText(pa);
        cost_edit.setText(String.valueOf(co));
        builder.setView(mview);
        builder.setTitle("Edit");
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                item = item_edit.getText().toString();
                cost = Integer.parseInt(cost_edit.getText().toString());
                pack = pack_edit.getText().toString();

                GoodsEntity goodsEntity = new GoodsEntity(item, pack, cost);
                goodsEntity.setKEY_GOODS_ID(i);
                goodsViewModel.update(goodsEntity);

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    }
