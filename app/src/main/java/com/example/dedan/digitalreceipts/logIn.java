package com.example.dedan.digitalreceipts;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class logIn extends AppCompatActivity {
    EditText user;
    EditText pass;
    SqlOpenHelper sqlOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        user=findViewById(R.id.username_edit);
        pass=findViewById(R.id.password_edit);
        sqlOpenHelper = new SqlOpenHelper(getApplicationContext());
    }

    public void login(View view) {
        String name=user.getText().toString();
        String password=pass.getText().toString();
        if (!name.isEmpty() && !password.isEmpty()) {
            // login user
            int x= checkLogin(sqlOpenHelper,name, password);
            if(x == 1){
                user.getText().clear();
                pass.getText().clear();
                Bundle bundle=new Bundle();
                bundle.putString("logged_On",name);
                Intent main=new Intent(this,MainActivity.class);
                startActivity(main,bundle);
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
    public static int checkLogin(SqlOpenHelper sqlOpenHelper,String name, String password) {
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper.KEY_ID, SqlOpenHelper.KEY_NAME,SqlOpenHelper.KEY_PASS};
        Cursor cursor = db.query(SqlOpenHelper.TABLE_USER, columns, null, null,
                null, null, null);

        int IdPos = cursor.getColumnIndex(SqlOpenHelper.KEY_ID);
        int namePos = cursor.getColumnIndex(SqlOpenHelper.KEY_NAME);
        int passwordPos = cursor.getColumnIndex(SqlOpenHelper.KEY_PASS);
        //refresh views here so that they can load again
        int x = 0;
        while (cursor.moveToNext()) {
            String n = cursor.getString(namePos);
            String c = cursor.getString(passwordPos);
            if (n.equals(name)&&c.equals(password)) {
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
                user.getText().clear();
                pass.getText().clear();
                Bundle bundle=new Bundle();
                bundle.putString("logged_On",name);
                Intent main=new Intent(this,MainActivity.class);
                startActivity(main,bundle);

                /*Intent main=new Intent(this,MainActivity.class);
                main.putExtra("logged_On",name);
                startActivity(main);*/
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
}
