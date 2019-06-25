package com.example.dedan.digitalreceipts;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class logIn extends AppCompatActivity {
    private static int loggedIn_baseId;
    EditText user;
    EditText pass;
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
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        sqlOpenHelper = new SqlOpenHelper(getApplicationContext());
    }

    public void login(View view) {
        String name=user.getText().toString();
        String password=pass.getText().toString();
        if (!validateName()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
            // login user
            int x= checkLogin(sqlOpenHelper,name, password);
            if(x == 1){
                housekeeping();
                Intent main=new Intent(this,MainActivity.class);
                main.putExtra("loggedIn_user",loggedIn_user);
                main.putExtra("loggedIn_username",loggedIn_username);
                main.putExtra("loggedIn_baseId",loggedIn_baseId);
                startActivity(main);
            }
            else if(x==2){
                Toast.makeText(getApplicationContext(),
                        "NOT YET APPROVED!", Toast.LENGTH_LONG)
                        .show();
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "Wrong credentials!", Toast.LENGTH_LONG)
                        .show();
            }
    }
    public static int checkLogin(SqlOpenHelper sqlOpenHelper,String name, String password) {
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper._USERID, SqlOpenHelper.KEY_NAME,SqlOpenHelper.KEY_PASS,SqlOpenHelper.KEY_FIRSTNAME,
        SqlOpenHelper.KEY_ACCESS};
        Cursor cursor = db.query(SqlOpenHelper.TABLE_USER, columns, null, null,
                null, null, null);

        int IdPos = cursor.getColumnIndex(SqlOpenHelper._USERID);
        int namePos = cursor.getColumnIndex(SqlOpenHelper.KEY_NAME);
        int userpos = cursor.getColumnIndex(SqlOpenHelper.KEY_FIRSTNAME);
        int passwordPos = cursor.getColumnIndex(SqlOpenHelper.KEY_PASS);
        int accessPos = cursor.getColumnIndex(SqlOpenHelper.KEY_ACCESS);
        //refresh views here so that they can load again
        int x = 0;
        while (cursor.moveToNext()) {
            String n = cursor.getString(namePos);
            String c = cursor.getString(passwordPos);
            String d=cursor.getString(accessPos);
            if (n.equals(name)&&c.equals(password)) {
                loggedIn_baseId=cursor.getInt(IdPos);
                loggedIn_username=n;
                loggedIn_user = cursor.getString(userpos);
                if(d.equals("ACCESS_DENIED")){
                    x = 2;
                    break;
                }
                x = 1;
                break;
            }

        }
        cursor.close();
        return x;
    }

    public void Reg_link(View view) {
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }

    public void Forgot_password(View view) {

    }

    public void admin_login(View view) {
        String name="ADMIN_"+user.getText().toString();
        String password=pass.getText().toString();
        if (!name.isEmpty() && !password.isEmpty()) {
            // login user
            int x=checkLogin(sqlOpenHelper,name,password);
            if(x == 1){
                housekeeping();

                Intent main=new Intent(this,MainActivity.class);
                main.putExtra("loggedIn_user",loggedIn_user);
                main.putExtra("loggedIn_username",loggedIn_username);
                main.putExtra("loggedIn_baseId",loggedIn_baseId);
                startActivity(main);

            }
            else{
                Toast.makeText(getApplicationContext(),
                        "Wrong credentials!", Toast.LENGTH_LONG)
                        .show();
            }
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }
    public void housekeeping(){
        user.getText().clear();
        pass.getText().clear();
        //finish();
    }
    private boolean validateName() {
        if (user.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("invalid username");
            requestFocus(user);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePassword() {
        if (user.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError("invalid password");
            requestFocus(pass);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
