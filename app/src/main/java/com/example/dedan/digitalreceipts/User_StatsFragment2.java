package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.dedan.digitalreceipts.Database.Today_Database.TodayEntity;
import com.example.dedan.digitalreceipts.Database.Today_Database.TodayViewModel;
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

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class User_StatsFragment2 extends Fragment {
    UserViewModel userViewModel;
    UserEntity user;
    JanViewModel janViewModel;FebViewModel febViewModel;MarViewModel marViewModel;
    AprViewModel aprViewModel;MayViewModel mayViewModel;JunViewModel junViewModel;
    JulViewModel julViewModel;AugViewModel augViewModel;SepViewModel sepViewModel;
    OctViewModel octViewModel;NovViewModel novViewModel;DecViewModel decViewModel;
    MonViewModel monViewModel;TueViewModel tueViewModel;WedViewModel wedViewModel;
    ThurViewModel thurViewModel;FriViewModel friViewModel;SatViewModel satViewModel;SunViewModel sunViewModel;
    UserStatsViewModel userStatsViewModel;
    TodayViewModel todayViewModel;
    private TextView txtname,txtidno,txtmobile,txtresidence,txtemail,txtdob,txtstatus,txtYrSales,txtYrClients;
    TextView MSales,Mclients,currMonth;
    TextView WSales,Wclients,currWeek;
    TextView TSales,Tclients,today;


    SharedPreferences sharedPreferences;
    ImageView imageView;
    Button mbtn,wbtn, accessbtn;
    public static String BUTTON_PRESSED="MONTH";
    private OnFragmentInteractionListener mListener;


    public String Loggeduser;
    public String LoggeduserId;
    private int pick=1;
    private Uri uri;
    private Bitmap bitmap;
    private String currentMonth;
    String time;

    public User_StatsFragment2() {
        // Required empty public constructor
    }

    public static User_StatsFragment2 newInstance() {
        return new User_StatsFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Date date=new Date();
        String monthformat="MMM";
        SimpleDateFormat sdf=new SimpleDateFormat(monthformat);
        sdf.format(date);
        currentMonth = date.toString();
        time =date.toString();

        sharedPreferences =getContext().getSharedPreferences("Data", MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        Loggeduser = sharedPreferences.getString("current_username", "");

        Intent i=getActivity().getIntent();
        if(i.hasExtra("userid")){
            LoggeduserId= String.valueOf(i.getIntExtra("userid",0));
        }
        else {
            LoggeduserId = String.valueOf(sharedPreferences.getInt("current_userId", 0));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user__stats_fragment2, container, false);
        imageView=view.findViewById(R.id.profilePic);
        txtname=view.findViewById(R.id.Name);
        txtemail=view.findViewById(R.id.memail);
        txtdob=view.findViewById(R.id.DOB);
        txtidno=view.findViewById(R.id.idno);
        txtresidence=view.findViewById(R.id.mresidence);
        txtmobile=view.findViewById(R.id.mobile);
        txtstatus=view.findViewById(R.id.mstatus);
        accessbtn =view.findViewById(R.id.access_btn);

        txtYrClients=view.findViewById(R.id.client_served);
        txtYrSales=view.findViewById(R.id.user_sales);

        MSales=view.findViewById(R.id.current_Msales);
        Mclients=view.findViewById(R.id.current_Mclient_served);
        currMonth=view.findViewById(R.id.current_month);
        mbtn=view.findViewById(R.id.seeMonths);

        WSales=view.findViewById(R.id.current_Wsales);
        Wclients=view.findViewById(R.id.current_Wclient_served);
        currWeek=view.findViewById(R.id.current_week);
        wbtn=view.findViewById(R.id.seeWeeks);

        TSales=view.findViewById(R.id.tod_sales);
        Tclients=view.findViewById(R.id.tod_client_served);
        today=view.findViewById(R.id.today);

        currMonth.setText(currentMonth);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthStatsPopul();
                mListener.changeFragment(2);
            }
        });
        wbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekStatsPopul();
                mListener.changeFragment(2);
            }
        });
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

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userStatsViewModel=ViewModelProviders.of(this).get(UserStatsViewModel.class);
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
        todayViewModel=ViewModelProviders.of(this).get(TodayViewModel.class);


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
                        if(usr.getKEY_ACCESS()=="ACCESS_GRANTED"){
                            accessbtn.setText("INVALIDATE");
                        }
                        else{
                            accessbtn.setText("AUTHORISE");
                        }
                        user=usr;
                        break;
                    }
                }
            }
        });
        weekstatsLoad();
        monthstatsLoad();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick&&resultCode==RESULT_OK){
            uri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
                profilepic(bitmap);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void monthstatsLoad(){
        todayViewModel.AllTodayEvents().observe(this, new Observer<List<TodayEntity>>() {
            @Override
            public void onChanged(List<TodayEntity> todayEntities) {
                Toast.makeText(getActivity(),""+todayEntities.size(),Toast.LENGTH_SHORT).show();
            }
        });
        if(todayViewModel.getUserTodaySales(LoggeduserId)!=null){
            today.setText(String.valueOf(todayViewModel.getUserTodaySales(LoggeduserId).getKEY_DAY()));
            TSales.setText(String.valueOf(todayViewModel.getUserTodaySales(LoggeduserId).getKEY_SALES()));
            Tclients.setText(String.valueOf(todayViewModel.getUserTodaySales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Jan")){
            currMonth.setText("Jan");
            MSales.setText(String.valueOf(janViewModel.getUserMonthsales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(janViewModel.getUserMonthsales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Feb")){
            currMonth.setText("Feb");
            MSales.setText(String.valueOf(febViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(febViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Mar")){
            currMonth.setText("Mar");
            MSales.setText(String.valueOf(marViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(marViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Apr")){//change boolean value
            currMonth.setText("Apr");
            MSales.setText(String.valueOf(aprViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(aprViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("May")){
            currMonth.setText("May");
            MSales.setText(String.valueOf(mayViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(mayViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Jun")){
            currMonth.setText("Jun");
            MSales.setText(String.valueOf(junViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(junViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Jul")){
            currMonth.setText("Jul");
            MSales.setText(String.valueOf(julViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(julViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Aug")){
            currMonth.setText("Mar");
            MSales.setText(String.valueOf(augViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(augViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Sep")){
            currMonth.setText("Sep");
            MSales.setText(String.valueOf(sepViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(sepViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Oct")){
            currMonth.setText("Oct");
            MSales.setText(String.valueOf(octViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(octViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Nov")){
            currMonth.setText("Nov");
            MSales.setText(String.valueOf(novViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(novViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Dec")){
            currMonth.setText("Dec");
            MSales.setText(String.valueOf(decViewModel.getUserMonthsales(LoggeduserId).getKEY_SALES()));
            Mclients.setText(String.valueOf(decViewModel.getUserMonthsales(LoggeduserId).getKEY_NO_OF_CLIENTS()));


    }
    }
    public void weekstatsLoad(){
        if(time.contains("Mon")){
        currWeek.setText("Mon");
        WSales.setText(String.valueOf(monViewModel.getUserDaySales(LoggeduserId).getKEY_SALES()));
        Wclients.setText(String.valueOf(monViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Tue")){
            currWeek.setText("Tue");
            WSales.setText(String.valueOf(tueViewModel.getUserDaySales(LoggeduserId).getKEY_SALES()));
            Wclients.setText(String.valueOf(tueViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Wed")){
            currWeek.setText("Wed");
            WSales.setText(String.valueOf(wedViewModel.getUserDaySales(LoggeduserId).getKEY_SALES()));
            Wclients.setText(String.valueOf(wedViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Thur")){
            currWeek.setText("Thur");
            WSales.setText(String.valueOf(thurViewModel.getUserDaySales(LoggeduserId).getKEY_SALES()));
            Wclients.setText(String.valueOf(thurViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Fri")){
            currWeek.setText("Fri");
            WSales.setText(String.valueOf(friViewModel.getUserDaySales(LoggeduserId).getKEY_SALES()));
            Wclients.setText(String.valueOf(friViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Sat")){
            currWeek.setText("Sat");
            WSales.setText(String.valueOf(satViewModel.getUserDaySales(LoggeduserId).getKEY_SALES()));
            Wclients.setText(String.valueOf(satViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }
        if(time.contains("Sun")){
            currWeek.setText("Sun");
            WSales.setText(String.valueOf(sunViewModel.getUserDaySales(LoggeduserId).getKEY_SALES()));
            Wclients.setText(String.valueOf(sunViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS()));
        }}
    public void monthStatsPopul(){
        UserStatsEntity janStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Jan",janViewModel.getUserMonthsales(LoggeduserId).getKEY_SALES(),
                janViewModel.getUserMonthsales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(janStat);
        UserStatsEntity febStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",febViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                febViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(febStat);
        UserStatsEntity marStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",marViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                marViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(marStat);
        UserStatsEntity aprStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",aprViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                aprViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(aprStat);
        UserStatsEntity mayStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",mayViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                mayViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(mayStat);
        UserStatsEntity junStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",junViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                junViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(junStat);
        UserStatsEntity julStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",julViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                julViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(julStat);
        UserStatsEntity augStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",augViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                augViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(augStat);
        UserStatsEntity sepStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",sepViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                sepViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(sepStat);
        UserStatsEntity octStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",octViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                octViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(octStat);
        UserStatsEntity novStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",novViewModel.getUserMonthSales(LoggeduserId).getKEY_SALES(),
                novViewModel.getUserMonthSales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(novStat);
        UserStatsEntity decStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",decViewModel.getUserMonthsales(LoggeduserId).getKEY_SALES(),
                decViewModel.getUserMonthsales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(decStat);

    }
    public void weekStatsPopul(){
        UserStatsEntity monStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",monViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                monViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(monStat);
        UserStatsEntity tueStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",tueViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                tueViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(tueStat);
        UserStatsEntity wedStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",wedViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                wedViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(wedStat);
        UserStatsEntity thurStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",thurViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                thurViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(thurStat);
        UserStatsEntity friStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",friViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                friViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(friStat);
        UserStatsEntity satStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",satViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                satViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(satStat);
        UserStatsEntity sunStat=new UserStatsEntity("sunday",0,"week 27",
                0,"Apr",sunViewModel.getUserDaySales(LoggeduserId).getKEY_SALES(),
                sunViewModel.getUserDaySales(LoggeduserId).getKEY_NO_OF_CLIENTS(),0,Integer.parseInt(LoggeduserId));
        userStatsViewModel.insert(sunStat);
    }
    public interface OnFragmentInteractionListener {
        public void changeFragment(int id);
    }
    public void editUser(){
        Intent intent=new Intent(getActivity(),Register.class);
        intent.putExtra("userId",LoggeduserId);
        startActivity(intent);
    }
    public void profilepic(Bitmap bit){
        ContextWrapper cw=new ContextWrapper(getActivity());
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
}
