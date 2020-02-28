package com.example.dedan.digitalreceipts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dedan.digitalreceipts.Database.Store.CategoryEntity;
import com.example.dedan.digitalreceipts.Database.Store.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;


public class unit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private GoodsViewModel goodsViewModel;
    private CategoryViewModel categoryViewModel;
    private String item;
    private int cost;
    private String pack;
    TextView nulltxt;
    Spinner catSpinner;
    Button catbtn;
    Toolbar unittool;
    private ArrayAdapter<String> spinAdapt;
    private List<String> spinList=new ArrayList<>();
    private String cat;
    private UnitAdapter adapter;
    private int pick=1;
    private Uri uri;
    private Bitmap bitmap;
    private ImageView imageView;
    private List<GoodsEntity> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        unittool = findViewById(R.id.unitbar);
        setSupportActionBar(unittool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        goodsViewModel= ViewModelProviders.of(this).get(GoodsViewModel.class);
        categoryViewModel=ViewModelProviders.of(this).get(CategoryViewModel.class);

        catSpinner=findViewById(R.id.cat_spin);
        catbtn=findViewById(R.id.cat_btn);
        nulltxt=findViewById(R.id.empty_unit);
        FloatingActionButton floatingActionButton=findViewById(R.id.floating_unit_btn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputDialog();
            }
        });
        catbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             addCategory();
            }
        });
        categoryViewModel.AllCategorys().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categoryEntities) {
                for(CategoryEntity ent:categoryEntities){
                    spinList.add(ent.getKEY_Category());
                }
                spinList.add(0,"ALL CATEGORY");
                loadSpinner();
            }

        });
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new UnitAdapter();
        recyclerView.setAdapter(adapter);

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
                editinputDialog(goodsEntity.getKEY_GOODS_ID(),goodsEntity.getKEY_PIC(),goodsEntity.getKEY_ITEM(),goodsEntity.getKEY_PACK(),goodsEntity.getKEY_COST());
            }
        });

    }
    public void loadSpinner(){
        if(!spinList.isEmpty()){
            spinAdapt=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinList);
            spinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            catSpinner.setAdapter(spinAdapt);
            catSpinner.setOnItemSelectedListener(this);
            //catSpinner.setSelection(0);
        }
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
        final Spinner spinner;
        AlertDialog.Builder builder = new AlertDialog.Builder(unit.this);
        View mview=getLayoutInflater().inflate(R.layout.unit_goods_dialog,null);
        item_edit=mview.findViewById(R.id.itemtxt);
        cost_edit=mview.findViewById(R.id.unittxt);
        pack_edit=mview.findViewById(R.id.packtxt);
        imageView =mview.findViewById(R.id.item_image);
        spinner=mview.findViewById(R.id.cat_spin);
        spinAdapt=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinList);
        //spinList.remove(0);
        spinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapt);
        spinner.setOnItemSelectedListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"pictures"),pick);
            }
        });
        builder.setView(mview);
        builder.setTitle("Item");
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                item=item_edit.getText().toString();
                cost= Integer.parseInt(cost_edit.getText().toString());
                pack=pack_edit.getText().toString();

                GoodsEntity goodsEntity=new GoodsEntity(goodpic(bitmap),item,pack,cost,spinner.getSelectedItem().toString());
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick&&resultCode==RESULT_OK){
            uri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public void editinputDialog(final int i,String pic, String it, String pa, int co) {
        final EditText item_edit, cost_edit, pack_edit;
        final Spinner spinner;
        AlertDialog.Builder builder = new AlertDialog.Builder(unit.this);
        View mview = getLayoutInflater().inflate(R.layout.unit_goods_dialog, null);
        item_edit = mview.findViewById(R.id.itemtxt);
        cost_edit = mview.findViewById(R.id.unittxt);
        pack_edit = mview.findViewById(R.id.packtxt);
        imageView=mview.findViewById(R.id.item_image);
        spinner=mview.findViewById(R.id.cat_spin);
        spinAdapt=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinList);
        //spinList.remove(0);
        spinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapt);
        spinner.setOnItemSelectedListener(this);
        item_edit.setText(it);
        pack_edit.setText(pa);
        cost_edit.setText(String.valueOf(co));
        retreivePic(pic,it);
        builder.setView(mview);
        builder.setTitle("Edit");
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                item = item_edit.getText().toString();
                cost = Integer.parseInt(cost_edit.getText().toString());
                pack = pack_edit.getText().toString();

                GoodsEntity goodsEntity = new GoodsEntity(goodpic(bitmap),item, pack, cost,spinner.getSelectedItem().toString());
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
    public void addCategory(){
        final EditText cat_edit,cost_edit,pack_edit;
        AlertDialog.Builder builder = new AlertDialog.Builder(unit.this);
        View mview=getLayoutInflater().inflate(R.layout.unit_goods_dialog,null);
        cat_edit=mview.findViewById(R.id.itemtxt);
        cost_edit=mview.findViewById(R.id.unittxt);
        pack_edit=mview.findViewById(R.id.packtxt);
        builder.setView(mview);
        builder.setTitle("Add Category");
        cost_edit.setVisibility(View.INVISIBLE);pack_edit.setVisibility(View.INVISIBLE);
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                cat=cat_edit.getText().toString();
                categoryViewModel.insert(new CategoryEntity(cat));
                spinList.clear();
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(spinList.get(position).equals("ALL CATEGORY")){
            goodsViewModel.getAllGoods().observe(this, new Observer<List<GoodsEntity>>() {
                @Override
                public void onChanged(@Nullable List<GoodsEntity> goodsEntities) {
                    adapter.submitList(goodsEntities);
                    goodsList=goodsEntities;
                    if(goodsEntities==null){
                        nulltxt.setVisibility(View.VISIBLE);
                        nulltxt.setText("Nothing to display");
                    }
                }
            });
        }
        else{
        goodsViewModel.getAllCategoryGoods(spinList.get(position)).observe(this, new Observer<List<GoodsEntity>>() {
            @Override
            public void onChanged(List<GoodsEntity> goodsEntities) {
                adapter.submitList(goodsEntities);
                if(goodsEntities==null){
                    nulltxt.setVisibility(View.VISIBLE);
                    nulltxt.setText("Nothing to display");
                }
            }
        }); }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void retreivePic(String path,String item) {
        try{
            File file=new File(path,item+"pic.jpg");
            Bitmap bit= BitmapFactory.decodeStream(new FileInputStream(file));
            imageView.setImageBitmap(bit);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public String goodpic(Bitmap bit){
        ContextWrapper cw=new ContextWrapper(this);
        File dir=cw.getDir("Goodspic", MODE_PRIVATE);
        File file=new File(dir,item+"pic.jpg");
        if(!file.exists()){
        }
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(file);
            bit.compress(Bitmap.CompressFormat.PNG,100,fos);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return dir.getAbsolutePath();
    }
}
