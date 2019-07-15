package com.example.dedan.digitalreceipts;

import android.app.LoaderManager;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
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
import java.util.ArrayList;
import java.util.Date;

public class homeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SPIN_LOAD = 1;
    private static final int SPINQ_LOAD = 2;
    int itemlistCount=0;
    int count=0,initCount;
    int total=0,initTotal;
    int receiptCount;

    ArrayList<CharSequence> spinList;
    ArrayList<CharSequence> spinListQ;
    ArrayAdapter<CharSequence> spinAdapt;
    ArrayAdapter<CharSequence> spinAdaptQ;
    Spinner spin,spinQ;
    String spinItem="";

    Button btn;

    EditText jina,ema,namba,lipa;
    EditText quant;
    EditText item;
    EditText kila;
    EditText price;
    ListView list;
    TextView countItem;
    TextView countTotal;
    ArrayList<String> itemList;
    ArrayList<String> selectList;
    ArrayList<Integer> Gpoint;
    ArrayAdapter<String> arrayAdapter;
    Toolbar tool;
    File file;      //pdf file
    String name,email,no,pay;
    String timeStamp;
    int pdfCount=0;
    SharedPreferences shp;
    String contact ="contactKey";
    private SimpleCursorAdapter adapter;
    private SimpleCursorAdapter adapterQ;
    SqlOpenHelper sqlOpenHelper;
    private int itemIdpos;
    private Cursor mcursor;
    private Cursor pcursor;
    private SQLiteDatabase db;
    ArrayList selectSpinid;
    private String itemfrmspin;
    private String packfrmspin;
    private String costfrmspin;

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tool= findViewById(R.id.bar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Receipt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectSpinid=new ArrayList();
        Gpoint=new ArrayList<>();
        sqlOpenHelper = new SqlOpenHelper(this);

        quant= findViewById(R.id.quantity);
        item= findViewById(R.id.items);
        kila= findViewById(R.id.each);
        price= findViewById(R.id.cost);
        countItem= findViewById(R.id.itemCount);
        countTotal= findViewById(R.id.totalCount);
        list = findViewById(R.id.list);
        btn= findViewById(R.id.btnadd);
        selectList = new ArrayList<>();

        spin= findViewById(R.id.spinner2);
        spinList=new ArrayList<CharSequence>();

        spinQ= findViewById(R.id.quanSpin);
        spinListQ=new ArrayList<CharSequence>();


        adapter = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,null,new String[]
                {SqlOpenHelper.KEY_ITEM},new int[]{android.R.id.text1},0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinAdapt=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,spinList);
       // spinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
       // spinAdapt.add("");
       // spinPop();
        getLoaderManager().initLoader(SPIN_LOAD,null,this);
        spin.setOnItemSelectedListener(this);

        adapterQ = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,null,new String[]
                {SqlOpenHelper.KEY_PACK},new int[]{android.R.id.text1},0);

        //spinAdaptQ=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,spinListQ);
        adapterQ.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinQ.setAdapter(adapterQ);
//        spinAdaptQ.add("");
        //spinPop2();
        getLoaderManager().initLoader(SPINQ_LOAD,null,this);
        spinQ.setOnItemSelectedListener(this);

        itemList = new ArrayList<>();
        // create an array adapter from listView
        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, itemList);
        list.setAdapter(arrayAdapter);

        shp=getSharedPreferences("Data",Context.MODE_PRIVATE);
        user = shp.getString("current_username","");

        addClick();
        delete();
        //graphAmount();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mcursor.close();
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

                    if(name==null){
                        inputDialog();
                    }
                    else{
                        pdfCount++;
                        if(pdfCount==1){
                            pdf();
                            stats();
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
               inputDialog();
                break;
            case R.id.newPdf:
                //recreate();
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
    public int getUser() {
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper.KEY_NAME, SqlOpenHelper.KEY_empNO};
        Cursor cursor = db.query(SqlOpenHelper.TABLE_USER, columns, null, null,
                null, null, null);

        //int IdPos = cursor.getColumnIndex(SqlOpenHelper.KEY_ID);
        int userPos = cursor.getColumnIndex(SqlOpenHelper.KEY_NAME);
        int empPos = cursor.getColumnIndex(SqlOpenHelper.KEY_empNO);
        //refresh views here so that they can load again
        int n = 0;
        while (cursor.moveToNext()) {
            String c = cursor.getString(userPos);
            if (c.equals(user)) {
                n = cursor.getInt(empPos);
            }

        }
        cursor.close();
        return n;
    }
    public void stats(){
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper.KEY_TALLY, SqlOpenHelper.KEY_MONTH,SqlOpenHelper.KEY_WEEK,SqlOpenHelper.KEY_YESTERDAY,
                SqlOpenHelper.KEY_TODAY,SqlOpenHelper.KEY_EMP_FOREIGN};
        Cursor cursor = db.query(SqlOpenHelper.TABLE_SALES, columns, null, null,
                null, null, null);

        int IdPos = cursor.getColumnIndex(SqlOpenHelper.KEY_EMP_FOREIGN);
        int yesterdayPos = cursor.getColumnIndex(SqlOpenHelper.KEY_YESTERDAY);
        int weekPos = cursor.getColumnIndex(SqlOpenHelper.KEY_WEEK);
        int todayPos = cursor.getColumnIndex(SqlOpenHelper.KEY_TODAY);
        int monthPos = cursor.getColumnIndex(SqlOpenHelper.KEY_MONTH);
        int tallyPos = cursor.getColumnIndex(SqlOpenHelper.KEY_TALLY);
        //refresh views here so that they can load again
        while (cursor.moveToNext()) {
            int j=getUser();
            if(j==cursor.getInt(IdPos)){
                int n = cursor.getInt(yesterdayPos);
                int c = cursor.getInt(weekPos);
                int l = cursor.getInt(todayPos);
                int p=cursor.getInt(monthPos);
                int h=cursor.getInt(tallyPos);
                update(n,c,l,p,h);
                break;
            }
        }
        cursor.close();
    }
    private void update(int n,int c,int l,int p,int h){
        int j=getUser();
        String select=SqlOpenHelper.KEY_EMP_FOREIGN+"=?";
        String[]selectArgs={String.valueOf(j)};
        ContentValues values=new ContentValues();
        values.put(SqlOpenHelper.KEY_TODAY,l+total);
        values.put(SqlOpenHelper.KEY_YESTERDAY,n);
        values.put(SqlOpenHelper.KEY_MONTH,p+total);
        values.put(SqlOpenHelper.KEY_WEEK,c+total);
        values.put(SqlOpenHelper.KEY_TALLY,h+pdfCount);

        SQLiteDatabase db=sqlOpenHelper.getWritableDatabase();
        db.update(SqlOpenHelper.TABLE_SALES,values,select,selectArgs);
    }

    public void addClick() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity = quant.getText().toString();
                String items = item.getText().toString();
                String each = kila.getText().toString();
                if(quant.getText().toString().isEmpty()){
                    quantity="1";
                }
                mCost();
                String cost = price.getText().toString();

                String Summary = quantity+"_"+ spinItem+":\t" + items + "\t@" + each +"\tSH." + cost ;

                if (price.getText().toString().isEmpty()){
                    price.setError("Cost is empty");
                    price.requestFocus();
                    return;
                }
                if (item.getText().toString().isEmpty()){
                    item.setError("Item is empty");
                    item.requestFocus();
                    return;
                }
                arrayAdapter.add(Summary);
                arrayAdapter.notifyDataSetChanged();

                itemCount();
               totalCount();
                clear();
            }
        });

  }
    public void mCost(){
        if(quant.getText().toString().isEmpty()){

        }
        else{
            try{
                int q,e,p;
                q=Integer.parseInt(quant.getText().toString());
                e=Integer.parseInt(kila.getText().toString());
                p=q*e;
                price.setText(""+p);}
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
    }
    public void clear(){
        quant.getText().clear();
        item.getText().clear();
        kila.getText().clear();
        price.getText().clear();
    }

    public void delete(){
        final ArrayList<Integer> listIndex;
        listIndex=new ArrayList<Integer>();
       // listView.setLongClickable(true);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
              if(b){
                  listIndex.add(i);
                  list.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.listHighlight));
                  count=count+1;
                  itemlistCount=itemlistCount-1;
                  countItem.setText(""+itemlistCount);
                  selectList.add(itemList.get(i));

                  String s=itemList.get(i).substring(itemList.get(i).indexOf(".")+1);
                  int c=Integer.parseInt(s);
                  total=total-c;
                  countTotal.setText("Ksh."+total);
              }
                else{
                  list.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.lightBackground));
                  count--;
                  itemCount();
                  selectList.remove(itemList.get(i));
                  String t=itemList.get(i).substring(itemList.get(i).indexOf(".")+1);
                  int c=Integer.parseInt(t);
                  total=total+c;
                  countTotal.setText("Ksh."+total);

              }
                actionMode.setTitle(count+"items selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater=actionMode.getMenuInflater();
                inflater.inflate(R.menu.del_menu, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.delete:
                        for(String msg:selectList){
                            arrayAdapter.remove(msg);
                        }
                        count=0;
                        actionMode.finish();
                        return true;
                       // break;
                    default:
                        return false;

                }
               // return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                for(int x=0;x<listIndex.size();x++){
                  int y=listIndex.get(x);
                    list.getChildAt(y).setBackgroundColor(getResources().getColor(R.color.lightBackground));
                }
                count=0;
                countTotal.setText("Ksh."+initTotal);
                countItem.setText(""+initCount);
                selectList.clear();
                actionMode.finish();
            }
        });
    }

    public void itemCount(){
        itemlistCount=itemlistCount+1;
        countItem.setText(""+itemlistCount);
        initCount=itemlistCount;
    }

    public void totalCount(){
        String p=price.getText().toString();
       int  tot=Integer.parseInt(p);
        total=total+tot;
        countTotal.setText("Ksh."+total);
        initTotal=total;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.quanSpin:
                //bcoz spiiner id starts at 0 while db rows from 1

                load(l);
                spinItem=packfrmspin;

                break;
            case R.id.spinner2:
                int x=i+1;
                load(l);
                item.setText(itemfrmspin);
                kila.setText(costfrmspin);
                spinQ.setSelection(i);

        }
    }
    public void load(long spinid){
        SQLiteDatabase db = sqlOpenHelper.getReadableDatabase();
        String[] columns = {SqlOpenHelper._ID,SqlOpenHelper.KEY_ITEM,SqlOpenHelper.KEY_PACK,SqlOpenHelper.KEY_COST};
        mcursor = db.query(SqlOpenHelper.TABLE_ITEM_DETAILS, columns, null, null,
                null, null, null);
        int IdPos = mcursor.getColumnIndex(SqlOpenHelper._ID);
        int itemPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_ITEM);
        int packPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_PACK);
        int costPos = mcursor.getColumnIndex(SqlOpenHelper.KEY_COST);
        //refresh views here so that they can load again
        while (mcursor.moveToNext()) {
            long id = mcursor.getLong(IdPos);
            if(id==spinid){
                itemfrmspin = mcursor.getString(itemPos);
                packfrmspin = mcursor.getString(packPos);
                costfrmspin = mcursor.getString(costPos);
                break;
            }

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
                com.itextpdf.text.Document doc=new com.itextpdf.text.Document();
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
                Paragraph Na=new Paragraph(shp.getString("nameKey","")+"\n"+shp.getString("emailKey","")+"\n"+"P.O. BOX"+shp.getString("boxKey","")+
                        "\n"+shp.getString("locationKey","")+"\n"+shp.getString("contactKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,18,Font.NORMAL));
             /*   Paragraph Em=new Paragraph(shp.getString("emailKey",""),FontFactory.getFont(FontFactory.TIMES_ROMAN,14,Font.NORMAL,BaseColor.MAGENTA));
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
                doc.add(new Paragraph(new Date().toString()));*/

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
                doc.add(new Paragraph(new Date().toString()));
                doc.add(new Paragraph("Sale No:"+shp.getString("receiptKey","1")+".................................." +
                        "....No.of Items:"+itemlistCount,FontFactory.getFont(FontFactory.TIMES_ROMAN,18,Font.NORMAL,BaseColor.DARK_GRAY)));
                doc.add(new LineSeparator());
                doc.add(new Paragraph("\n"));

                PdfPTable table=new PdfPTable(5);
                table.addCell("Quantity");
                table.addCell("Package");
                table.addCell("Item");
                table.addCell("@");
                table.addCell("Ksh");

                for(int i=0;i<itemList.size();i++){
                    String Q=itemList.get(i).substring(0,itemList.get(i).indexOf("_"));
                    String St=itemList.get(i).substring(itemList.get(i).indexOf("_")+1,itemList.get(i).indexOf(":"));
                    String T=itemList.get(i).substring(itemList.get(i).indexOf(":")+1,itemList.get(i).indexOf("@"));
                   // String E=itemList.get(i).substring(itemList.get(i).indexOf("@")+1,itemList.get(i).indexOf("S"));
                    String E=itemList.get(i).substring(itemList.get(i).indexOf("@")+1,itemList.get(i).indexOf(".")-2);
                    String t=itemList.get(i).substring(itemList.get(i).indexOf(".")+1);

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
        ema=mview.findViewById(R.id.clEmail);
        jina=mview.findViewById(R.id.clName);
        namba=mview.findViewById(R.id.clNo);
        lipa=mview.findViewById(R.id.clCash);

        builder.setView(mview);
        builder.setTitle("Client Details");
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                 email=ema.getText().toString();
                 name=jina.getText().toString();
                 no=namba.getText().toString();
                 pay=lipa.getText().toString();

                if (jina.getText().toString().isEmpty()){
                    jina.setError("Name is empty");
                    jina.requestFocus();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        ema.setText(email);
        jina.setText(name);
        namba.setText(no);
        lipa.setText(pay);

        AlertDialog dialog=builder.create();
        dialog.show();

    }
    public void spinPop(){
/*        File route = Environment.getExternalStorageDirectory();
        File dir = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS", "Resources");
        File g=new File(dir,"/Items.txt");
        try (FileInputStream stream = new FileInputStream(g)) {
            DataInputStream dataInputStream=new DataInputStream(stream);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(dataInputStream));
            CharSequence V;
            while ((V=bufferedReader.readLine())!=null){

                //Toast.makeText(getApplicationContext(),"stacktrace1"+V,Toast.LENGTH_SHORT).show();
                spinAdapt.add(""+V);
            }
            spinAdapt.notifyDataSetChanged();
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        db = sqlOpenHelper.getReadableDatabase();
        String[] columns={SqlOpenHelper.KEY_ITEM_ID,SqlOpenHelper.KEY_ITEM,SqlOpenHelper._ID,SqlOpenHelper.KEY_COST,SqlOpenHelper.KEY_PACK};
        mcursor = db.query(SqlOpenHelper.TABLE_ITEM_DETAILS,columns,
                null,null,null,null,SqlOpenHelper.KEY_ITEM);
        int IDpos=mcursor.getColumnIndex(SqlOpenHelper._ID);
        int packPos=mcursor.getColumnIndex(SqlOpenHelper.KEY_PACK);
        int costPos=mcursor.getColumnIndex(SqlOpenHelper.KEY_COST);
        int itemPos=mcursor.getColumnIndex(SqlOpenHelper.KEY_ITEM);
        while(mcursor.moveToNext()){
            String t=mcursor.getString(itemPos);
            String c=mcursor.getString(costPos);
            String p=mcursor.getString(packPos);
            int d=mcursor.getInt(IDpos);
        }
        adapter.changeCursor(mcursor);
    }
    public void spinPop2(){/*
        File route = Environment.getExternalStorageDirectory();
        File dir = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS", "Resources");
        File g=new File(dir,"/Dominion.txt");
        try (FileInputStream stream = new FileInputStream(g)) {
            DataInputStream dataInputStream=new DataInputStream(stream);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(dataInputStream));
            CharSequence V;
            while ((V=bufferedReader.readLine())!=null){

                //Toast.makeText(getApplicationContext(),"stacktrace1"+V,Toast.LENGTH_SHORT).show();
                spinAdaptQ.add(""+V);
            }
            spinAdaptQ.notifyDataSetChanged();
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        db=sqlOpenHelper.getReadableDatabase();
        String[] columns={SqlOpenHelper.KEY_ITEM_ID,SqlOpenHelper.KEY_ITEM,SqlOpenHelper._ID,SqlOpenHelper.KEY_COST,SqlOpenHelper.KEY_PACK};
        pcursor = db.query(SqlOpenHelper.TABLE_ITEM_DETAILS,columns,
                null,null,null,null,SqlOpenHelper.KEY_ITEM);
        adapterQ.changeCursor(pcursor);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        android.content.CursorLoader loader=null;
        if(i==SPIN_LOAD)
            loader=createLoaderValues();
        if(i==SPINQ_LOAD)
            loader=createLoaderValuesQ();

        return loader;
    }

    private android.content.CursorLoader createLoaderValuesQ() {
        return new android.content.CursorLoader(this){
            @Override
            public Cursor loadInBackground() {
                db=sqlOpenHelper.getReadableDatabase();
                String[] columns={SqlOpenHelper.KEY_ITEM_ID,SqlOpenHelper.KEY_ITEM,SqlOpenHelper._ID,SqlOpenHelper.KEY_COST,SqlOpenHelper.KEY_PACK};
                return db.query(SqlOpenHelper.TABLE_ITEM_DETAILS,columns,
                        null,null,null,null,SqlOpenHelper._ID);
            }
        };
    }

    private android.content.CursorLoader createLoaderValues() {
        return new android.content.CursorLoader(this) {
            @Override
            public Cursor loadInBackground() {
                db = sqlOpenHelper.getReadableDatabase();
                String[] columns={SqlOpenHelper.KEY_ITEM_ID,SqlOpenHelper.KEY_ITEM,SqlOpenHelper._ID,SqlOpenHelper.KEY_COST,SqlOpenHelper.KEY_PACK};
                return db.query(SqlOpenHelper.TABLE_ITEM_DETAILS,columns,
                        null,null,null,null,SqlOpenHelper._ID);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId()==SPIN_LOAD) {
            adapter.changeCursor(cursor);
        }
        if(loader.getId()==SPINQ_LOAD){
            adapterQ.changeCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId()==SPIN_LOAD)
        adapter.changeCursor(null);

        if(loader.getId()==SPINQ_LOAD)
            adapterQ.changeCursor(null);
    }
}
