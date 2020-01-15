package com.example.dedan.digitalreceipts;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class logIn extends AppCompatActivity {
    UserViewModel userViewModel;
    SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    TextView stat_view;
    private FirebaseAuth mAuth;
    EditText usernametxt;
    TextInputEditText pass;

    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);

        setContentView(R.layout.activity_log_in);
        pass=findViewById(R.id.password_edit);
        usernametxt =findViewById(R.id.user_edit);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        //inputLayoutEmail=findViewById(R.id.input_layout_useremail);
        progressBar = findViewById(R.id.progressBar);
        stat_view=findViewById(R.id.status_check);

        mAuth = FirebaseAuth.getInstance();

    }
    public void login(View view) {
        String username= usernametxt.getText().toString();
        String password=pass.getText().toString();
        if (!validateCredentials()) {
            return;
        }
        stat_view.setText("Please Wait....");
        stat_view.setTextColor(Color.CYAN);
            // login user
            int x= checkLogin(username, password);
            x=1;
            if(x==3){
                Toast.makeText(logIn.this, "  No such user exists",
                        Toast.LENGTH_SHORT).show();
            }
        if(x==1 & !internetIsConnected()){
            housekeeping();
            Intent main=new Intent(logIn.this,MainActivity.class);
            loggedUser();
            startActivity(main);
        }
            if(x == 1 & internetIsConnected()){
                mAuth.signInWithEmailAndPassword(user.getKEY_EMAIL(), pass.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        housekeeping();
                                        loggedUser();
                                        Intent main=new Intent(logIn.this,MainActivity.class);
                                        startActivity(main);
                                    }
                                    else{
                                        Toast.makeText(logIn.this, "Please verify email",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                    stat_view.setText("success");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        stat_view.setText(""+e.getLocalizedMessage());
                        stat_view.setTextColor(Color.RED);
                       /* Toast.makeText(logIn.this, ""+e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();*/
                    }
                });
            }
            else if(x==2){
                Toast.makeText(getApplicationContext(),
                        "NOT YET APPROVED!", Toast.LENGTH_LONG)
                        .show();
            }
    }
    public int checkLogin( String name, String password) {              //0=default value
        int x = 0;                                                      //99=credentials empty unlikely scenario
        //2=accessbtn denied
        user = userViewModel.getSingleUser(name,password);
        if(user !=null){                                                   //1=login good
            String n = user.getKEY_NAME();                                 //3=no such user exists
            String p = user.getKEY_PASS();
            String f = user.getKEY_FIRSTNAME();
            String a = user.getKEY_ACCESS();
            long y= user.getKEY_empNO();
            String h= user.getKEY_EMAIL();
            if (n.isEmpty() && p.isEmpty()) {
                x = 99;
                return x;
            }
            if (n.equals(name) && p.equals(password)) {
                if(a.equals("ACCESS_DENIED")){
                    x = 2;
                    return x;
                }
                x = 1;
                return x;
            }
        }
        else{
            x=3;
            return x;
        }
        return x;
    }
    public void Reg_link(View view) {
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }
    public void Forgot_password(View view) {
        if (usernametxt.getText().toString().trim().isEmpty()) {
            usernametxt.setError("Required.");
            return;
        } else {
            usernametxt.setError(null);
        }
        if(user!=null){
            mAuth.sendPasswordResetEmail(user.getKEY_EMAIL())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(logIn.this, ""+"Reset email sent",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(logIn.this, Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(logIn.this, ""+e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
    public void admin_login(View view) {
        String username= usernametxt.getText().toString();
        String password=pass.getText().toString();
        if (!validateCredentials()) {
            return;
        }
        stat_view.setText("Please Wait.....");

         int x=checkLogin(username,password);
            if(user!=null && user.getKEY_NAME().startsWith("ADMIN_")){
                if(x==3){
                    Toast.makeText(logIn.this, "  No such user exists",
                            Toast.LENGTH_SHORT).show();
                }

                if(x==1 & !internetIsConnected()){
                    housekeeping();
                    Intent main=new Intent(logIn.this,MainActivity.class);
                    loggedUser();
                    startActivity(main);
                }
                if(x == 1 & internetIsConnected()){
                    mAuth.signInWithEmailAndPassword(user.getKEY_EMAIL(), pass.getText().toString().trim())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        if(mAuth.getCurrentUser().isEmailVerified()){
                                            housekeeping();
                                            loggedUser();
                                            Intent main=new Intent(logIn.this,MainActivity.class);
                                            startActivity(main);
                                        }
                                        else{
                                            Toast.makeText(logIn.this, "Please verify email",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                        stat_view.setText("success");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            stat_view.setText(""+e.getLocalizedMessage());
                            stat_view.setTextColor(Color.RED);
                        }
                    });
                    stat_view.setText("success");
                }
            /*else{
                stat_view.setText("Wrong Credentials!");
                stat_view.setTextColor(Color.RED);
            }*/
            }

        }
    public void housekeeping(){
        usernametxt.getText().clear();
        pass.getText().clear();
    }
    private boolean validateCredentials() {
        if (usernametxt.getText().toString().trim().isEmpty()) {
            usernametxt.setError("Required.");
            return false;
        } else {
            usernametxt.setError(null);
        }
        if(pass.getText().toString().trim().isEmpty()){
            inputLayoutPassword.setError("invalid password");
        }
        else{
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return (Runtime.getRuntime().exec(command).waitFor(5,TimeUnit.SECONDS));
            }
            return (Runtime.getRuntime().exec(command).waitFor()==0);
        } catch (Exception e) {
            return false;
        }
    }
    public void loggedUser(){
        if(user!=null){
            Date date=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
            String timeStamp= sdf.format(date);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_time",timeStamp);
            editor.putInt("current_user",0);
            editor.putString("current_user",user.getKEY_FIRSTNAME());
            editor.putString("current_username",user.getKEY_NAME());
            editor.putString("current_userId", String.valueOf(user.getKEY_USER_ID()));
            editor.apply();
        }
    }
}