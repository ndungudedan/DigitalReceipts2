package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;


public class secFragment extends Fragment implements View.OnClickListener {
    EditText user_fName,user_scName,user_idNo,user_DOB,user_resid,user_mobileNo,user_email;
    TextInputEditText txt_pass;
    EditText txt_user;
    TextView user_empno;
    Cursor mcursor;
    security security;
    SharedPreferences sharedPreferences;
    public String user;
    public long hid=1;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static secFragment fragment;
    String accessCheck=null;
    RelativeLayout relative;
    private Button authenticate;
    private Button invalidate;

    private FirebaseAuth mAuth;
    public secFragment() {
    }

    public static Fragment newInstance(long param1,String userCheck) {
        fragment = new secFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2,userCheck);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getContext().getSharedPreferences("Data",MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        user = sharedPreferences.getString("current_username","");
        security=new security();
        mAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {
            hid = getArguments().getLong(ARG_PARAM1);
            accessCheck= getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //container.removeAllViews();
        //container.removeAllViewsInLayout();

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sec, container, false);
        RelativeLayout relativeLayout=view.findViewById(R.id.rel);
        txt_user = view.findViewById(R.id.user_txt);
        txt_pass =view. findViewById(R.id.pass_txt);
        user_fName=view.findViewById(R.id.user_first_name);
        user_scName=view.findViewById(R.id.user_sec_name);
        user_DOB=view.findViewById(R.id.user_dob);
        user_idNo=view.findViewById(R.id.user_id_no);
        user_mobileNo=view.findViewById(R.id.user_tel_no);
        user_resid=view.findViewById(R.id.user_residence);
        user_email=view.findViewById(R.id.user_email_reg);
        //user_empno=view.findViewById(R.id.empno_text);
        authenticate = view.findViewById(R.id.auth_btn);
        invalidate = view.findViewById(R.id.inval_btn);
        Button edit= view.findViewById(R.id.edit_btn);
        Button save= view.findViewById(R.id.save_btn);

        if(hid>1&user.startsWith("ADMIN")){
            relativeLayout.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);

            if(accessCheck.equals("ACCESS_GRANTED")){
                invalidate.setVisibility(View.VISIBLE);
                invalidate.setOnClickListener(this);
                authenticate.setVisibility(View.INVISIBLE);
            }
            else{
                authenticate.setVisibility(View.VISIBLE);
                invalidate.setVisibility(View.INVISIBLE);
                authenticate.setOnClickListener(this);
            }
        }
        else{
            authenticate.setVisibility(View.INVISIBLE);
            invalidate.setVisibility(View.INVISIBLE);
        }
        edit.setOnClickListener(this);
        save.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //to cater for the admin and user logins
        if(!user.startsWith("ADMIN")){

        }
        else{

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onResume() {
        super.onResume();
        if(getArguments()!=null){
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    public void fragload(long passedid) {
        String n=null ,y=null,c=null,l=null,f=null,h=null,k=null,b=null,t=null,s=null,w=null;
        String passeduser = user;

                }
    private boolean validateForm() {
        boolean valid = true;

        String emailtxt = user_email.getText().toString();
        if (TextUtils.isEmpty(emailtxt)) {
            user_email.setError("Required.");
            valid = false;
        }
        else {
            user_email.setError(null);
        }

        String password = txt_pass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            txt_pass.setError("Required.");
            valid = false;
        }
        if(password.length()<6){
            txt_pass.setError("Not Less Than 6 Characters.");
            valid = false;
        }else {
            txt_pass.setError(null);
        }
        if(txt_user.getText().toString().isEmpty()){
            txt_user.setError("Required");
        }else{txt_user.setError(null);}
        if(user_fName.getText().toString().isEmpty()){
            user_fName.setError("Required");
        }else{user_fName.setError(null);}
        if(user_scName.getText().toString().isEmpty()){
            user_scName.setError("Required");
        }else{user_scName.setError(null);}
        if(user_idNo.getText().toString().isEmpty()){
            user_idNo.setError("Required");
        }else{user_idNo.setError(null);}
        if(user_resid.getText().toString().isEmpty()){
            user_resid.setError("Required");
        }else{user_resid.setError(null);}
        if(user_mobileNo.getText().toString().isEmpty()){
            user_mobileNo.setError("Required");
        }else{user_mobileNo.setError(null);}

        return valid;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_btn:
                setEditable();
                break;
            case R.id.save_btn:
                if (!validateForm()) {
                    return;
                }
                update();
                notEditable();
                break;
            case R.id.auth_btn:
                authorise();
                break;
            case R.id.inval_btn:
                invalidate();
                break;
        }
    }
    public void update(){
        if(internetIsConnected()){
            FirebaseUser fireuser = FirebaseAuth.getInstance().getCurrentUser();
            String newPassword =txt_pass.getText().toString().trim();
            fireuser.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "password updated",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), ""+e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            fireuser.updateEmail(user_email.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "email updated",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), ""+e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

           //update local database
        }
        else{
            Toast.makeText(getActivity(), "failed!!!No network access",
                    Toast.LENGTH_SHORT).show();
        }

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
        user_idNo.setEnabled(false);
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
    public void invalidate() {

    }
    public void authorise() {

    }
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return (Runtime.getRuntime().exec(command).waitFor(5, TimeUnit.SECONDS));
            }
            return (Runtime.getRuntime().exec(command).waitFor()==0);
        } catch (Exception e) {
            return false;
        }
    }
}
