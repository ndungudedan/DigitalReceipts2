package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

import com.example.dedan.digitalreceipts.Month_Database.December.DecViewModel;
import com.example.dedan.digitalreceipts.Month_Database.January.JanEntity;
import com.example.dedan.digitalreceipts.Month_Database.January.JanViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class User_StatsFragment2 extends Fragment {
    UserViewModel userViewModel;
    JanViewModel janViewModel;
    DecViewModel decViewModel;
    private TextView txtname,txtidno,txtmobile,txtresidence,txtemail,txtdob,txtYrSales,txtYrClients;
    TextView MSales,Mclients,currMonth;
    SharedPreferences sharedPreferences;
    ImageView imageView;
    Button mbtn;
    public static String BUTTON_PRESSED="MONTH";
    private OnFragmentInteractionListener mListener;


    public String Loggeduser;
    public String LoggeduserId;
    private int pick=1;
    private Uri uri;
    private Bitmap bitmap;
    private String currentMonth;

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

        sharedPreferences =getContext().getSharedPreferences("Data", MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        Loggeduser = sharedPreferences.getString("current_username", "");
        LoggeduserId= String.valueOf(sharedPreferences.getInt("current_userId",0));

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
        txtYrClients=view.findViewById(R.id.client_served);
        txtYrSales=view.findViewById(R.id.user_sales);
        MSales=view.findViewById(R.id.current_Msales);
        Mclients=view.findViewById(R.id.current_Mclient_served);
        currMonth=view.findViewById(R.id.current_month);
        mbtn=view.findViewById(R.id.seeMonths);

        currMonth.setText(currentMonth);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.changeFragment(2);
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        janViewModel= ViewModelProviders.of(this).get(JanViewModel.class);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        decViewModel=ViewModelProviders.of(this).get(DecViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                for(UserEntity user:userEntities){
                    if(Loggeduser.equals(user.getKEY_NAME())){
                        txtdob.setText(user.getKEY_DOB());
                        txtemail.setText(user.getKEY_EMAIL());
                        txtidno.setText(user.getKEY_NATNLID());
                        txtmobile.setText(user.getKEY_MOBILENO());
                        txtresidence.setText(user.getKEY_residence());
                        txtname.setText(user.getKEY_FIRSTNAME()+""+user.getKEY_SECNAME());
                        break;
                    }
                }
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick&&resultCode==RESULT_OK){
            uri=data.getData();

            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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

    public void statsLoad(){
        janViewModel.getAllJanEvents().observe(this, new Observer<List<JanEntity>>() {
            @Override
            public void onChanged(List<JanEntity> janEntities) {
                for(JanEntity jan: janEntities){
                    if(LoggeduserId.equals(jan.getKEY_FOREIGN_KEY())){
                        MSales.setText(String.valueOf(jan.getKEY_SALES()));
                        Mclients.setText(String.valueOf(jan.getKEY_NO_OF_CLIENTS()));
                        break;
                    }
                }
            }
        });
    }
    public interface OnFragmentInteractionListener {
        public void changeFragment(int id);
    }
}
