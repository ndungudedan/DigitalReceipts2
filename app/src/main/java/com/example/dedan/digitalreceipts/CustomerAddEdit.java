package com.example.dedan.digitalreceipts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class CustomerAddEdit extends AppCompatActivity {
    private EditText editfname,editsname,editphone,editemail,editpobox,editaddress,editlocation;
    public static final String EXTRA_FNAME=
            "com.tidtech.EXTRA_FNAME";
    public static final String EXTRA_SNAME=
            "com.tidtech.EXTRA_SNAME";
    public static final String EXTRA_PHONE=
            "com.tidtech.EXTRA_PHONE";
    public static final String EXTRA_EMAIL=
            "com.tidtech.EXTRA_EMAIL";
    public static final String EXTRA_POBOX=
            "com.tidtech.EXTRA_POBOX";
    public static final String EXTRA_ADDRESS=
            "com.tidtech.EXTRA_ADDRESS";
    public static final String EXTRA_LOCATION=
            "com.tidtech.EXTRA_LOCATION";
    public static final String EXTRA_ID=
            "com.tidtech.room_database.EXTRA_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add_edit);

        editaddress=findViewById(R.id.addresstxt);
        editemail=findViewById(R.id.emailtxt);
        editfname=findViewById(R.id.fnametxt);
        editlocation=findViewById(R.id.locationtxt);
        editphone=findViewById(R.id.phonetxt);
        editpobox=findViewById(R.id.poboxtxt);
        editsname=findViewById(R.id.snametxt);

        Button btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCustomer();
            }
        });

        Intent intent =getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Customer");
            editfname.setText(intent.getStringExtra(EXTRA_FNAME));
            editsname.setText(intent.getStringExtra(EXTRA_SNAME));
            editphone.setText(intent.getStringExtra(EXTRA_PHONE));
            editpobox.setText(intent.getStringExtra(EXTRA_POBOX));
            editaddress.setText(intent.getStringExtra(EXTRA_ADDRESS));
            editlocation.setText(intent.getStringExtra(EXTRA_LOCATION));
            editemail.setText(intent.getStringExtra(EXTRA_EMAIL));

        }
        else{
            setTitle("ADD CUSTOMER");
        }
    }
    public void saveCustomer(){
        if (validate()){
            Intent data=new Intent();
            data.putExtra(EXTRA_FNAME,editfname.getText().toString());
            data.putExtra(EXTRA_EMAIL,editemail.getText().toString());
            data.putExtra(EXTRA_SNAME,editsname.getText().toString());
            data.putExtra(EXTRA_PHONE,editphone.getText().toString());
            data.putExtra(EXTRA_POBOX,editpobox.getText().toString());
            data.putExtra(EXTRA_LOCATION,editlocation.getText().toString());
            data.putExtra(EXTRA_ADDRESS,editaddress.getText().toString());

            int id=getIntent().getIntExtra(EXTRA_ID,-1);
            if(id!=-1){
                data.putExtra(EXTRA_ID,id);
            }
            setResult(RESULT_OK,data);
            finish();
        }

    }
    public boolean validate(){
        if (editfname.getText().toString().trim().isEmpty()) {
            editfname.setError("Required.");
            return false;
        } else {
            editfname.setError(null);
        }
        if (editsname.getText().toString().trim().isEmpty()) {
            editsname.setError("Required.");
            return false;
        } else {
            editsname.setError(null);
        }
        if (editemail.getText().toString().trim().isEmpty()) {
            editemail.setText("N/A");
        } else {
            editemail.setError(null);
        }
        if (editlocation.getText().toString().trim().isEmpty()) {
            editlocation.setText("N/A");
        } else {
            editlocation.setError(null);
        }
        if (editaddress.getText().toString().trim().isEmpty()) {
            editaddress.setText("N/A");
        } else {
            editaddress.setError(null);
        }
        if (editpobox.getText().toString().trim().isEmpty()) {
            editpobox.setText("N/A");

        } else {
            editpobox.setError(null);
        }
        if (editphone.getText().toString().trim().isEmpty()) {
            editphone.setText("N/A");
        } else {
            editphone.setError(null);
        }
        return true;
    }
}
