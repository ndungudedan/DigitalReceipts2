package com.example.dedan.digitalreceipts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.core.app.NavUtils;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.August.AugEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.August.AugViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.December.DecEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.December.DecViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.February.FebEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.February.FebViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.January.JanEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.January.JanViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.July.JulEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.July.JulViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.June.JunEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.June.JunViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.March.MarEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.March.MarViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.November.NovEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.November.NovViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.October.OctEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.October.OctViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.September.SepEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.September.SepViewModel;
import com.example.dedan.digitalreceipts.Database.Today_Database.UserStatsMonthViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Monday.MonEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Monday.MonViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Sunday.SunEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Sunday.SunViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Thursday.ThurEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Thursday.ThurViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Tuesday.TueEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Tuesday.TueViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Wednesday.WedEntity;
import com.example.dedan.digitalreceipts.Database.Week_Database.Wednesday.WedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class homeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    GoodsViewModel goodsViewModel;
    PickedGoodViewModel pickedGoodViewModel;
    ReceiptViewModel receiptViewModel;
    MonthSalesViewModel monthSalesViewModel;
    WeekSalesViewModel weekSalesViewModel;
    UserStatsViewModel userStatsViewModel;
    JanViewModel janViewModel;FebViewModel febViewModel;MarViewModel marViewModel;
    AprViewModel aprViewModel;MayViewModel mayViewModel;JunViewModel junViewModel;
    JulViewModel julViewModel;AugViewModel augViewModel;SepViewModel sepViewModel;
    OctViewModel octViewModel;NovViewModel novViewModel;DecViewModel decViewModel;
    MonViewModel monViewModel;TueViewModel tueViewModel;WedViewModel wedViewModel;ThurViewModel thurViewModel;
    FriViewModel friViewModel;SatViewModel satViewModel;SunViewModel sunViewModel;
    UserStatsMonthViewModel userStatsMonthViewModel;
    List<PickedGoodEntity> pickedGoods;

    TextView totalView;
    TextView nulltxt;
    public static final int GET_CUSTOMER_REQUEST=3;
    private static final int EDIT_CHECK_DIALOG=1;
    private static final int ADD_CHECK_DIALOG=2;

    String item;
    String pack;
    int cost;
    int quan;

    int itemlistCount=0;
    int count=0,initCount;
    private int total=0,clients=0;
    int receiptCount;

    Toolbar tool;
    File file;      //pdf file
    String name="",email,no,pay="",pobox,address,location;
    String timeStamp;
    int pdfCount=0;
    SharedPreferences shp;
    int userTodaySales,userWeeksales,userMonthsales;
    String contact ="contactKey";

    private String user;
    private String date;
    private EditText lipa;
    private String LoggeduserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_home);
        tool= findViewById(R.id.bar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Receipt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalView=findViewById(R.id.totaltxt);
        nulltxt=findViewById(R.id.empty_pro);

        goodsViewModel= ViewModelProviders.of(this).get(GoodsViewModel.class);
        pickedGoodViewModel=ViewModelProviders.of(this).get(PickedGoodViewModel.class);
        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        weekSalesViewModel=ViewModelProviders.of(this).get(WeekSalesViewModel.class);
        monthSalesViewModel=ViewModelProviders.of(this).get(MonthSalesViewModel.class);
        userStatsViewModel=ViewModelProviders.of(this).get(UserStatsViewModel.class);
        janViewModel=ViewModelProviders.of(this).get(JanViewModel.class);febViewModel=ViewModelProviders.of(this).get(FebViewModel.class);
        marViewModel=ViewModelProviders.of(this).get(MarViewModel.class);aprViewModel=ViewModelProviders.of(this).get(AprViewModel.class);
        mayViewModel=ViewModelProviders.of(this).get(MayViewModel.class);junViewModel=ViewModelProviders.of(this).get(JunViewModel.class);
        julViewModel=ViewModelProviders.of(this).get(JulViewModel.class);augViewModel=ViewModelProviders.of(this).get(AugViewModel.class);
        sepViewModel=ViewModelProviders.of(this).get(SepViewModel.class);octViewModel=ViewModelProviders.of(this).get(OctViewModel.class);
        novViewModel=ViewModelProviders.of(this).get(NovViewModel.class);decViewModel=ViewModelProviders.of(this).get(DecViewModel.class);
        monViewModel=ViewModelProviders.of(this).get(MonViewModel.class);tueViewModel=ViewModelProviders.of(this).get(TueViewModel.class);
        wedViewModel=ViewModelProviders.of(this).get(WedViewModel.class);thurViewModel=ViewModelProviders.of(this).get(ThurViewModel.class);
        friViewModel=ViewModelProviders.of(this).get(FriViewModel.class);satViewModel=ViewModelProviders.of(this).get(SatViewModel.class);
        sunViewModel=ViewModelProviders.of(this).get(SunViewModel.class);
        userStatsMonthViewModel =ViewModelProviders.of(this).get(UserStatsMonthViewModel.class);




        //pickedGoods=pickedGoodViewModel.getAllPickedGoodsList();

        shp=getSharedPreferences("Data",Context.MODE_PRIVATE);
        user = shp.getString("current_username","");
        LoggeduserId= String.valueOf(shp.getInt("current_userId",0));


        RecyclerView GoodsrecyclerView=findViewById(R.id.homeGoodsrecycler);
        GoodsrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        GoodsrecyclerView.setHasFixedSize(true);
        final UnitAdapter adapter=new UnitAdapter();
        GoodsrecyclerView.setAdapter(adapter);
        goodsViewModel.getAllGoods().observe(this, new Observer<List<GoodsEntity>>() {
            @Override
            public void onChanged(List<GoodsEntity> goodsEntities) {
                adapter.submitList(goodsEntities);
                if(goodsEntities.isEmpty()){
                    nulltxt.setVisibility(View.VISIBLE);
                    nulltxt.setText("Nothing to display");
                }
            }
        });

        adapter.setOnItemClickListener(new UnitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsEntity goodsEntity) {
                // do update, if item does not exist on picked table then insert
                //pickedGoods=pickedGoodViewModel.getAllPickedGoodsList();

                int res=0;
                quan=1;
                int picked_id=goodsEntity.getKEY_GOODS_ID();
                pack=goodsEntity.getKEY_PACK();
                cost=goodsEntity.getKEY_COST();
                item=goodsEntity.getKEY_ITEM();

                if(pickedGoods!=null){
                    for(PickedGoodEntity good : pickedGoods) {
                        int n = good.getKEY_picked_id();
                        int id=good.getKEY_ID();
                        if (n == picked_id) {
                            quan = quan + good.getKEY_Quantity();
                            int total=cost*quan;
                            PickedGoodEntity pickedGoodEntity=new PickedGoodEntity(quan,pack,item,cost,total,picked_id);
                            pickedGoodEntity.setKEY_ID(id);
                            pickedGoodViewModel.update(pickedGoodEntity);
                            res++;
                            break;
                        }
                    }
                    if(res==0) {
                        int total=cost*quan;
                        PickedGoodEntity pickedGoodEntity=new PickedGoodEntity(quan,pack,item,cost,total,picked_id);
                        pickedGoodViewModel.insert(pickedGoodEntity);
                    }
                }

            }
        });

        RecyclerView pickedRecycler=findViewById(R.id.pickedGoodsRecycler);
        pickedRecycler.setLayoutManager(new LinearLayoutManager(this));
        pickedRecycler.setHasFixedSize(true);
        final pickedGoodAdapter pickedAdapter=new pickedGoodAdapter();
        pickedRecycler.setAdapter(pickedAdapter);
        pickedGoodViewModel.getAllPickedGoods().observe(this, new Observer<List<PickedGoodEntity>>() {
            @Override
            public void onChanged(List<PickedGoodEntity> pickedGoodEntities) {
                pickedAdapter.submitList(pickedGoodEntities);
                pickedGoods=pickedGoodEntities;
                totalView.setText(String.valueOf(totalCount()));
            }
        });
        pickedAdapter.setOnItemClickListener(new pickedGoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PickedGoodEntity pickedGoodEntity) {
                editpickedinputDialog(pickedGoodEntity.getKEY_ID(),pickedGoodEntity.getKEY_Item(),pickedGoodEntity.getKEY_Pack(),
                        pickedGoodEntity.getKEY_Cost(),pickedGoodEntity.getKEY_Quantity(),pickedGoodEntity.getKEY_picked_id(),EDIT_CHECK_DIALOG);

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                 ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pickedGoodViewModel.delete(pickedAdapter.getGoodAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(pickedRecycler);

        FloatingActionButton floatingActionButton=findViewById(R.id.pickedfloatingBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpickedinputDialog(0,null,null,0,0,0,ADD_CHECK_DIALOG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_CUSTOMER_REQUEST && resultCode==RESULT_OK){
            if (data != null) {
                name=data.getStringExtra(CustomerActivity.EXT_FNAME)+""+data.getStringExtra(CustomerActivity.EXT_SNAME);
                email=data.getStringExtra(CustomerActivity.EXT_EMAIL);
                no= String.valueOf(data.getIntExtra(CustomerActivity.EXT_PHONE,0));
                pobox= String.valueOf(data.getIntExtra(CustomerActivity.EXT_POBOX,0));
                address= String.valueOf(data.getIntExtra(CustomerActivity.EXT_ADDRESS,0));
                location=data.getStringExtra(CustomerActivity.EXT_LOCATION);

                if(pay.isEmpty()){
                    inputDialog(total);
                }
                else{
                    pdfCount++;
                    if(pdfCount==1){
                        pdf();
                        Toast.makeText(getApplicationContext(),"SAVED",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Pdf Already saved\nPlease Refresh",Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                break;
            case R.id.save:
                if(pickedGoods.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Cannot save empty receipt",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(name.isEmpty()){
                    getClient();
                    }
                else if(pay.isEmpty()){
                    inputDialog(total);
                }
                    else{
                        operationcomplete();
                }
                //Toast.makeText(getApplicationContext(),"Empty PDF",Toast.LENGTH_SHORT).show();

                break;
            case R.id.send:
                if(file!=null){
                    email();
                }
                Toast.makeText(getApplicationContext(),"Please save pdf",Toast.LENGTH_SHORT).show();
                break;
            case R.id.client:
               getClient();
                break;
            case R.id.newPdf:
                confirmdialog();
                clearpickeditems();
                refresh();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void operationcomplete() {
        pdfCount++;
        if(pdfCount==1){
            userSalesInsert();
            pdf();
            ReceiptEntity receiptEntity=new ReceiptEntity(date,file.getAbsolutePath(),user,file.getName());
            receiptViewModel.insert(receiptEntity);
            Toast.makeText(getApplicationContext(),"SAVED",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Pdf Already saved\nPlease Refresh",Toast.LENGTH_SHORT).show();
        }
    }

    public void refresh(){
        startActivity(getIntent());
        finish();
        overridePendingTransition(0,0);
    }
    public void getClient(){
        Intent f=new Intent(this,CustomerActivity.class);
        f.putExtra("GetCustomer","From_homeActivity");
        startActivityForResult(f,GET_CUSTOMER_REQUEST);
    }
    public void companyTotals(String time,String session){
        MonthSalesEntity mTot=monthSalesViewModel.getSale(time);
        if(mTot!=null){
            MonthSalesEntity monthSalesEntity=new MonthSalesEntity(time,session,mTot.getKEY_clients()+1,mTot.getKEY_total()+total);
            monthSalesEntity.setKEY_ID(mTot.getKEY_ID());
            monthSalesViewModel.update(monthSalesEntity);
        }else{
            MonthSalesEntity monthSalesEntity=new MonthSalesEntity(time,session,1,total);
            monthSalesViewModel.insert(monthSalesEntity);
        }
    }
    public void userSalesInsert(){
        Calendar calendar=Calendar.getInstance();
        date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String userdb="user";

        date="Jan";
        if(date.contains("Jan")){
            JanEntity jan=janViewModel.getUserMonthsales(LoggeduserId);
            if(jan!=null){
                JanEntity janEntity = new JanEntity(jan.getKEY_SALES()+total, jan.getKEY_NO_OF_CLIENTS()+1, LoggeduserId, userdb);
                janEntity.setKEY_ID(jan.getKEY_ID());
                janViewModel.update(janEntity);
            }else {
                JanEntity janEntity = new JanEntity(total, 1, LoggeduserId, userdb);
                janViewModel.insert(janEntity);
            }
            companyTotals("Jan","Month");
            date="Feb";
        }
         if(date.contains("Feb")){
            FebEntity feb=febViewModel.getUserMonthSales(LoggeduserId);
            if(feb!=null){
                FebEntity febEntity=new FebEntity(feb.getKEY_SALES()+total,feb.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                febEntity.setKEY_ID(feb.getKEY_ID());
                febViewModel.update(febEntity);
            }else{
            FebEntity febEntity=new FebEntity(total,1,LoggeduserId,userdb);
            febViewModel.insert(febEntity);
            }
            companyTotals("Feb","Month");date="Mar";
            }
         if(date.contains("Mar")){
            MarEntity mar=marViewModel.getUserMonthSales(LoggeduserId);
            if(mar!=null){
                MarEntity marEntity=new MarEntity(mar.getKEY_SALES()+total,mar.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                marEntity.setKEY_ID(mar.getKEY_ID());
                marViewModel.update(marEntity);
            }else{
                MarEntity marEntity=new MarEntity(total,1,LoggeduserId,userdb);
                marViewModel.insert(marEntity);
            }
            companyTotals("Mar","Month");date="Apr";
        }
         if(date.contains("Apr")){//change boolean value
             AprEntity apr=aprViewModel.getUserMonthSales(LoggeduserId);
             if(apr!=null){
                 AprEntity aprEntity=new AprEntity(apr.getKEY_SALES()+total,apr.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 aprEntity.setKEY_ID(apr.getKEY_ID());
                 aprViewModel.update(aprEntity);
             }
             else{AprEntity aprEntity=new AprEntity(total,1,LoggeduserId,userdb);
                 aprViewModel.insert(aprEntity);}
            companyTotals("Apr","Month");date="May";
        }
         if(date.contains("May")){
             MayEntity may=mayViewModel.getUserMonthSales(LoggeduserId);
             if(may!=null){
                 MayEntity mayEntity=new MayEntity(may.getKEY_SALES()+total,may.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 mayEntity.setKEY_ID(may.getKEY_ID());
                 mayViewModel.update(mayEntity);
             }
             else{MayEntity mayEntity=new MayEntity(total,1,LoggeduserId,userdb);
                 mayViewModel.insert(mayEntity);}
            companyTotals("May","Month");date="Jun";
        }
         if(date.contains("Jun")){
             JunEntity jun=junViewModel.getUserMonthSales(LoggeduserId);
             if(jun!=null){
                 JunEntity junEntity=new JunEntity(jun.getKEY_SALES()+total,jun.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 junEntity.setKEY_ID(jun.getKEY_ID());
                 junViewModel.update(junEntity);
             }else{JunEntity junEntity=new JunEntity(total,1,LoggeduserId,userdb);
                 junViewModel.insert(junEntity);}
            companyTotals("Jun","Month");date="Jul";
        }
         if(date.contains("Jul")){
             JulEntity jul=julViewModel.getUserMonthSales(LoggeduserId);
             if(jul!=null){
                 JulEntity julEntity=new JulEntity(jul.getKEY_SALES()+total,jul.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 julEntity.setKEY_ID(jul.getKEY_ID());
                 julViewModel.update(julEntity);
             }else{JulEntity julEntity=new JulEntity(total,1,LoggeduserId,userdb);
                 julViewModel.insert(julEntity);}
            companyTotals("Jul","Month");date="Aug";
        }
         if(date.contains("Aug")){
             AugEntity aug=augViewModel.getUserMonthSales(LoggeduserId);
             if(aug!=null){
                 AugEntity augEntity=new AugEntity(aug.getKEY_SALES()+total,aug.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 augEntity.setKEY_ID(aug.getKEY_ID());
                 augViewModel.update(augEntity);
             }else{  AugEntity augEntity=new AugEntity(total,1,LoggeduserId,userdb);
                 augViewModel.insert(augEntity);}
            companyTotals("Aug","Month");date="Sep";
        }
         if(date.contains("Sep")){
             SepEntity sep=sepViewModel.getUserMonthSales(LoggeduserId);
             if(sep!=null){
                 SepEntity sepEntity=new SepEntity(sep.getKEY_SALES()+total,sep.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 sepEntity.setKEY_ID(sep.getKEY_ID());
                 sepViewModel.update(sepEntity);
             }else{SepEntity sepEntity=new SepEntity(total,1,LoggeduserId,userdb);
                 sepViewModel.insert(sepEntity);}
            companyTotals("Sep","Month");date="Oct";
        }
         if(date.contains("Oct")){
             OctEntity oct=octViewModel.getUserMonthSales(LoggeduserId);
             if(oct!=null){
                 OctEntity octEntity=new OctEntity(oct.getKEY_SALES()+total,oct.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 octEntity.setKEY_ID(oct.getKEY_ID());
                 octViewModel.update(octEntity);
             }else{OctEntity octEntity=new OctEntity(total,1,LoggeduserId,userdb);
                 octViewModel.insert(octEntity);}
            companyTotals("Oct","Month");date="Nov";
        }
         if(date.contains("Nov")){
             NovEntity nov=novViewModel.getUserMonthSales(LoggeduserId);
             if(nov!=null){
                 NovEntity novEntity=new NovEntity(nov.getKEY_SALES()+total,nov.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 novEntity.setKEY_ID(nov.getKEY_ID());
                 novViewModel.update(novEntity);
             }else{NovEntity novEntity=new NovEntity(total,1,LoggeduserId,userdb);
                 novViewModel.insert(novEntity);}
            companyTotals("Nov","Month");date="Dec";
        }
         if(date.contains("Dec")){
             DecEntity dec=decViewModel.getUserMonthsales(LoggeduserId);
             if(dec!=null){
                 DecEntity decEntity=new DecEntity(dec.getKEY_SALES()+total,dec.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 decEntity.setKEY_ID(dec.getKEY_ID());
                 decViewModel.update(decEntity);
             }else{
            DecEntity decEntity=new DecEntity(total,1,LoggeduserId,userdb);
            decViewModel.insert(decEntity);
             }
            companyTotals("Dec","Month");}

        date="Mon";
        if(date.contains("Mon")){
             MonEntity mon=monViewModel.getUserDaySales(LoggeduserId);
             if(mon!=null){
                 MonEntity monEntity=new MonEntity(mon.getKEY_SALES()+total,mon.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 monEntity.setKEY_ID(mon.getKEY_ID());
                 monViewModel.update(monEntity);
             }else{MonEntity monEntity=new MonEntity(total,1,LoggeduserId,userdb);
                 monViewModel.insert(monEntity);}
            companyTotals("Mon","Week");
            date="Tue";
         }
          if(date.contains("Tue")){
            TueEntity tue=tueViewModel.getUserDaySales(LoggeduserId);
            if(tue!=null){
                TueEntity tueEntity=new TueEntity(tue.getKEY_SALES()+total,tue.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                tueEntity.setKEY_ID(tue.getKEY_ID());
                tueViewModel.update(tueEntity);
            }else{TueEntity tueEntity=new TueEntity(total,1,LoggeduserId,userdb);
                tueViewModel.insert(tueEntity);}
            companyTotals("Tue","Week");date="Wed";
         }
          if(date.contains("Wed")){
             WedEntity wed=wedViewModel.getUserDaySales(LoggeduserId);
            if(wed!=null){
                WedEntity wedEntity=new WedEntity(wed.getKEY_SALES()+total,wed.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                wedEntity.setKEY_ID(wed.getKEY_ID());
                wedViewModel.update(wedEntity);
            }else{WedEntity wedEntity=new WedEntity(total,1,LoggeduserId,userdb);
                wedViewModel.insert(wedEntity);}
            companyTotals("Wed","Week");date="Thur";
         }
          if(date.contains("Thur")){
             ThurEntity thur=thurViewModel.getUserDaySales(LoggeduserId);
             if(thur!=null){
                 ThurEntity thurEntity=new ThurEntity(thur.getKEY_SALES()+total,thur.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 thurEntity.setKEY_ID(thur.getKEY_ID());
                 thurViewModel.update(thurEntity);
             }else{ThurEntity thurEntity=new ThurEntity(total,1,LoggeduserId,userdb);
                 thurViewModel.insert(thurEntity);}
            companyTotals("Thur","Week");date="Fri";
         }
          if(date.contains("Fri")){
            FriEntity fri=friViewModel.getUserDaySales(LoggeduserId);
            if(fri!=null){
                FriEntity friEntity=new FriEntity(fri.getKEY_SALES()+total,fri.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                friEntity.setKEY_ID(fri.getKEY_ID());
                friViewModel.update(friEntity);
            }else{FriEntity friEntity=new FriEntity(total,1,LoggeduserId,userdb);
                friViewModel.insert(friEntity);}
            companyTotals("Fri","Week");date="Sat";
         }
          if(date.contains("Sat")){
            SatEntity sat=satViewModel.getUserDaySales(LoggeduserId);
            if(sat!=null){
                SatEntity satEntity=new SatEntity(sat.getKEY_SALES()+total,sat.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                satEntity.setKEY_ID(sat.getKEY_ID());
                satViewModel.update(satEntity);
            }else{SatEntity satEntity=new SatEntity(total,1,LoggeduserId,userdb);
                satViewModel.insert(satEntity);}
            companyTotals("Sat","Week");date="Sun";
         }
          if(date.contains("Sun")){
             SunEntity sun=sunViewModel.getUserDaySales(LoggeduserId);
             if(sun!=null){
                 SunEntity sunEntity=new SunEntity(sun.getKEY_SALES()+total,sun.getKEY_NO_OF_CLIENTS()+1,LoggeduserId,userdb);
                 sunEntity.setKEY_ID(sun.getKEY_ID());
                 sunViewModel.update(sunEntity);
             }else{
                 SunEntity sunEntity=new SunEntity(total,1,LoggeduserId,userdb);
                 sunViewModel.insert(sunEntity);
             }
            companyTotals("Sun","Week");}

        }
    public int totalCount(){
        total=0;
        for(PickedGoodEntity good:pickedGoods){
            total=good.getKEY_total()+total;
        }
        return total;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.quanSpin:

                break;
            case R.id.spinner2:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void pdf(){
        String state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            File route=Environment.getExternalStorageDirectory();
            File dir=new File(route.getAbsolutePath()+"/DIGITALRECEIPTS","Receipts");

            if(!dir.exists()){
                dir.mkdirs();
            }
            Date date=new Date();
            SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy_HHmmss");
            timeStamp= currentDate.format(date);

             file=new File(dir,name+".pdf");
            try {
//creates pdf,saves and writes to it.
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                Document doc=new Document();
                PdfWriter.getInstance(doc,fileOutputStream );
                doc.open();
                shp=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                File d = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS","Logo");
                File g=new File(d,"/Logo.jpg");

                PdfPTable company=new PdfPTable(2);
                company.setWidthPercentage(60);
                PdfPCell logoCell=new PdfPCell();
                logoCell.setBorder(Rectangle.NO_BORDER);

                if(g.exists()){
                    try (FileInputStream stream = new FileInputStream(g)) {
                        Bitmap bit=BitmapFactory.decodeStream(stream);
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        Image image=Image.getInstance(byteArrayOutputStream.toByteArray());
                        //image.setAlignment(Image.ALIGN_LEFT);
                        //doc.add(image);
                        logoCell.addElement(image);
                        company.addCell(logoCell);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Paragraph Na=new Paragraph(shp.getString("nameKey","")+"\n"+shp.getString("emailKey","")+"\n"+"P.O. BOX"+
                        shp.getString("boxKey","")+ "\n"+shp.getString("locationKey","")+"\n"+
                        shp.getString("contactKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,18,Font.NORMAL));
   Paragraph Em=new Paragraph(shp.getString("emailKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));
                Paragraph Bo=new Paragraph("P.O. BOX"+shp.getString("boxKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));
                Paragraph Lo=new Paragraph(shp.getString("locationKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));
                Paragraph Co=new Paragraph(shp.getString("contactKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));

                Na.setAlignment(Element.ALIGN_RIGHT);
                Em.setAlignment(Element.ALIGN_RIGHT);
                Bo.setAlignment(Element.ALIGN_RIGHT);
                Lo.setAlignment(Element.ALIGN_RIGHT);
                Co.setAlignment(Element.ALIGN_RIGHT);
                doc.add(Na);
                doc.add(Em);
                doc.add(Bo);
                doc.add(Lo);
                doc.add(Co);
                doc.add(new Paragraph(new Date().toString()));


                PdfPCell Cell=new PdfPCell(new Paragraph(Na));
                company.addCell(Cell);
                doc.add(company);

                doc.add(new Paragraph(""));
                doc.add(new DottedLineSeparator());
                //doc.add(new Paragraph(timeStamp));
                //doc.addCreationDate();
                doc.add(new Paragraph("\nClient Details",FontFactory.getFont(FontFactory.TIMES_BOLD,14,Font.BOLD,BaseColor.DARK_GRAY)));
                doc.add(new Paragraph("NAME:"+name,FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("EMAIL:"+email,FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("N0   :"+no+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("PO.Box:"+pobox+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("Address:"+address+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("LOCATION:"+location+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph(new Date().toString()));
                doc.add(new Paragraph("Sale No:"+shp.getString("receiptKey","1")+".................................." +
                        "....No.of Items:"+pickedGoods.size(),FontFactory.getFont(FontFactory.TIMES_ROMAN,18,Font.NORMAL,BaseColor.DARK_GRAY)));
                doc.add(new LineSeparator());
                doc.add(new Paragraph("\n"));

                PdfPTable table=new PdfPTable(5);
                table.addCell("Quantity");
                table.addCell("Package");
                table.addCell("Item");
                table.addCell("@");
                table.addCell("Ksh");

                for(PickedGoodEntity good : pickedGoods){
                    String Q= String.valueOf(good.getKEY_Quantity());
                    String St=good.getKEY_Pack();
                    String T=good.getKEY_Item();
                    String E= String.valueOf(good.getKEY_Cost());
                    String t= String.valueOf(good.getKEY_total());

                    table.addCell(Q);
                    table.addCell(St);
                    table.addCell(T);
                    table.addCell(E);
                    table.addCell(t);
                    //doc.add(new Paragraph("\n"+itemList.get(i)));
                }
                doc.add(table);
                Paragraph noIt=new Paragraph("Total SH."+total+"\n");
                Paragraph cashT=new Paragraph("CASH TENDERED:"+pay);
                noIt.setAlignment(Element.ALIGN_RIGHT);
                cashT.setAlignment(Element.ALIGN_RIGHT);
                doc.add(noIt);
                doc.add(cashT);
                //pay used as int       NB:pay used as string above
                try{
                    int pay=Integer.parseInt(lipa.getText().toString());
                    Paragraph change=new Paragraph("CHANGE:"+(pay-total));
                    change.setAlignment(Element.ALIGN_RIGHT);
                    doc.add(change);
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }
                doc.close();
                fileOutputStream.close();
                receiptcount();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Please save logo first",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"stack trace2",Toast.LENGTH_SHORT).show();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"sdCard not mounted",Toast.LENGTH_SHORT).show();
        }
    }
    public void receiptcount(){
       String c=shp.getString("receiptKey","1");
        int g=Integer.parseInt(c);
        receiptCount=g+1;
        shp =getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putString("receiptKey", ""+receiptCount);
        editor.apply();

    }
    public void email(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "CASH SALE");
        Uri uri = Uri.fromFile(file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent,"Send Email"));
        }
    }

    public void inputDialog(int invCash){
        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity.this);
        View mview=getLayoutInflater().inflate(R.layout.activity_client_dialog,null);
        lipa=mview.findViewById(R.id.clCash);
        TextView bal = mview.findViewById(R.id.bal_display);

        bal.setText(String.valueOf(invCash));
        builder.setMessage(String.valueOf(invCash));
        builder.setView(mview);
        builder.setTitle("Cash Tendered");
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                 pay=lipa.getText().toString();
                 if(!pay.isEmpty()){
                     operationcomplete();
                 }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        lipa.setText(pay);

        AlertDialog dialog=builder.create();
        dialog.show();

    }
    public void editpickedinputDialog(final int key_id, String it, String pa, int co, final int q, final int pick_id, final int check) {
        final EditText item_edit, cost_edit, pack_edit,quan_edit;
        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.home_goods_dialog, null);
        item_edit = mview.findViewById(R.id.hitemtxt);
        cost_edit = mview.findViewById(R.id.hunittxt);
        pack_edit = mview.findViewById(R.id.hpacktxt);
        quan_edit=mview.findViewById(R.id.hquantxt);
        if(check==EDIT_CHECK_DIALOG){
            item_edit.setText(it);
            pack_edit.setText(pa);
            cost_edit.setText(String.valueOf(co));
            quan_edit.setText(String.valueOf(q));

            builder.setView(mview);
            builder.setTitle("Edit");
        }
        else{
            builder.setView(mview);
            builder.setTitle("Add Product");
        }
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String item = item_edit.getText().toString();
                int cost = Integer.parseInt(cost_edit.getText().toString());
                String pack = pack_edit.getText().toString();
                int newq= Integer.parseInt(quan_edit.getText().toString());

                int tot=cost*newq;
                PickedGoodEntity pickedGoodEntity = new PickedGoodEntity(newq,pack, item, cost,tot,pick_id);
                if(check==EDIT_CHECK_DIALOG){
                    pickedGoodEntity.setKEY_ID(key_id);
                    pickedGoodViewModel.update(pickedGoodEntity);
                }
                else{
                    pickedGoodViewModel.insert(pickedGoodEntity);
                }

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

    public void confirmdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(homeActivity.this);
        View mview=getLayoutInflater().inflate(R.layout.confirm_dialogbox,null);
        TextView mess = mview.findViewById(R.id.conf_text);
        mess.setText("CLEAR ALL SELECTED ITEMS");
        builder.setTitle("CONFIRM");
        builder.setView(mview);
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearpickeditems();
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

    private void clearpickeditems() {
        pickedGoodViewModel.deleteAll();
    }

    public void deleteAll(){
        janViewModel.deleteAll();febViewModel.deleteAll();marViewModel.deleteAll();
        aprViewModel.deleteAll();mayViewModel.deleteAll();junViewModel.deleteAll();
        julViewModel.deleteAll();augViewModel.deleteAll();sepViewModel.deleteAll();
        octViewModel.deleteAll();novViewModel.deleteAll();decViewModel.deleteAll();
        monViewModel.deleteAll();tueViewModel.deleteAll();wedViewModel.deleteAll();
        thurViewModel.deleteAll();friViewModel.deleteAll();satViewModel.deleteAll();
        sunViewModel.deleteAll();
    }
}
