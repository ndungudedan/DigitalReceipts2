package com.example.dedan.digitalreceipts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.core.app.NavUtils;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class homeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    GoodsViewModel goodsViewModel;
    PickedGoodViewModel pickedGoodViewModel;
    DailySalesViewModel dailySalesViewModel;
    MonthSalesViewModel monthSalesViewModel;
    WeekSalesViewModel weekSalesViewModel;
    List<PickedGoodEntity> pickedGoods;

    TextView totalView;
    public static final int GET_CUSTOMER_REQUEST=3;

    String item;
    String pack;
    int cost;
    int quan;

    int itemlistCount=0;
    int count=0,initCount;
    int total=0,initTotal;
    int receiptCount;

    Toolbar tool;
    File file;      //pdf file
    String name="",email,no,pay="",pobox,address,location;
    String timeStamp;
    int pdfCount=0;
    SharedPreferences shp;
    String contact ="contactKey";

    private String user;
    private EditText lipa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_home);
        tool= findViewById(R.id.bar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Receipt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalView=findViewById(R.id.totaltxt);

        goodsViewModel= ViewModelProviders.of(this).get(GoodsViewModel.class);
        pickedGoodViewModel=ViewModelProviders.of(this).get(PickedGoodViewModel.class);
        dailySalesViewModel= ViewModelProviders.of(this).get(DailySalesViewModel.class);
        weekSalesViewModel=ViewModelProviders.of(this).get(WeekSalesViewModel.class);
        monthSalesViewModel=ViewModelProviders.of(this).get(MonthSalesViewModel.class);


        //pickedGoods=pickedGoodViewModel.getAllPickedGoodsList();

        shp=getSharedPreferences("Data",Context.MODE_PRIVATE);
        user = shp.getString("current_username","");


        RecyclerView GoodsrecyclerView=findViewById(R.id.homeGoodsrecycler);
        GoodsrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        GoodsrecyclerView.setHasFixedSize(true);
        final UnitAdapter adapter=new UnitAdapter();
        GoodsrecyclerView.setAdapter(adapter);
        goodsViewModel.getAllGoods().observe(this, new Observer<List<GoodsEntity>>() {
            @Override
            public void onChanged(List<GoodsEntity> goodsEntities) {
                adapter.submitList(goodsEntities);
            }
        });

        adapter.setOnItemClickListener(new UnitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsEntity goodsEntity) {
                // do update, if item does not exist on picked table then insert
                //pickedGoods=pickedGoodViewModel.getAllPickedGoodsList();

                int res = 0;//no of rows updated
                quan=1;
                int picked_id=goodsEntity.getKEY_GOODS_ID();
                pack=goodsEntity.getKEY_PACK();
                cost=goodsEntity.getKEY_COST();
                item=goodsEntity.getKEY_ITEM();

                if(pickedGoods!=null){
                    for(PickedGoodEntity good : pickedGoods) {
                        int n = good.getKEY_picked_id();
                        int id=good.getKEY_ID();
                        if (n == picked_id) {
                            quan = quan + good.getKEY_Quantity();
                            int total=cost*quan;
                            PickedGoodEntity pickedGoodEntity=new PickedGoodEntity(quan,pack,item,cost,total,picked_id);
                            pickedGoodEntity.setKEY_ID(id);
                            res = pickedGoodViewModel.update(pickedGoodEntity);
                            break;
                        }

                    }
                }
                if (res == 0) {
                    int total=cost*quan;
                    PickedGoodEntity pickedGoodEntity=new PickedGoodEntity(quan,pack,item,cost,total,picked_id);
                    pickedGoodViewModel.insert(pickedGoodEntity);
                }
            }
        });

        RecyclerView pickedRecycler=findViewById(R.id.pickedGoodsRecycler);
        pickedRecycler.setLayoutManager(new LinearLayoutManager(this));
        pickedRecycler.setHasFixedSize(true);
        final pickedGoodAdapter pickedAdapter=new pickedGoodAdapter();
        pickedRecycler.setAdapter(pickedAdapter);
        pickedGoodViewModel.getAllPickedGoods().observe(this, new Observer<List<PickedGoodEntity>>() {
            @Override
            public void onChanged(List<PickedGoodEntity> pickedGoodEntities) {
                pickedAdapter.submitList(pickedGoodEntities);
                pickedGoods=pickedGoodEntities;
                totalView.setText(String.valueOf(totalCount()));
            }
        });
        pickedAdapter.setOnItemClickListener(new pickedGoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PickedGoodEntity pickedGoodEntity) {
                editpickedinputDialog(pickedGoodEntity.getKEY_ID(),pickedGoodEntity.getKEY_Item(),pickedGoodEntity.getKEY_Pack(),
                        pickedGoodEntity.getKEY_Cost(),pickedGoodEntity.getKEY_Quantity(),pickedGoodEntity.getKEY_picked_id());

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                 ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pickedGoodViewModel.delete(pickedAdapter.getGoodAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(pickedRecycler);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_CUSTOMER_REQUEST && resultCode==RESULT_OK){
            if (data != null) {
                name=data.getStringExtra(CustomerActivity.EXT_FNAME)+""+data.getStringExtra(CustomerActivity.EXT_SNAME);
                email=data.getStringExtra(CustomerActivity.EXT_EMAIL);
                no= String.valueOf(data.getIntExtra(CustomerActivity.EXT_PHONE,0));
                pobox= String.valueOf(data.getIntExtra(CustomerActivity.EXT_POBOX,0));
                address= String.valueOf(data.getIntExtra(CustomerActivity.EXT_ADDRESS,0));
                location=data.getStringExtra(CustomerActivity.EXT_LOCATION);

                if(pay.isEmpty()){
                    inputDialog();
                }
                else{
                    pdfCount++;
                    if(pdfCount==1){
                        pdf();
                        Toast.makeText(getApplicationContext(),"SAVED",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Pdf Already saved\nPlease Refresh",Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                break;
            case R.id.save:
                if(pickedGoods.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Cannot save empty receipt",Toast.LENGTH_SHORT).show();
                }
                if(name.isEmpty()){
                    getClient();
                    }
                else if(pay.isEmpty()){
                    inputDialog();
                }
                    else{
                        pdfCount++;
                        if(pdfCount==1){
                            pdf();
                            Toast.makeText(getApplicationContext(),"SAVED",Toast.LENGTH_SHORT).show();
                            }
                            else{
                            Toast.makeText(getApplicationContext(),"Pdf Already saved\nPlease Refresh",Toast.LENGTH_SHORT).show();
                        }
                }

                //Toast.makeText(getApplicationContext(),"Empty PDF",Toast.LENGTH_SHORT).show();

                break;
            case R.id.send:
                if(file!=null){
                    email();
                }
                Toast.makeText(getApplicationContext(),"Please save pdf",Toast.LENGTH_SHORT).show();
                break;
            case R.id.client:
               getClient();
                break;
            case R.id.newPdf:
                refresh();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void refresh(){
        startActivity(getIntent());
        finish();
        overridePendingTransition(0,0);
    }
    public void getClient(){
        Intent f=new Intent(this,CustomerActivity.class);
        f.putExtra("GetCustomer","From_homeActivity");
        startActivityForResult(f,GET_CUSTOMER_REQUEST);
    }

    public void salesInsert(){
        Date date=new Date();
        String format="hh:mm:ss a dd-MMM-yyyy";
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        sdf.format(date);
    }

    public int totalCount(){
        total=0;
        for(PickedGoodEntity good:pickedGoods){
            total=good.getKEY_total()+total;
        }
        return total;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.quanSpin:

                break;
            case R.id.spinner2:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void pdf(){
        String state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            File route=Environment.getExternalStorageDirectory();
            File dir=new File(route.getAbsolutePath()+"/DIGITALRECEIPTS","Receipts");

            if(!dir.exists()){
                dir.mkdirs();
            }
            Date date=new Date();
            SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy_HHmmss");
            timeStamp= currentDate.format(date);

             file=new File(dir,name+"_"+timeStamp+".pdf");
            try {
//creates pdf,saves and writes to it.
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                Document doc=new Document();
                PdfWriter.getInstance(doc,fileOutputStream );
                doc.open();
                shp=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                File d = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS","Logo");
                File g=new File(d,"/Logo.jpg");

                PdfPTable company=new PdfPTable(2);
                company.setWidthPercentage(60);
                PdfPCell logoCell=new PdfPCell();
                logoCell.setBorder(Rectangle.NO_BORDER);

                if(g.exists()){
                    try (FileInputStream stream = new FileInputStream(g)) {
                        Bitmap bit=BitmapFactory.decodeStream(stream);
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
                Paragraph Na=new Paragraph(shp.getString("nameKey","")+"\n"+shp.getString("emailKey","")+"\n"+"P.O. BOX"+
                        shp.getString("boxKey","")+ "\n"+shp.getString("locationKey","")+"\n"+
                        shp.getString("contactKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,18,Font.NORMAL));
   Paragraph Em=new Paragraph(shp.getString("emailKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));
                Paragraph Bo=new Paragraph("P.O. BOX"+shp.getString("boxKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));
                Paragraph Lo=new Paragraph(shp.getString("locationKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));
                Paragraph Co=new Paragraph(shp.getString("contactKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));

                Na.setAlignment(Element.ALIGN_RIGHT);
                Em.setAlignment(Element.ALIGN_RIGHT);
                Bo.setAlignment(Element.ALIGN_RIGHT);
                Lo.setAlignment(Element.ALIGN_RIGHT);
                Co.setAlignment(Element.ALIGN_RIGHT);
                doc.add(Na);
                doc.add(Em);
                doc.add(Bo);
                doc.add(Lo);
                doc.add(Co);
                doc.add(new Paragraph(new Date().toString()));


                PdfPCell Cell=new PdfPCell(new Paragraph(Na));
                company.addCell(Cell);
                doc.add(company);

                doc.add(new Paragraph(""));
                doc.add(new DottedLineSeparator());
                //doc.add(new Paragraph(timeStamp));
                //doc.addCreationDate();
                doc.add(new Paragraph("\nClient Details",FontFactory.getFont(FontFactory.TIMES_BOLD,14,Font.BOLD,BaseColor.DARK_GRAY)));
                doc.add(new Paragraph("NAME:"+name,FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("EMAIL:"+email,FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("N0   :"+no+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("PO.Box:"+pobox+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("Address:"+address+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph("LOCATION:"+location+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL)));
                doc.add(new Paragraph(new Date().toString()));
                doc.add(new Paragraph("Sale No:"+shp.getString("receiptKey","1")+".................................." +
                        "....No.of Items:"+pickedGoods.size(),FontFactory.getFont(FontFactory.TIMES_ROMAN,18,Font.NORMAL,BaseColor.DARK_GRAY)));
                doc.add(new LineSeparator());
                doc.add(new Paragraph("\n"));

                PdfPTable table=new PdfPTable(5);
                table.addCell("Quantity");
                table.addCell("Package");
                table.addCell("Item");
                table.addCell("@");
                table.addCell("Ksh");

                for(PickedGoodEntity good : pickedGoods){
                    String Q= String.valueOf(good.getKEY_Quantity());
                    String St=good.getKEY_Pack();
                    String T=good.getKEY_Item();
                    String E= String.valueOf(good.getKEY_Cost());
                    String t= String.valueOf(good.getKEY_total());

                    table.addCell(Q);
                    table.addCell(St);
                    table.addCell(T);
                    table.addCell(E);
                    table.addCell(t);
                    //doc.add(new Paragraph("\n"+itemList.get(i)));
                }
                doc.add(table);
                Paragraph noIt=new Paragraph("Total SH."+total+"\n");
                Paragraph cashT=new Paragraph("CASH TENDERED:"+pay);
                noIt.setAlignment(Element.ALIGN_RIGHT);
                cashT.setAlignment(Element.ALIGN_RIGHT);
                doc.add(noIt);
                doc.add(cashT);
                //pay used as int       NB:pay used as string above
                try{
                    int pay=Integer.parseInt(lipa.getText().toString());
                    Paragraph change=new Paragraph("CHANGE:"+(pay-total));
                    change.setAlignment(Element.ALIGN_RIGHT);
                    doc.add(change);
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }
                doc.close();
                fileOutputStream.close();
                receiptcount();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Please save logo first",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"stack trace2",Toast.LENGTH_SHORT).show();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"sdCard not mounted",Toast.LENGTH_SHORT).show();
        }
    }
    public void receiptcount(){
       String c=shp.getString("receiptKey","1");
        int g=Integer.parseInt(c);
        receiptCount=g+1;
        shp =getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putString("receiptKey", ""+receiptCount);
        editor.apply();

    }
    public void email(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "CASH SALE");
        Uri uri = Uri.fromFile(file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent,"Send Email"));
        }
    }

    public void inputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity.this);
        View mview=getLayoutInflater().inflate(R.layout.activity_client_dialog,null);
        lipa=mview.findViewById(R.id.clCash);

        builder.setView(mview);
        builder.setTitle("Cash Tendered");
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                 pay=lipa.getText().toString();

                if (lipa.getText().toString().isEmpty()){
                    lipa.setError("Name is empty");
                    lipa.requestFocus();
                    return;
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        lipa.setText(pay);

        AlertDialog dialog=builder.create();
        dialog.show();

    }
    public void editpickedinputDialog(final int key_id, String it, String pa, int co, final int q, final int pick_id) {
        final EditText item_edit, cost_edit, pack_edit,quan_edit;
        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.home_goods_dialog, null);
        item_edit = mview.findViewById(R.id.hitemtxt);
        cost_edit = mview.findViewById(R.id.hunittxt);
        pack_edit = mview.findViewById(R.id.hpacktxt);
        quan_edit=mview.findViewById(R.id.hquantxt);

        item_edit.setText(it);
        pack_edit.setText(pa);
        cost_edit.setText(String.valueOf(co));
        quan_edit.setText(String.valueOf(q));
        builder.setView(mview);
        builder.setTitle("Edit");
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String item = item_edit.getText().toString();
                int cost = Integer.parseInt(cost_edit.getText().toString());
                String pack = pack_edit.getText().toString();
                int newq= Integer.parseInt(quan_edit.getText().toString());

                int tot=cost*newq;

                PickedGoodEntity pickedGoodEntity = new PickedGoodEntity(newq,pack, item, cost,tot,pick_id);
                pickedGoodEntity.setKEY_ID(key_id);
                pickedGoodViewModel.update(pickedGoodEntity);

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
