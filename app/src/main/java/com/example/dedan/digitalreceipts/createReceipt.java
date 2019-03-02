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
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class createReceipt extends AppCompatActivity {

    EditText title,locat,box;
    EditText mail;
    EditText cont;
    Button save;
    ImageView image;
    int pick=1;
    Uri uri;
    File file;
    Bitmap bitmap;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt);

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

                sharedpreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("nameKey",""+ t);
                    editor.putString("emailKey",""+ e);
                    editor.putString("locationKey",""+ l);
                    editor.putString("boxKey",""+ b);
                    editor.putString("contactKey",""+ c);
                    editor.apply();
                    Toast.makeText(createReceipt.this, "thanks", Toast.LENGTH_LONG).show();


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


                    cont.setEnabled(false);
                    mail.setEnabled(false);
                    title.setEnabled(false);
                    locat.setEnabled(false);
                    box.setEnabled(false);
                   // image.setEnabled(false);
                }});
        image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                Intent gallery=new Intent();
                gallery.setType("Image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"pictures"),pick);
            }
        });
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
        sharedpreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        mail.setText(sharedpreferences.getString("emailKey",""));
        title.setText(sharedpreferences.getString("nameKey",""));
        locat.setText(sharedpreferences.getString("locationKey",""));
        box.setText(sharedpreferences.getString("boxKey",""));
        cont.setText(sharedpreferences.getString("contactKey",""));

        File route = Environment.getExternalStorageDirectory();
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
        }
    }


    public void dropLIST(View view) {
        Intent dropOpen=new Intent(this,dropDown.class);
        startActivity(dropOpen);
    }
}

