package com.example.dedan.digitalreceipts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    private UserViewModel userViewModel;
    private LiveData<List<UserEntity>> allUsers;
    private onlineDb onlineDb;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    EditText fName,scName, idN0,DOB,resid,mobileNo;
    EditText user;
    TextInputEditText pass;
    EditText email;
    int empNO =000;
    int editID=0;
    private TextInputLayout inputName,inputsecname,inputId,inputDOB,inputresidence,inputNo,inputUsername,inputPass,inputemail;
    private UserEntity checkUserEntity=null;
    FirebaseFirestore firedb = FirebaseFirestore.getInstance();
    private String userfname;
    private String userscName;
    private String usernationalID;
    private String userDOB;
    private String userresid;
    private String usermobile;
    private String username;
    private String userpass;
    private String useremail;
    private String access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userViewModel= ViewModelProviders.of(Register.this).get(UserViewModel.class);
        onlineDb=new onlineDb(firedb);
        sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);

        user=findViewById(R.id.user_reg);
        pass=findViewById(R.id.pass_reg);
        email=findViewById(R.id.email_reg);
        fName=findViewById(R.id.first_name);
        scName=findViewById(R.id.sec_name);
        idN0=findViewById(R.id.id_no);
        DOB=findViewById(R.id.dob);
        resid=findViewById(R.id.residence);
        mobileNo=findViewById(R.id.tel_no);


        inputName=findViewById(R.id.first_name_inputlayout);
        inputsecname=findViewById(R.id.sec_name_inputLayout);
        inputId=findViewById(R.id.id_no_inputLayout);
        inputDOB=findViewById(R.id.dob_inputLayout);
        inputresidence=findViewById(R.id.residence_inputLayout);
        inputNo=findViewById(R.id.tel_no_inputLayout);
        inputUsername=findViewById(R.id.user_reg_inputLayout);
        inputPass=findViewById(R.id.pass_reg_inputLayout);
        inputemail=findViewById(R.id.email_reg_inputLayout);
        mAuth = FirebaseAuth.getInstance();

        Intent intent=getIntent();
        if(intent.hasExtra("userId")){
            editID=Integer.parseInt(getIntent().getStringExtra("userId"));
            userViewModel.getAllUsers().observe(this, new Observer<List<UserEntity>>() {
                @Override
                public void onChanged(List<UserEntity> userEntities) {
                    for(UserEntity user:userEntities){
                        if(user.getKEY_USER_ID()==editID){
                            DOB.setText(user.getKEY_DOB());
                            email.setText(user.getKEY_EMAIL());
                            idN0.setText(user.getKEY_NATNLID());
                            mobileNo.setText(user.getKEY_MOBILENO());
                            resid.setText(user.getKEY_residence());
                            fName.setText(user.getKEY_FIRSTNAME());
                            scName.setText(user.getKEY_SECNAME());
                            break;
                        }
                    }
                }
            });
        }

        DOB_picker();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } }
    public void register(View view) {
        userfname = fName.getText().toString();
        userscName = scName.getText().toString();
        usernationalID = idN0.getText().toString();
        userDOB = DOB.getText().toString();
        userresid = resid.getText().toString();
        usermobile = mobileNo.getText().toString();

        username = user.getText().toString().trim();
        userpass = pass.getText().toString().trim();
        useremail = email.getText().toString().trim();
        access = "ACCESS_DENIED";

        if(!sharedPreferences.getBoolean("firstRegistration", false)) {
            username ="ADMIN_"+ username;
            access ="ACCESS_GRANTED";
        }
        if (!validateForm()) {
            return;
        }

        Intent intent=getIntent();
        if(intent.hasExtra("userId")){
            update();
        }
        else{
        mAuth.createUserWithEmailAndPassword(useremail, userpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            regUser();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Registration Success.",
                                    Toast.LENGTH_SHORT).show();

                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Register.this, "Verification Email sent.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, ""+e.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        //place above code in amethod and put ti nin firebase onsucces
        }
    }
    public void regUser(){
        onlineDb.registerOnline(userfname,userscName,empNO,userDOB,userresid,usermobile,username,useremail,userpass,usernationalID,access);
            UserEntity userEntity=new UserEntity(userfname,userscName,empNO,userDOB,userresid,usermobile,username,useremail,userpass,usernationalID,"Welcome",access,"");
            userViewModel.insert(userEntity);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstRegistration", true);   ///so that value changes only when reg is successful
            editor.apply();
    }
    public void login_link(View view) {
        UserEntity userEntity=new UserEntity("dedan","dedan",0,"dedan","dedan","dedan",
                "dedan","dedan","dedan","dedan","Welcome","dedan","");
        userViewModel.insert(userEntity);
        Intent intent=new Intent(this,logIn.class);
        startActivity(intent);
    }
    private boolean validateForm() {
        boolean valid = true;

        String emailtxt = email.getText().toString();
        if (TextUtils.isEmpty(emailtxt)) {
            email.setError("Required.");
            valid = false;
        }
        else {
            email.setError(null);
        }

        String password = pass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            pass.setError("Required.");
            valid = false;
        }
        if(password.length()<6){
            pass.setError("Not Less Than 6 Characters.");
            valid = false;
        }else {
            pass.setError(null);
        }
        if(user.getText().toString().isEmpty()){
            user.setError("Required");
        }else{user.setError(null);}
        if(fName.getText().toString().isEmpty()){
            fName.setError("Required");
        }else{fName.setError(null);}
        if(scName.getText().toString().isEmpty()){
            scName.setError("Required");
        }else{scName.setError(null);}
        if(idN0.getText().toString().isEmpty()){
            idN0.setError("Required");
        }else{idN0.setError(null);}
        if(resid.getText().toString().isEmpty()){
            resid.setError("Required");
        }else{resid.setError(null);}
        if(mobileNo.getText().toString().isEmpty()){
            mobileNo.setError("Required");
        }else{mobileNo.setError(null);}

        return valid;
    }

    public void DOB_picker(){
        final Calendar calendar=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat sdfformat=new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
                 String dob=sdfformat.format(calendar.getTime());
                 DOB.setText(dob);

            }
        };

        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Register.this,onDateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    public void update(){
        final String userfname=fName.getText().toString();
        final String userscName=scName.getText().toString();
        final String usernationalID= idN0.getText().toString();
        final String userDOB=DOB.getText().toString();
        final String userresid=resid.getText().toString();
        final String usermobile=mobileNo.getText().toString();
        String username=user.getText().toString().trim();
        final String userpass=pass.getText().toString().trim();
        final String useremail=email.getText().toString().trim();
        String access="ACCESS_GRANTED";
        if(internetIsConnected()){
            onlineDb.updateOnline(userfname,userscName,empNO,userDOB,userresid,usermobile,username,useremail,userpass,usernationalID,access);
            UserEntity userEntity=new UserEntity(userfname,userscName,empNO,userDOB,userresid,usermobile,username,useremail,userpass,usernationalID,"Welcome",access,null);
            userEntity.setKEY_USER_ID(editID);
            userViewModel.update(userEntity);

            FirebaseUser fireuser = FirebaseAuth.getInstance().getCurrentUser();
            fireuser.updatePassword(userpass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "password updated",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register.this, ""+e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            fireuser.updateEmail(useremail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "email updated",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register.this, ""+e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(Register.this, "failed!!!No network accessbtn",
                    Toast.LENGTH_SHORT).show();
        }

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
