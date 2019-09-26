package com.example.dedan.digitalreceipts;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class logIn extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private static int loggedIn_baseId;
    private static long loggedIn_empNO;
    EditText user,emailtxt;
    TextInputEditText pass;
    SqlOpenHelper sqlOpenHelper;

    private static String loggedIn_user;
    private static String loggedIn_username;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        user=findViewById(R.id.username_edit);
        pass=findViewById(R.id.password_edit);
        emailtxt=findViewById(R.id.email_edit);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        //inputLayoutEmail=findViewById(R.id.input_layout_useremail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        sqlOpenHelper = new SqlOpenHelper(getApplicationContext());
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
        progressBar.setVisibility(View.VISIBLE);
            // login user
            int x= checkLogin(sqlOpenHelper,name, password);
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
                                    progressBar.setVisibility(View.INVISIBLE);
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
            else if(x==2){
                Toast.makeText(getApplicationContext(),
                        "NOT YET APPROVED!", Toast.LENGTH_LONG)
                        .show();
            }
            else if(x==0 & internetIsConnected()){
                mAuth.signInWithEmailAndPassword(emailtxt.getText().toString().trim(), pass.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Objects.requireNonNull(mAuth.getCurrentUser()).delete();
                                    housekeeping();
                                    Toast.makeText(getApplicationContext(),
                                            "Please register Again!", Toast.LENGTH_LONG)
                                            .show();
                                    progressBar.setVisibility(View.INVISIBLE);
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
            else{
                Toast.makeText(getApplicationContext(),
                        "Wrong credentials!", Toast.LENGTH_LONG)
                        .show();
            }
        progressBar.setVisibility(View.INVISIBLE);

    }
    public int checkLogin(SqlOpenHelper sqlOpenHelper, String name, String password) {
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper._USERID, SqlOpenHelper.KEY_NAME,SqlOpenHelper.KEY_PASS,SqlOpenHelper.KEY_FIRSTNAME,SqlOpenHelper.KEY_empNO,
        SqlOpenHelper.KEY_ACCESS};
        Cursor cursor = db.query(SqlOpenHelper.TABLE_USER, columns, null, null,
                null, null, null);

        int IdPos = cursor.getColumnIndex(SqlOpenHelper._USERID);
        int namePos = cursor.getColumnIndex(SqlOpenHelper.KEY_NAME);
        int userpos = cursor.getColumnIndex(SqlOpenHelper.KEY_FIRSTNAME);
        int passwordPos = cursor.getColumnIndex(SqlOpenHelper.KEY_PASS);
        int accessPos = cursor.getColumnIndex(SqlOpenHelper.KEY_ACCESS);
        int empPos=cursor.getColumnIndex(SqlOpenHelper.KEY_empNO);
        //refresh views here so that they can load again
         int x = 0;
        while (cursor.moveToNext()) {
            String n = cursor.getString(namePos);
            String c = cursor.getString(passwordPos);
            String d=cursor.getString(accessPos);
            if(n.isEmpty()&&c.isEmpty()){
                x=99;
                return x;
            }
            if (n.equals(name)&&c.equals(password)) {
                loggedIn_baseId=cursor.getInt(IdPos);
                loggedIn_username=n;
                loggedIn_user = cursor.getString(userpos);
                loggedIn_empNO=cursor.getLong(empPos);
                if(d.equals("ACCESS_DENIED")){
                    x = 2;
                    return x;
                }
                x = 1;
                return x;
            }
        }
        cursor.close();
        return x;
    }
    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Intent main=new Intent(this,MainActivity.class);
            main.putExtra("loggedIn_user",loggedIn_user);
            main.putExtra("loggedIn_username",loggedIn_username);
            main.putExtra("loggedIn_baseId",loggedIn_baseId);
            startActivity(main);
        }
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
        progressBar.setVisibility(View.VISIBLE);

            // login user
            int x=checkLogin(sqlOpenHelper,name,password);

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
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(logIn.this, ""+e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
            }
            else if(x==0 & internetIsConnected()){
                mAuth.signInWithEmailAndPassword(emailtxt.getText().toString().trim(), pass.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Objects.requireNonNull(mAuth.getCurrentUser()).delete();
                                        housekeeping();
                                    Toast.makeText(getApplicationContext(),
                                            "Please register Again!", Toast.LENGTH_LONG)
                                            .show();
                                    progressBar.setVisibility(View.INVISIBLE);
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
            else{
                Toast.makeText(getApplicationContext(),
                        "Wrong credentials!", Toast.LENGTH_LONG)
                        .show();
            }
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
                return (Runtime.getRuntime().exec(command).waitFor(10,TimeUnit.SECONDS));
            }
            return (Runtime.getRuntime().exec(command).waitFor()==0);
        } catch (Exception e) {
            return false;
        }
    }
}