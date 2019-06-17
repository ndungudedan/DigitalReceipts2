package com.example.dedan.digitalreceipts;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText fName,scName,empNo,DOB,resid,mobileNo;
    EditText user;
    EditText pass;
    EditText email;
    SqlOpenHelper sqlOpenHelper;
    int empNO =000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user=findViewById(R.id.user_reg);
        pass=findViewById(R.id.pass_reg);
        email=findViewById(R.id.email_reg);
        fName=findViewById(R.id.first_name);
        scName=findViewById(R.id.sec_name);
        empNo=findViewById(R.id.emp_no);
        DOB=findViewById(R.id.dob);
        resid=findViewById(R.id.residence);
        mobileNo=findViewById(R.id.tel_no);
        sqlOpenHelper = new SqlOpenHelper(getApplicationContext());
    }
    public void register(View view) {
        String userfname=fName.getText().toString();
        String userscName=scName.getText().toString();
        String usernationalID=empNo.getText().toString();
        String userDOB=DOB.getText().toString();
        String userresid=resid.getText().toString();
        String usermobile=mobileNo.getText().toString();

        String username=user.getText().toString();
        String userpass=pass.getText().toString();
        String useremail=email.getText().toString();
        if(username.isEmpty()||userpass.isEmpty()||useremail.isEmpty()){
            Toast.makeText(getApplicationContext(),
                    "Fill in all details!", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if(userpass.length()>=8){
            Toast.makeText(getApplicationContext(),
                    "Strong!", Toast.LENGTH_LONG)
                    .show();
        }
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
                break;
            }
        }
        while(cursor.moveToLast()){
            empNO =cursor.getInt(empPos)+1;
            break;
        }
        cursor.close();

        sqlOpenHelper.addUser(userfname,userscName,usernationalID,userDOB,userresid,usermobile,username,userpass,useremail, empNO);
        Toast.makeText(getApplicationContext(),
                "registered!", Toast.LENGTH_SHORT)
                .show();

        Intent intent=new Intent(this,logIn.class);
        startActivity(intent);
    }

    public void login_link(View view) {
        Intent intent=new Intent(this,logIn.class);
        startActivity(intent);
    }
}
