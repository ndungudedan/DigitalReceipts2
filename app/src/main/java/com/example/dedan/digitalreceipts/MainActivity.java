package com.example.dedan.digitalreceipts;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dedan.digitalreceipts.Database.CompDetails.detailsEntity;
import com.example.dedan.digitalreceipts.Database.CompDetails.detailsViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.April.AprViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.August.AugEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.August.AugViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.December.DecEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.December.DecViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.February.FebEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.February.FebViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.January.JanEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.January.JanViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.July.JulEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.July.JulViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.June.JunEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.June.JunViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.March.MarEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.March.MarViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.May.MayViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.November.NovEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.November.NovViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.October.OctEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.October.OctViewModel;
import com.example.dedan.digitalreceipts.Database.Month_Database.September.SepEntity;
import com.example.dedan.digitalreceipts.Database.Month_Database.September.SepViewModel;
import com.example.dedan.digitalreceipts.Database.Today_Database.UserStatsMonthViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Friday.FriViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Monday.MonViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Saturday.SatViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Sunday.SunViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Thursday.ThurViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Tuesday.TueViewModel;
import com.example.dedan.digitalreceipts.Database.Week_Database.Wednesday.WedViewModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Toolbar maintool;
    SharedPreferences sharedPreferences;
    UserViewModel userViewModel;
    JanViewModel janViewModel;FebViewModel febViewModel;MarViewModel marViewModel;
    AprViewModel aprViewModel;MayViewModel mayViewModel;JunViewModel junViewModel;
    JulViewModel julViewModel;AugViewModel augViewModel;SepViewModel sepViewModel;
    OctViewModel octViewModel;NovViewModel novViewModel;DecViewModel decViewModel;
    MonViewModel monViewModel;TueViewModel tueViewModel;WedViewModel wedViewModel;ThurViewModel thurViewModel;
    FriViewModel friViewModel;SatViewModel satViewModel;SunViewModel sunViewModel;
    ReceiptViewModel receiptViewModel;
    MonthSalesViewModel monthSalesViewModel;
    WeekSalesViewModel weekSalesViewModel;
    UserStatsMonthViewModel userStatsMonthViewModel;
    detailsViewModel detailsViewModel;
    private Document doc;
    private FileOutputStream fileOutputStream;
    private PdfPTable table;
    private String username="";
    private File file;
    private String date;
    Paragraph compDetails;
    private detailsEntity detEnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maintool = findViewById(R.id.mainActivitybar);
        setSupportActionBar(maintool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        userViewModel=ViewModelProviders.of(this).get(UserViewModel.class);
        janViewModel=ViewModelProviders.of(this).get(JanViewModel.class);febViewModel=ViewModelProviders.of(this).get(FebViewModel.class);
        marViewModel=ViewModelProviders.of(this).get(MarViewModel.class);aprViewModel=ViewModelProviders.of(this).get(AprViewModel.class);
        mayViewModel=ViewModelProviders.of(this).get(MayViewModel.class);junViewModel=ViewModelProviders.of(this).get(JunViewModel.class);
        julViewModel=ViewModelProviders.of(this).get(JulViewModel.class);augViewModel=ViewModelProviders.of(this).get(AugViewModel.class);
        sepViewModel=ViewModelProviders.of(this).get(SepViewModel.class);octViewModel=ViewModelProviders.of(this).get(OctViewModel.class);
        novViewModel=ViewModelProviders.of(this).get(NovViewModel.class);decViewModel=ViewModelProviders.of(this).get(DecViewModel.class);
        monViewModel=ViewModelProviders.of(this).get(MonViewModel.class);tueViewModel=ViewModelProviders.of(this).get(TueViewModel.class);
        wedViewModel=ViewModelProviders.of(this).get(WedViewModel.class);thurViewModel=ViewModelProviders.of(this).get(ThurViewModel.class);
        friViewModel=ViewModelProviders.of(this).get(FriViewModel.class);satViewModel=ViewModelProviders.of(this).get(SatViewModel.class);
        sunViewModel=ViewModelProviders.of(this).get(SunViewModel.class);
        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        weekSalesViewModel=ViewModelProviders.of(this).get(WeekSalesViewModel.class);
        monthSalesViewModel=ViewModelProviders.of(this).get(MonthSalesViewModel.class);
        detailsViewModel=ViewModelProviders.of(this).get(detailsViewModel.class);
        userStatsMonthViewModel =ViewModelProviders.of(this).get(UserStatsMonthViewModel.class);

        sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!sharedPreferences.getBoolean("firstTime", false)) {
            editor.putBoolean("firstTime", true);
            editor.apply();
        }

        Calendar calendar=Calendar.getInstance();
        date= DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(calendar.getTime());
        detailsViewModel.AllDetails().observe(this, new Observer<detailsEntity>() {
            @Override
            public void onChanged(detailsEntity detailsEntity) {
                detEnt=detailsEntity;
            }
        });
        if (detEnt!=null && date.contains(detEnt.getKEY_StartMonth())){
            reportPdf();
            endYeardialog();
        }

        permissions();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                confirmdialog();
                break;
            case R.id.secmenu:
                Intent sec=new Intent(this,Company.class);
                startActivity(sec);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void open(View view) {
        Intent intent=new Intent(this,createReceipt.class);
        startActivity(intent);
    }

    public void temp(View view) {
        Intent temp=new Intent(this,homeActivity.class);
        startActivity(temp);
    }

    public void drop(View view) {
        Intent drop=new Intent(this,unit.class);
        startActivity(drop);
    }

    public void list(View view) {
        Intent l=new Intent(this,rcptList.class);
        startActivity(l);
    }

    public void cust(View view){
        Intent j=new Intent(this,CustomerActivity.class);
        startActivity(j);
    }
    public void stat(View view){
        Intent j=new Intent(this,security.class);
        startActivity(j);
    }

    public void confirmdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        View mview=getLayoutInflater().inflate(R.layout.confirm_dialogbox,null);
        TextView mess = mview.findViewById(R.id.conf_text);
        mess.setText("YOU ARE LOGGING OUT");
        builder.setTitle("CONFIRM");
        builder.setMessage("DO YOU WANT TO LOG OUT?");
        builder.setView(mview);
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    public void endYeardialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        View mview=getLayoutInflater().inflate(R.layout.confirm_dialogbox,null);
        TextView mess = mview.findViewById(R.id.conf_text);
        builder.setTitle("END OF FINANCIAL YEAR:");
        builder.setMessage("OPEN ANNUAL REPORT");
        builder.setView(mview);
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(MainActivity.this,Company.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void logout() {
        reportPdf();
        finish();
        Intent logout=new Intent(this,logIn.class);
        startActivity(logout);
    }

    public void permissions(){
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
/*        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //permission not granted
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            //show explanation for need of permisssion
        }
        else{

        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if((grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                   // Toast.makeText(getApplicationContext(),"welcome",Toast.LENGTH_SHORT).show();

                }
                else{
                   // Toast.makeText(getApplicationContext(),"Please allow permissions",Toast.LENGTH_SHORT).show();
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    public String getUser(final String x){
        userViewModel.getAllUsers().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                for(UserEntity user:userEntities){
                    if(x.equals(String.valueOf(user.getKEY_USER_ID()))){
                        username =user.getKEY_FIRSTNAME()+""+user.getKEY_SECNAME();
                    }
                }
            }
        });
        return username;
    }
    public void table(String month){
        try {
            doc.add(new Paragraph(month+"Summary"));
            doc.add(new DottedLineSeparator());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table = new PdfPTable(4);
        table.addCell("User");
        table.addCell("Sales");
        table.addCell("Clients");
        try {
            doc.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public void report(){
        janViewModel.getAllJanEvents().observe(this, new Observer<List<JanEntity>>() {
            @Override
            public void onChanged(List<JanEntity> janEntities) {
                for(JanEntity jan:janEntities){
                    table("January");
                    table.addCell(getUser(jan.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(jan.getKEY_SALES()));
                    table.addCell(String.valueOf(jan.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        febViewModel.AllFebEvents().observe(this, new Observer<List<FebEntity>>() {
            @Override
            public void onChanged(List<FebEntity> febEntities) {
                for (FebEntity feb:febEntities){
                    table("February");
                    table.addCell(getUser(feb.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(feb.getKEY_SALES()));
                    table.addCell(String.valueOf(feb.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        marViewModel.allMarEvents().observe(this, new Observer<List<MarEntity>>() {
            @Override
            public void onChanged(List<MarEntity> marEntities) {
                for(MarEntity val:marEntities){
                    table("March");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        aprViewModel.AllAprEvents().observe(this, new Observer<List<AprEntity>>() {
            @Override
            public void onChanged(List<AprEntity> aprEntities) {
                for(AprEntity val:aprEntities){
                    table("April");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        mayViewModel.AllMayEvents().observe(this, new Observer<List<MayEntity>>() {
            @Override
            public void onChanged(List<MayEntity> mayEntities) {
                for(MayEntity val:mayEntities){
                    table("May");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        junViewModel.AllJunEvents().observe(this, new Observer<List<JunEntity>>() {
            @Override
            public void onChanged(List<JunEntity> junEntities) {
                for(JunEntity val:junEntities){
                    table("June");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        julViewModel.AllJulEvents().observe(this, new Observer<List<JulEntity>>() {
            @Override
            public void onChanged(List<JulEntity> julEntities) {
                for(JulEntity val:julEntities){
                    table("July");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        augViewModel.AllAugEvents().observe(this, new Observer<List<AugEntity>>() {
            @Override
            public void onChanged(List<AugEntity> augEntities) {
                for(AugEntity val:augEntities){
                    table("August");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        sepViewModel.AllSepEvents().observe(this, new Observer<List<SepEntity>>() {
            @Override
            public void onChanged(List<SepEntity> sepEntities) {
                for(SepEntity val:sepEntities){
                    table("September");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        octViewModel.AllOctEvents().observe(this, new Observer<List<OctEntity>>() {
            @Override
            public void onChanged(List<OctEntity> octEntities) {
                for(OctEntity val:octEntities){
                    table("October");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        novViewModel.AllNovEvents().observe(this, new Observer<List<NovEntity>>() {
            @Override
            public void onChanged(List<NovEntity> novEntities) {
                for(NovEntity val:novEntities){
                    table("November");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        decViewModel.AllDecEvents().observe(this, new Observer<List<DecEntity>>() {
            @Override
            public void onChanged(List<DecEntity> decEntities) {
                for(DecEntity val:decEntities){
                    table("December");
                    table.addCell(getUser(val.getKEY_FOREIGN_KEY()));
                    table.addCell(String.valueOf(val.getKEY_SALES()));
                    table.addCell(String.valueOf(val.getKEY_NO_OF_CLIENTS()));
                }
            }
        });
        doc.close();
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reportPdf(){
        String state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            File route=Environment.getExternalStorageDirectory();
            File dir=new File(route.getAbsolutePath()+"/DIGITALRECEIPTS","Reports");

            if(!dir.exists()){
                dir.mkdirs();
            }
            file = new File(dir,"Annual Report"+".pdf");
            try {
//creates pdf,saves and writes to it.
                fileOutputStream = new FileOutputStream(file);
                doc = new Document();
                PdfWriter.getInstance(doc, fileOutputStream);
                doc.open();
                sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                File d = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS","Logo");
                File g=new File(d,"/Logo.jpg");

                PdfPTable company=new PdfPTable(2);
                company.setWidthPercentage(60);
                PdfPCell logoCell=new PdfPCell();
                logoCell.setBorder(Rectangle.NO_BORDER);

                if(g.exists()){
                    try (FileInputStream stream = new FileInputStream(g)) {
                        Bitmap bit= BitmapFactory.decodeStream(stream);
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        Image image=Image.getInstance(byteArrayOutputStream.toByteArray());
                        //image.setAlignment(Image.ALIGN_LEFT);
                        //doc.add(image);
                        logoCell.addElement(image);
                        company.addCell(logoCell);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                detailsEntity ent=detEnt;
                    compDetails=new Paragraph(ent.getKEY_Title()+"\n"+ent.getKEY_Email()+"\n"+"P.O. BOX"+ ent.getKEY_Box()+ "\n"
                            +ent.getKEY_Location()+"\n"+ent.getKEY_Contact(), FontFactory.getFont(FontFactory.TIMES_ROMAN,18, Font.NORMAL));

                Paragraph Em=new Paragraph(sharedPreferences.getString("emailKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL, BaseColor.MAGENTA));
                Paragraph Bo=new Paragraph("P.O. BOX"+sharedPreferences.getString("boxKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL, BaseColor.MAGENTA));
                Paragraph Lo=new Paragraph(sharedPreferences.getString("locationKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));
                Paragraph Co=new Paragraph(sharedPreferences.getString("contactKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));

                compDetails.setAlignment(Element.ALIGN_RIGHT);
                Em.setAlignment(Element.ALIGN_RIGHT);
                Bo.setAlignment(Element.ALIGN_RIGHT);
                Lo.setAlignment(Element.ALIGN_RIGHT);
                Co.setAlignment(Element.ALIGN_RIGHT);
                doc.add(compDetails);
                doc.add(Em);
                doc.add(Bo);
                doc.add(Lo);
                doc.add(Co);
                doc.add(new Paragraph(new Date().toString()));


                PdfPCell Cell=new PdfPCell(new Paragraph(compDetails));
                company.addCell(Cell);
                doc.add(company);

                report();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Please save logo first",Toast.LENGTH_SHORT).show();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"sdCard not mounted",Toast.LENGTH_SHORT).show();
        }
        ReceiptEntity receiptEntity=new ReceiptEntity(date.toString(),file.getAbsolutePath(),"N/A",file.getName(),"Report");
        receiptViewModel.insert(receiptEntity);
    }
}
