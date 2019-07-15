package com.example.dedan.digitalreceipts;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText fName,scName, idN0,DOB,resid,mobileNo;
    EditText user;
    TextInputEditText pass;
    EditText email;
    SqlOpenHelper sqlOpenHelper;
    int empNO =000;
    private TextInputLayout inputName,inputsecname,inputId,inputDOB,inputresidence,inputNo,inputUsername,inputPass,inputemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user=findViewById(R.id.user_reg);
        pass=findViewById(R.id.pass_reg);
        email=findViewById(R.id.email_reg);
        fName=findViewById(R.id.first_name);
        scName=findViewById(R.id.sec_name);
        idN0=findViewById(R.id.id_no);
        DOB=findViewById(R.id.dob);
        resid=findViewById(R.id.residence);
        mobileNo=findViewById(R.id.tel_no);
        sqlOpenHelper = new SqlOpenHelper(getApplicationContext());

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

    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } }
    public void register(View view) {
        final String userfname=fName.getText().toString();
        final String userscName=scName.getText().toString();
        final String usernationalID= idN0.getText().toString();
        final String userDOB=DOB.getText().toString();
        final String userresid=resid.getText().toString();
        final String usermobile=mobileNo.getText().toString();

        final String username=user.getText().toString().trim();
        final String userpass=pass.getText().toString().trim();
        final String useremail=email.getText().toString().trim();
        if (!validateForm()) {
            return;
        }
        /*if(username.isEmpty()||userpass.isEmpty()||useremail.isEmpty()||userscName.isEmpty()||userfname.isEmpty()||usernationalID.isEmpty()
                ||userDOB.isEmpty()||userresid.isEmpty()||usermobile.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Fill in all details!", Toast.LENGTH_LONG)
                    .show();
            return;
        }*/
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper.KEY_ID, SqlOpenHelper.KEY_NAME,SqlOpenHelper.KEY_PASS,SqlOpenHelper.KEY_EMAIL,SqlOpenHelper.KEY_empNO};
        Cursor cursor = db.query(SqlOpenHelper.TABLE_USER, columns, null, null,
                null, null, null);

        int IdPos = cursor.getColumnIndex(SqlOpenHelper.KEY_ID);
        int namePos = cursor.getColumnIndex(SqlOpenHelper.KEY_NAME);
        int emailPos = cursor.getColumnIndex(SqlOpenHelper.KEY_EMAIL);
        int empPos = cursor.getColumnIndex(SqlOpenHelper.KEY_empNO);
        //refresh views here so that they can load again
        while (cursor.moveToNext()) {
            String n = cursor.getString(namePos);
            String c = cursor.getString(emailPos);
            if (n.equals(username)&&c.equals(useremail)) {
                Toast.makeText(getApplicationContext(),
                        "Already registered!", Toast.LENGTH_LONG)
                        .show();
                return;
            }
        }
        while(cursor.moveToLast()){
            empNO =cursor.getInt(empPos)+1;
            break;
        }
        cursor.close();

        mAuth.createUserWithEmailAndPassword(useremail, userpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Registration Success.",
                                    Toast.LENGTH_SHORT).show();
                            sqlOpenHelper.addUser(userfname,userscName,usernationalID,userDOB,userresid,usermobile,username,userpass,useremail, empNO);
                            Toast.makeText(getApplicationContext(),
                                    "registered!", Toast.LENGTH_SHORT)
                                    .show();

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
       /* Intent intent=new Intent(this,logIn.class);
        startActivity(intent);*/
    }

    public void login_link(View view) {
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

        return valid;
    }

}
