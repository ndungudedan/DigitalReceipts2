package com.example.dedan.digitalreceipts;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContextWrapper;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.August.AugViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.December.DecViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.February.FebViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.January.JanViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.July.JulViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.June.JunViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.March.MarViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.November.NovViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.October.OctViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.September.SepViewModel;
import com.example.dedan.digitalreceipts.Database.Today_Database.UserStatsMonthAdapter;
import com.example.dedan.digitalreceipts.Database.Today_Database.UserStatsMonthEntity;
import com.example.dedan.digitalreceipts.Database.Today_Database.UserStatsMonthViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Monday.MonViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Sunday.SunViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Thursday.ThurViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Tuesday.TueViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Wednesday.WedViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class security extends AppCompatActivity {
    UserViewModel userViewModel;
    UserEntity user;
    JanViewModel janViewModel;
    FebViewModel febViewModel;
    MarViewModel marViewModel;
    AprViewModel aprViewModel;
    MayViewModel mayViewModel;
    JunViewModel junViewModel;
    JulViewModel julViewModel;
    AugViewModel augViewModel;
    SepViewModel sepViewModel;
    OctViewModel octViewModel;
    NovViewModel novViewModel;
    DecViewModel decViewModel;
    MonViewModel monViewModel;
    TueViewModel tueViewModel;
    WedViewModel wedViewModel;
    ThurViewModel thurViewModel;
    FriViewModel friViewModel;
    SatViewModel satViewModel;
    SunViewModel sunViewModel;
    UserStatsViewModel userStatsViewModel;
    UserStatsMonthViewModel userStatsMonthViewModel;
    private TextView txtname,txtidno,txtmobile,txtresidence,txtemail,txtdob,txtstatus,txtYrSales,txtYrClients;
    private int total_sales=0,total_clients=0;


    SharedPreferences sharedPreferences;
    ImageView imageView;
    Button accessbtn;

    public String Loggeduser;
    public String LoggeduserId;
    private int pick=1;
    private Uri uri;
    private Bitmap bitmap;
    private String currentMonth;
    String time;
    private UserStatsAdapter userStatsAdapter;
    private UserStatsMonthAdapter userStatsMonthAdapter;
    private Intent intent;
    TextView nulltxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        imageView=findViewById(R.id.profilePic);
        txtname=findViewById(R.id.Name);
        txtemail=findViewById(R.id.memail);
        txtdob=findViewById(R.id.DOB);
        txtidno=findViewById(R.id.idno);
        txtresidence=findViewById(R.id.mresidence);
        txtmobile=findViewById(R.id.mobile);
        txtstatus=findViewById(R.id.mstatus);
        accessbtn =findViewById(R.id.access_btn);
        txtYrClients=findViewById(R.id.client_served);
        txtYrSales=findViewById(R.id.user_sales);
        nulltxt=findViewById(R.id.empty_txt);

        userStatsViewModel=ViewModelProviders.of(this).get(UserStatsViewModel.class);
        userStatsMonthViewModel=ViewModelProviders.of(this).get(UserStatsMonthViewModel.class);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        janViewModel= ViewModelProviders.of(this).get(JanViewModel.class);febViewModel=ViewModelProviders.of(this).get(FebViewModel.class);
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
        Date date=new Date();
        String monthformat="MMM";
        SimpleDateFormat sdf=new SimpleDateFormat(monthformat);
        sdf.format(date);
        currentMonth = date.toString();
        time =date.toString();

        sharedPreferences =this.getSharedPreferences("Data", MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        Loggeduser = sharedPreferences.getString("current_username", "");

        intent =getIntent();
        if(intent.hasExtra("userid")){
            LoggeduserId= String.valueOf(intent.getIntExtra("userid",0));
            accessbtn.setVisibility(View.VISIBLE);
            accessbtn.setClickable(true);
        }
        else {
            LoggeduserId = String.valueOf(sharedPreferences.getInt("current_userId", 0));
        }

        RecyclerView recyclerView=findViewById(R.id.comp_week_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
        userStatsAdapter=new UserStatsAdapter();
        recyclerView.setAdapter(userStatsAdapter);

        RecyclerView recycler2=findViewById(R.id.comp_month_recycler);
        recycler2.setLayoutManager(new GridLayoutManager(this,4));
        recycler2.setHasFixedSize(true);
        userStatsMonthAdapter=new UserStatsMonthAdapter();
        recycler2.setAdapter(userStatsMonthAdapter);

        accessbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getKEY_ACCESS().equals("ACCESS_GRANTED")){
                    UserEntity userEntity=new UserEntity(user.getKEY_FIRSTNAME(),user.getKEY_SECNAME(),user.getKEY_empNO(),user.getKEY_DOB(),
                            user.getKEY_residence(),user.getKEY_MOBILENO(),user.getKEY_NAME(),user.getKEY_EMAIL(),user.getKEY_PASS(),user.getKEY_NATNLID(),
                            user.getKEY_LOG(),"ACCESS_DENIED",user.getKEY_PIC());
                    userEntity.setKEY_USER_ID(user.getKEY_USER_ID());
                    userViewModel.update(userEntity);
                }
                else{
                    UserEntity userEntity=new UserEntity(user.getKEY_FIRSTNAME(),user.getKEY_SECNAME(),user.getKEY_empNO(),user.getKEY_DOB(),
                            user.getKEY_residence(),user.getKEY_MOBILENO(),user.getKEY_NAME(),user.getKEY_EMAIL(),user.getKEY_PASS(),user.getKEY_NATNLID(),
                            user.getKEY_LOG(),"ACCESS_GRANTED",user.getKEY_PIC());
                    userEntity.setKEY_USER_ID(user.getKEY_USER_ID());
                    userViewModel.update(userEntity);
                }
            }
        });

        userViewModel.getAllUsers().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                for(UserEntity usr:userEntities){
                    if(usr.getKEY_USER_ID()==Integer.parseInt(LoggeduserId)){
                        txtdob.setText(usr.getKEY_DOB());
                        txtemail.setText(usr.getKEY_EMAIL());
                        txtidno.setText(usr.getKEY_NATNLID());
                        txtmobile.setText(usr.getKEY_MOBILENO());
                        txtresidence.setText(usr.getKEY_residence());
                        txtname.setText(usr.getKEY_FIRSTNAME()+""+usr.getKEY_SECNAME());
                        txtstatus.setText(usr.getKEY_ACCESS());
                        if(usr.getKEY_PIC()!=null){
                            retreivePic(usr.getKEY_PIC());
                        }
                        if(usr.getKEY_ACCESS().equals("ACCESS_GRANTED")){
                            accessbtn.setText("INVALIDATE");
                        }
                        else if(usr.getKEY_ACCESS().equals("ACCESS_DENIED")){
                            accessbtn.setText("AUTHORISE");
                        }
                        user=usr;
                        break;
                    }
                }
            }
        });
        weekStatsPopul();
        monthStatsPopul();
        userStatsViewModel= ViewModelProviders.of(this).get(UserStatsViewModel.class);
        userStatsViewModel.getAllstats().observe(this, new Observer<List<UserStatsEntity>>() {
            @Override
            public void onChanged(List<UserStatsEntity> userStatsEntities) {
                userStatsAdapter.submitList(userStatsEntities);
                if(userStatsEntities.isEmpty()){
                    nulltxt.setVisibility(View.VISIBLE);
                    nulltxt.setText("Nothing to display");
                }
            }
        });
        userStatsMonthViewModel=ViewModelProviders.of(this).get(UserStatsMonthViewModel.class);
        userStatsMonthViewModel.AllTodayEvents().observe(this, new Observer<List<UserStatsMonthEntity>>() {
            @Override
            public void onChanged(List<UserStatsMonthEntity> userStatsMonthEntities) {
                userStatsMonthAdapter.submitList(userStatsMonthEntities);
            }
        });

        txtYrSales.setText(String.valueOf(total_sales));
        txtYrClients.setText(String.valueOf(total_clients));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"pictures"),pick);
            }
        });
    }
    private void retreivePic(String path) {
        try{
            File file=new File(path,"pic.jpg");
            Bitmap bit= BitmapFactory.decodeStream(new FileInputStream(file));
            imageView.setImageBitmap(bit);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick&&resultCode==RESULT_OK){
            uri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
                profilepic(bitmap);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        MenuItem item = menu.findItem(R.id.toolspinner);

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
    public void monthStatsPopul(){
        if(janViewModel.getUserMonthsales(LoggeduserId)!=null) {
            UserStatsMonthEntity janStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Jan", janViewModel.getUserMonthsales(LoggeduserId).getKEY_SALES(),
                    janViewModel.getUserMonthsales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(janStat);
            total_sales=total_sales+janStat.getKEY_MONTH_SALES();
            total_clients=total_clients+janStat.getKEY_M_CLIENTS_SERVED();
        }
        if(febViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity febStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", febViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    febViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(febStat);
            total_sales=total_sales+febStat.getKEY_MONTH_SALES();
            total_clients=total_clients+febStat.getKEY_M_CLIENTS_SERVED();
        }
        if(marViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity marStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", marViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    marViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(marStat);
            total_sales=total_sales+marStat.getKEY_MONTH_SALES();
            total_clients=total_clients+marStat.getKEY_M_CLIENTS_SERVED();
        }
        if(aprViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity aprStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", aprViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    aprViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(aprStat);
            total_sales=total_sales+aprStat.getKEY_MONTH_SALES();
            total_clients=total_clients+aprStat.getKEY_M_CLIENTS_SERVED();
        }
        if(mayViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity mayStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", mayViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    mayViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(mayStat);
            total_sales=total_sales+mayStat.getKEY_MONTH_SALES();
            total_clients=total_clients+mayStat.getKEY_M_CLIENTS_SERVED();
        }
        if(junViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity junStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", junViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    junViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(junStat);
            total_sales=total_sales+junStat.getKEY_MONTH_SALES();
            total_clients=total_clients+junStat.getKEY_M_CLIENTS_SERVED();
        }
        if(julViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity julStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", julViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    julViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(julStat);
            total_sales=total_sales+julStat.getKEY_MONTH_SALES();
            total_clients=total_clients+julStat.getKEY_M_CLIENTS_SERVED();
        }
        if(augViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity augStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", augViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    augViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(augStat);
            total_sales=total_sales+augStat.getKEY_MONTH_SALES();
            total_clients=total_clients+augStat.getKEY_M_CLIENTS_SERVED();
        }
        if(sepViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity sepStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", sepViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    sepViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(sepStat);
            total_sales=total_sales+sepStat.getKEY_MONTH_SALES();
            total_clients=total_clients+sepStat.getKEY_M_CLIENTS_SERVED();
        }
        if(octViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity octStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", octViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    octViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(octStat);
            total_sales=total_sales+octStat.getKEY_MONTH_SALES();
            total_clients=total_clients+octStat.getKEY_M_CLIENTS_SERVED();
        }
        if(novViewModel.getUserMonthSales(LoggeduserId)!=null) {
            UserStatsMonthEntity novStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", novViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                    novViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(novStat);
            total_sales=total_sales+novStat.getKEY_MONTH_SALES();
            total_clients=total_clients+novStat.getKEY_M_CLIENTS_SERVED();
        }
        if(decViewModel.getUserMonthsales(LoggeduserId)!=null) {
            UserStatsMonthEntity decStat = new UserStatsMonthEntity("sunday", 0, "week 27",
                    0, "Apr", decViewModel.getUserMonthsales(LoggeduserId).getKEY_SALES(),
                    decViewModel.getUserMonthsales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsMonthViewModel.insert(decStat);
            total_sales=total_sales+decStat.getKEY_MONTH_SALES();
            total_clients=total_clients+decStat.getKEY_M_CLIENTS_SERVED();
        }
    }
    public void weekStatsPopul(){
        int x=monViewModel.getUserDaySales(LoggeduserId).getKEY_SALES();
        if(monViewModel.getUserDaySales(LoggeduserId)!=null){
            UserStatsEntity monStat=new UserStatsEntity("sunday",0,"week 27",
                    0,"Apr",monViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                    monViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
            userStatsViewModel.insert(monStat);
        }
        if(tueViewModel.getUserDaySales(LoggeduserId)!=null) {
            UserStatsEntity tueStat = new UserStatsEntity("sunday", 0, "week 27",
                    0, "Apr", tueViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                    tueViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsViewModel.insert(tueStat);
        }
        if(wedViewModel.getUserDaySales(LoggeduserId)!=null) {
            UserStatsEntity wedStat = new UserStatsEntity("sunday", 0, "week 27",
                    0, "Apr", wedViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                    wedViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsViewModel.insert(wedStat);
        }
        if(thurViewModel.getUserDaySales(LoggeduserId)!=null) {
            UserStatsEntity thurStat = new UserStatsEntity("sunday", 0, "week 27",
                    0, "Apr", thurViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                    thurViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsViewModel.insert(thurStat);
        }
        if(friViewModel.getUserDaySales(LoggeduserId)!=null) {
            UserStatsEntity friStat = new UserStatsEntity("sunday", 0, "week 27",
                    0, "Apr", friViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                    friViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsViewModel.insert(friStat);
        }
        if(satViewModel.getUserDaySales(LoggeduserId)!=null) {
            UserStatsEntity satStat = new UserStatsEntity("sunday", 0, "week 27",
                    0, "Apr", satViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                    satViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsViewModel.insert(satStat);
        }
        if(sunViewModel.getUserDaySales(LoggeduserId)!=null) {
            UserStatsEntity sunStat = new UserStatsEntity("sunday", 0, "week 27",
                    0, "Apr", sunViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                    sunViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(), 0, Integer.parseInt(LoggeduserId));
            userStatsViewModel.insert(sunStat);
        }
    }
    public void profilepic(Bitmap bit){
        ContextWrapper cw=new ContextWrapper(this);
        File dir=cw.getDir("profilepic", MODE_PRIVATE);
        File file=new File(dir,"pic.jpg");
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
        UserEntity userEntity=new UserEntity(user.getKEY_FIRSTNAME(),user.getKEY_SECNAME(),user.getKEY_empNO(),user.getKEY_DOB(),
                user.getKEY_residence(),user.getKEY_MOBILENO(),user.getKEY_NAME(),user.getKEY_EMAIL(),user.getKEY_PASS(),user.getKEY_NATNLID(),
                user.getKEY_LOG(),user.getKEY_ACCESS(),dir.getAbsolutePath());
        userEntity.setKEY_USER_ID(user.getKEY_USER_ID());
        userViewModel.update(userEntity);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userStatsViewModel.deleteAll();
        userStatsMonthViewModel.deleteAll();
    }
}