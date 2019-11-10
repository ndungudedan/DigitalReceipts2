package com.example.dedan.digitalreceipts;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class logIn extends AppCompatActivity {
    UserViewModel userViewModel;
    private List<UserEntity> allUsers;

    private ProgressBar progressBar;
    TextView stat_view;
    private FirebaseAuth mAuth;
    private static int loggedIn_baseId=1;//to be removeed
    private static long loggedIn_empNO;
    EditText user,emailtxt;
    TextInputEditText pass;

    private static String loggedIn_user;
    private static String loggedIn_username;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);

        setContentView(R.layout.activity_log_in);
        user=findViewById(R.id.username_edit);
        pass=findViewById(R.id.password_edit);
        emailtxt=findViewById(R.id.email_edit);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        //inputLayoutEmail=findViewById(R.id.input_layout_useremail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        stat_view=findViewById(R.id.status_check);

        mAuth = FirebaseAuth.getInstance();
        if(!internetIsConnected()){
            emailtxt.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
      //  alreadySignedIn(currentUser);
    }
    public void alreadySignedIn(FirebaseUser currentUser){
        if(currentUser!=null){
            Intent main=new Intent(this,MainActivity.class);
            main.putExtra("loggedIn_user",loggedIn_user);
            main.putExtra("loggedIn_username",loggedIn_username);
            main.putExtra("loggedIn_baseId",loggedIn_baseId);
            main.putExtra("loggedIn_empNo",loggedIn_empNO);
            startActivity(main);
        }
    }
    public void login(View view) {
        String name=user.getText().toString();
        String password=pass.getText().toString();
        if (!validateCredentials()) {
            return;
        }
        stat_view.setText("Please Wait....");
        stat_view.setTextColor(Color.CYAN);
            // login user
            int x= checkLogin(name, password);
            if(x==3){
                Toast.makeText(logIn.this, "  No such user exists",
                        Toast.LENGTH_SHORT).show();
            }
        if(x==1 & !internetIsConnected()){
            housekeeping();
            Intent main=new Intent(logIn.this,MainActivity.class);
            main.putExtra("loggedIn_user",loggedIn_user);
            main.putExtra("loggedIn_username",loggedIn_username);
            main.putExtra("loggedIn_baseId",loggedIn_baseId);
            main.putExtra("loggedIn_empNo",loggedIn_empNO);
            startActivity(main);
        }
            if(x == 1 & internetIsConnected()){
                mAuth.signInWithEmailAndPassword(emailtxt.getText().toString().trim(), pass.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        housekeeping();
                                        Intent main=new Intent(logIn.this,MainActivity.class);
                                        main.putExtra("loggedIn_user",loggedIn_user);
                                        main.putExtra("loggedIn_username",loggedIn_username);
                                        main.putExtra("loggedIn_baseId",loggedIn_baseId);
                                        main.putExtra("loggedIn_empNo",loggedIn_empNO);
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
        UserEntity user=userViewModel.getSingleUser(name,password);            //2=access denied
        if(user!=null){                                                   //1=login good
            String n = user.getKEY_NAME();                                      //3=no such user exists
            String p = user.getKEY_PASS();
            String f = user.getKEY_FIRSTNAME();
            String a = user.getKEY_ACCESS();
            long y=user.getKEY_empNO();
            if (n.isEmpty() && p.isEmpty()) {
                x = 99;
                return x;
            }
            if (n.equals(name) && p.equals(password)) {
                //loggedIn_baseId=cursor.getInt(IdPos);
                loggedIn_username = n;
                loggedIn_user = f;
                loggedIn_empNO = y;
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
        String emailAddress = emailtxt.getText().toString().trim();
        if (TextUtils.isEmpty(emailtxt.getText().toString().trim())) {
            emailtxt.setError("Required.");
            return;
        } else {
            emailtxt.setError(null);
        }
            mAuth.sendPasswordResetEmail(emailAddress)
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
    public void admin_login(View view) {
        String name="ADMIN_"+user.getText().toString();
        String password=pass.getText().toString();
        if (!validateCredentials()) {
            return;
        }
        stat_view.setText("Please Wait.....");

            // login user
            int x=checkLogin(name,password);

        if(x==3){
            Toast.makeText(logIn.this, "  No such user exists",
                    Toast.LENGTH_SHORT).show();
        }

            if(x==1 & !internetIsConnected()){
                housekeeping();
                Intent main=new Intent(logIn.this,MainActivity.class);
                main.putExtra("loggedIn_user",loggedIn_user);
                main.putExtra("loggedIn_username",loggedIn_username);
                main.putExtra("loggedIn_baseId",loggedIn_baseId);
                main.putExtra("loggedIn_empNo",loggedIn_empNO);
                startActivity(main);
            }
            if(x == 1 & internetIsConnected()){
                mAuth.signInWithEmailAndPassword(emailtxt.getText().toString().trim(), pass.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        housekeeping();
                                        Intent main=new Intent(logIn.this,MainActivity.class);
                                        main.putExtra("loggedIn_user",loggedIn_user);
                                        main.putExtra("loggedIn_username",loggedIn_username);
                                        main.putExtra("loggedIn_baseId",loggedIn_baseId);
                                        main.putExtra("loggedIn_empNo",loggedIn_empNO);
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
    public void housekeeping(){
        user.getText().clear();
        pass.getText().clear();
    }
    private boolean validateCredentials() {
        if (internetIsConnected() & TextUtils.isEmpty(emailtxt.getText().toString().trim())) {
            emailtxt.setError("Required.");
            return false;
        } else {
            emailtxt.setError(null);
        }
        if (user.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("invalid username");
            requestFocus(user);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }
        if(pass.getText().toString().trim().isEmpty()){
            inputLayoutPassword.setError("invalid password");
        }
        else{
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } }
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
}