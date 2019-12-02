package com.example.dedan.digitalreceipts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    public static final int ADD_CUSTOMER_REQUEST=1;
    public static final int EDIT_CUSTOMER_REQUEST=2;
    CustomerViewModel customerViewModel;

    public static final String EXT_FNAME=
            "com.tidtech.EXTRA_FNAME";
    public static final String EXT_SNAME=
            "com.tidtech.EXTRA_SNAME";
    public static final String EXT_PHONE=
            "com.tidtech.EXTRA_PHONE";
    public static final String EXT_EMAIL=
            "com.tidtech.EXTRA_EMAIL";
    public static final String EXT_POBOX=
            "com.tidtech.EXTRA_POBOX";
    public static final String EXT_ADDRESS=
            "com.tidtech.EXTRA_ADDRESS";
    public static final String EXT_LOCATION=
            "com.tidtech.EXTRA_LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        FloatingActionButton floatingActionButton=findViewById(R.id.ActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomerActivity.this,CustomerAddEdit.class);
                startActivityForResult(intent,ADD_CUSTOMER_REQUEST);
            }
        });

        RecyclerView recyclerView=findViewById(R.id.customerRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final customerAdapter adapter=new customerAdapter();
        recyclerView.setAdapter(adapter);

        customerViewModel= ViewModelProviders.of(this).get(CustomerViewModel.class);
        customerViewModel.getAllCustomers().observe(this, new Observer<List<CustomerEntity>>() {
            @Override
            public void onChanged(List<CustomerEntity> customerEntities) {
                adapter.submitList(customerEntities);
            }
        });
        adapter.setOnItemClickListener(new customerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CustomerEntity customerEntity) {
                Intent i=getIntent();
                if(i.hasExtra("GetCustomer")){
                    Intent getCustomer=new Intent();
                    getCustomer.putExtra(EXT_FNAME,customerEntity.getKEY_first_name());
                    getCustomer.putExtra(EXT_SNAME,customerEntity.getKEY_sec_name());
                    getCustomer.putExtra(EXT_ADDRESS,customerEntity.getKEY_address());
                    getCustomer.putExtra(EXT_EMAIL,customerEntity.getKEY_email());
                    getCustomer.putExtra(EXT_LOCATION,customerEntity.getKEY_location());
                    getCustomer.putExtra(EXT_PHONE,customerEntity.getKEY_phone());
                    getCustomer.putExtra(EXT_POBOX,customerEntity.getKEY_po_box());
                    setResult(RESULT_OK,getCustomer);
                    finish();
                }
                else{
                    Intent intent=new Intent(CustomerActivity.this,CustomerAddEdit.class);
                    intent.putExtra(CustomerAddEdit.EXTRA_ID,customerEntity.getKEY_id());
                    intent.putExtra(CustomerAddEdit.EXTRA_EMAIL,customerEntity.getKEY_email());
                    intent.putExtra(CustomerAddEdit.EXTRA_LOCATION,customerEntity.getKEY_location());
                    intent.putExtra(CustomerAddEdit.EXTRA_FNAME,customerEntity.getKEY_first_name());
                    intent.putExtra(CustomerAddEdit.EXTRA_ADDRESS,customerEntity.getKEY_address());
                    intent.putExtra(CustomerAddEdit.EXTRA_POBOX,customerEntity.getKEY_po_box());
                    intent.putExtra(CustomerAddEdit.EXTRA_PHONE,customerEntity.getKEY_phone());
                    intent.putExtra(CustomerAddEdit.EXTRA_SNAME,customerEntity.getKEY_sec_name());
                    startActivityForResult(intent,EDIT_CUSTOMER_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_CUSTOMER_REQUEST && resultCode==RESULT_OK){
            String fname=data.getStringExtra(CustomerAddEdit.EXTRA_FNAME);
            String sname=data.getStringExtra(CustomerAddEdit.EXTRA_SNAME);
            int pobox= Integer.parseInt(data.getStringExtra(CustomerAddEdit.EXTRA_POBOX));
            int address= Integer.parseInt(data.getStringExtra(CustomerAddEdit.EXTRA_ADDRESS));
            String location=data.getStringExtra(CustomerAddEdit.EXTRA_LOCATION);
            String email=data.getStringExtra(CustomerAddEdit.EXTRA_EMAIL);
            int phone= Integer.parseInt(data.getStringExtra(CustomerAddEdit.EXTRA_PHONE));

            CustomerEntity customerEntity=new CustomerEntity(fname,sname,phone,location,address,pobox,email);
            customerViewModel.insert(customerEntity);
        }
        else if(requestCode==EDIT_CUSTOMER_REQUEST && resultCode==RESULT_OK){
            int id=data.getIntExtra(CustomerAddEdit.EXTRA_ID,-1);
            if(id==-1){
                Toast.makeText(this, "cannot update", Toast.LENGTH_SHORT).show();
                return;
            }
            String fname=data.getStringExtra(CustomerAddEdit.EXTRA_FNAME);
            String sname=data.getStringExtra(CustomerAddEdit.EXTRA_SNAME);
            int pobox= Integer.parseInt(data.getStringExtra(CustomerAddEdit.EXTRA_POBOX));
            int address= Integer.parseInt(data.getStringExtra(CustomerAddEdit.EXTRA_ADDRESS));
            String location=data.getStringExtra(CustomerAddEdit.EXTRA_LOCATION);
            String email=data.getStringExtra(CustomerAddEdit.EXTRA_EMAIL);
            int phone= Integer.parseInt(data.getStringExtra(CustomerAddEdit.EXTRA_PHONE));

            CustomerEntity customerEntity=new CustomerEntity(fname,sname,phone,location,address,pobox,email);
            customerEntity.setKEY_id(id);
            customerViewModel.update(customerEntity);

        }
    }
}
