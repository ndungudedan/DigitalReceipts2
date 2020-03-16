package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dedan.digitalreceipts.Database.CompDetails.detailsEntity;
import com.example.dedan.digitalreceipts.Database.CompDetails.detailsViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class createReceipt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private detailsViewModel detailsViewModel;
    private onlineDb onlineDb;
    FirebaseFirestore firedb = FirebaseFirestore.getInstance();
    FirebaseStorage firestorage = FirebaseStorage.getInstance();
    StorageReference storageRef = firestorage.getReference();
    EditText title,locat,box,cont;
    EditText mail;
    Button save;
    ImageView image;
    int pick=1;
    Uri uri;
    File file;
    Bitmap bitmap;
    Spinner spin;
    String[] spinList ;
    ArrayAdapter<String> spinAdapt;
    SharedPreferences sharedpreferences;
    private detailsEntity detEnt=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt);
        detailsViewModel= ViewModelProviders.of(this).get(detailsViewModel.class);
        onlineDb=new onlineDb(firedb,firestorage,storageRef);

        sharedpreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        spin= findViewById(R.id.month_spinner);
        spinList= new String[]{"January","February","March","April","May","June","July","August",
                "September","October","November","December"};
        spinAdapt=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinList);
        spinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinAdapt);
        spin.setOnItemSelectedListener(this);

        title= findViewById(R.id.title);
        mail= findViewById(R.id.email);
        locat= findViewById(R.id.location);
        box= findViewById(R.id.box);
        cont= findViewById(R.id.contact);
        save= findViewById(R.id.save);
        image= findViewById(R.id.imageView);

        retrieve();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = title.getText().toString();
                String e = mail.getText().toString();
                String c = cont.getText().toString();
                String l = locat.getText().toString();
                String b = box.getText().toString();

                String state= Environment.getExternalStorageState();
                if(bitmap!=null){
                if(Environment.MEDIA_MOUNTED.equals(state)) {
                    File route = Environment.getExternalStorageDirectory();
                    File dir = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS","Logo");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    file = new File(dir,  "Logo" + ".jpg");

                    try {
                        FileOutputStream fileOutputStream=new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                        fileOutputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }}
                if(detEnt!=null){
                        companyUpdate(t,b,l,e,c);
                }else{
                    companyRegister(t,b,l,e,c);
                }

                    cont.setEnabled(false);
                    mail.setEnabled(false);
                    title.setEnabled(false);
                    locat.setEnabled(false);
                    box.setEnabled(false);
                   // image.setEnabled(false);
                }});
        image.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"pictures"),pick);
            }
        });
    }
    public void companyRegister(String title,String address,String location,String email,String contact){
        if(internetIsConnected()){
            onlineDb.registerCompanyOnline(title,spin.getSelectedItemPosition(),spin.getSelectedView().toString(),location,contact,email,file.getAbsolutePath());
            detailsEntity detailsEntity=new detailsEntity(title,email,location,address,contact,file.getAbsolutePath(),spin.getSelectedItemPosition(),spin.getSelectedView().toString());
            detailsViewModel.insert(detailsEntity);
            onlineDb.companyUsers(title,sharedpreferences.getString("current_useremail",""),sharedpreferences.getString("current_username",""));
        }else{
            Toast.makeText(createReceipt.this, "failed!!!No network access",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void companyUpdate(String title,String address,String location,String email,String contact){
        if(internetIsConnected()){
            if(file==null){
                onlineDb.updateCompanyOnline(title,spin.getSelectedItemPosition(),spin.getSelectedView().toString(),location,contact,email,detEnt.getKEY_Logo());
                onlineDb.companyUpdateUsers(title,sharedpreferences.getString("current_useremail",""),sharedpreferences.getString("current_username",""));
                detailsEntity detailsEntity=new detailsEntity(title,email,location,address,contact,detEnt.getKEY_Logo(),spin.getSelectedItemPosition(),spin.getSelectedView().toString());
                detailsEntity.setKEY_ID(detEnt.getKEY_ID());
                detailsViewModel.update(detailsEntity);
            }else {
                onlineDb.companyUpdateUsers(title,sharedpreferences.getString("current_useremail",""),sharedpreferences.getString("current_username",""));
                onlineDb.updateCompanyOnline(title,spin.getSelectedItemPosition(),spin.getSelectedView().toString(),location,contact,email,file.getAbsolutePath());
                detailsEntity detailsEntity = new detailsEntity(title, email, location, address, contact, file.getAbsolutePath(), spin.getSelectedItemPosition(), spin.getSelectedView().toString());
                detailsEntity.setKEY_ID(detEnt.getKEY_ID());
                detailsViewModel.update(detailsEntity);
            }
        }
        else{
            Toast.makeText(createReceipt.this, "failed!!!No network access",
                    Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==pick&&resultCode==RESULT_OK){
            uri=data.getData();

                try {
                 bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                image.setImageBitmap(bitmap);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void edit(View view) {
        cont.setEnabled(true);
        mail.setEnabled(true);
        title.setEnabled(true);
        image.setEnabled(true);
        locat.setEnabled(true);
        box.setEnabled(true);
    }
    public void retrieve (){
        detailsViewModel.AllDetails().observe(this, new Observer<detailsEntity>() {
            @Override
            public void onChanged(detailsEntity detailsEntity) {
                detEnt=detailsEntity;
                if(detailsEntity!=null) {
                    mail.setText(detailsEntity.getKEY_Email());
                    title.setText(detailsEntity.getKEY_Title());
                    locat.setText(detailsEntity.getKEY_Location());
                    box.setText(detailsEntity.getKEY_Box());
                    cont.setText(detailsEntity.getKEY_Contact());
                    spin.setSelection(detailsEntity.getKEY_StartMonthPos());
                    try (FileInputStream stream = new FileInputStream(detailsEntity.getKEY_Logo())) {
                        Bitmap bit = BitmapFactory.decodeStream(stream);
                        image.setImageBitmap(bit);

                        if (bit == null) {
                            image.setImageResource(R.mipmap.launch_foreground);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /*File route = Environment.getExternalStorageDirectory();
        File dir = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS","Logo");
        File g=new File(dir,"/Logo.jpg");
        try (FileInputStream stream = new FileInputStream(g)) {
            Bitmap bit=BitmapFactory.decodeStream(stream);
            image.setImageBitmap(bit);

            if(bit==null){
                image.setImageResource(R.mipmap.launch_foreground);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void dropLIST(View view) {
        Intent dropOpen=new Intent(this,security.class);
        startActivity(dropOpen);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(detEnt!=null){
            detailsEntity detailsEntity=new detailsEntity(detEnt.getKEY_Title(),detEnt.getKEY_Email(),detEnt.getKEY_Location(),
                    detEnt.getKEY_Box(),detEnt.getKEY_Contact(),detEnt.getKEY_Logo(),position,view.toString());
            detailsEntity.setKEY_ID(detEnt.getKEY_ID());
            detailsViewModel.update(detailsEntity);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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