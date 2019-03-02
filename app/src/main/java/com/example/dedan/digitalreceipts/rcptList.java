package com.example.dedan.digitalreceipts;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class rcptList extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    ArrayList<String> storeList,selectList;
    Toolbar Rtool;
    int count;
    int itemlistCount;
    int countItem;
    String name_of_file;

    List<load> receiptList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcpt_list);
        Rtool= findViewById(R.id.Rbar);
        setSupportActionBar(Rtool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listActivity);
        receiptList=new ArrayList<>();
        viewFiles();
        customListAdapter customListAdapter=new customListAdapter(this,R.layout.adapter_view,receiptList);
        listView.setAdapter(customListAdapter);


       /* storeList = new ArrayList<>();
        selectList = new ArrayList<>();
        // create an array adapter from listView
        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, storeList);
        listView.setAdapter(arrayAdapter);*/
        open();
       // delete();
        DELETE();
    }
    public void viewFiles(){
        String path = Environment.getExternalStorageDirectory().toString()+"/DIGITALRECEIPTS";
        Log.d("Files", "Path: " + path);
        File directory = new File(path,"Receipts");
        if(directory.exists()){
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                receiptList.add(new load(files[i].getName()));
                Log.d("Files", "Size: "+ files.length);
                Log.d("Files", "FileName:" + files[i].getName());
            }
        }
    }
//    public void viewFiles(){
//        String path = Environment.getExternalStorageDirectory().toString()+"/DIGITALRECEIPTS";
//        Log.d("Files", "Path: " + path);
//        File directory = new File(path,"Receipts");
//        if(directory.exists()){
//            File[] files = directory.listFiles();
//            for (int i = 0; i < files.length; i++)
//            {
//                Log.d("Files", "Size: "+ files.length);
//                arrayAdapter.add( files[i].getName());
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("Files", "FileName:" + files[i].getName());
//            }
//        }
//    }

    public void delete(){
        final ArrayList<Integer> listIndex;
        listIndex=new ArrayList<Integer>();
        // listView.setLongClickable(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if(b){
                    listIndex.add(i);
                //    listView.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.listHighlight));
                    int y= listView.getChildAt(i).getId();
                    listView.getChildAt(y).setBackgroundColor(getResources().getColor(R.color.listHighlight));
                    count=count+1;
                    itemlistCount=itemlistCount-1;
                   // countItem.setText(""+itemlistCount);
                    selectList.add(storeList.get(i));

                }
                else{
                    listView.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.lightBackground));
                    count--;
                   // itemCount();
                    selectList.remove(storeList.get(i));

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
                            RefreshFiles();
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
                    listView.getChildAt(y).setBackgroundColor(getResources().getColor(R.color.lightBackground));
                }
                count=0;
                selectList.clear();
            }
        });
    }

    public void open (){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView v = (TextView) view.findViewById(R.id.rcpt);
                name_of_file=v.getText().toString();
               // name_of_file =adapterView.getItemAtPosition(i).toString();
                openPdf();

            }
        });
    }
    public void openPdf(){
        File route = Environment.getExternalStorageDirectory();
        File dir = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS","Receipts");
       // load load=new load(name_of_file);
      //  String H = load.getFilename();
        File V=new File(dir,name_of_file);
        Uri uri = Uri.fromFile(V);
        Intent pdfIntent=new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(uri,"application/pdf");
        Intent intent = Intent.createChooser(pdfIntent, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
// Instruct the user to install a PDF reader here, or something
        }
    }
    public void RefreshFiles(){
        String path = Environment.getExternalStorageDirectory().toString()+"/DIGITALRECEIPTS";
        Log.d("Files", "Path: " + path);
        File directory = new File(path,"Receipts");
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < selectList.size(); i++)
        {
            String H=selectList.get(i);
            File V=new File(directory,H);
            V.delete();
        }
    }
    public void DELETE(){
        customListAdapter customListAdapter=new customListAdapter(this,R.layout.adapter_view,receiptList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        final com.example.dedan.digitalreceipts.customListAdapter finalCustomListAdapter = customListAdapter;
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if(b){
                    count  = listView.getCheckedItemCount();
                    actionMode.setTitle(count+ "  Selected");
                    finalCustomListAdapter.toggleSelection(i);

                }
                else{
                    count--;
                    actionMode.setTitle(count+ "  Selected");

                }
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
                        SparseBooleanArray  selected = finalCustomListAdapter.getSelectedIds();

                        for (int i =  (selected.size() - 1); i >= 0; i--) {
                            if  (selected.valueAt(i)) {
                                load load;
                                load= finalCustomListAdapter.getItem(selected.keyAt(i));
                               // String  selecteditem =customListAdapter.getItem(selected.keyAt(i));
                                // Remove  selected items following the ids
                                finalCustomListAdapter.remove(load);

                                String path = Environment.getExternalStorageDirectory().toString()+"/DIGITALRECEIPTS";
                                File directory = new File(path,"Receipts");
                                String H= null;
                                if (load != null) {
                                    H = load.getFilename();
                                }
                                    File V=new File(directory,H);
                                    V.delete();
                            }
                        }
                        actionMode.finish();
                        selected.clear();
                     //   RefreshFiles();
                        count=0;
                        return true;
                    // break;
                    default:
                        return false;
                }
                // return false;
            }


            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }
}
