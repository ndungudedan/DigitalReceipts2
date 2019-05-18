package com.example.dedan.digitalreceipts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public  class dominion extends dropDown {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down);

        dropbar=findViewById(R.id.Dbar);
        droptxt=findViewById(R.id.dropTxt);
        list = findViewById(R.id.dropList);
        add = findViewById(R.id.dropAdd);

        getSupportActionBar().setSubtitle("Dominion");
        droptxt.setHint("Dominion");

        dropList = new ArrayList<>();
        dropList.clear();
         //create an array adapter from listView
       /* dropAdapt = new ArrayAdapter<CharSequence>
            (this, android.R.layout.simple_list_item_1, dropList);
        list.setAdapter(dropAdapt);*/

        receiptList=new ArrayList<>();
        customListAdapter customListAdapter=new customListAdapter(this,R.layout.adapter_view,receiptList);
        list.setAdapter(customListAdapter);

        addDrop();
        get();
        DELETE();
        //delete();
    }

    @Override
    public void addDrop() {
       // super.addDrop();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence T = droptxt.getText().toString();
                load load=new load((String) T);
                // load.setFilename((String) T);
                //    receiptList.add(new load((String) T));
                receiptList.add(load);

                //dropAdapt.add(T);
                //  dropAdapt.notifyDataSetChanged();
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    File route = Environment.getExternalStorageDirectory();
                    File dir = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS", "Resources");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    File file = new File(dir, "Dominion" + ".txt");
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        for (int i = 0; i < receiptList.size(); i++) {
                            CharSequence a = receiptList.get(i).getFilename()+"\n";
                            fileOutputStream.write(a.toString().getBytes());
                        }
                        fileOutputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    droptxt.getText().clear();


                }
            }
        });
    }

    @Override
    public void get() {
        //super.get();
        File route = Environment.getExternalStorageDirectory();
        File dir = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS", "Resources");
        File g=new File(dir,"/Dominion.txt");
        try (FileInputStream stream = new FileInputStream(g)) {
            DataInputStream dataInputStream=new DataInputStream(stream);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(dataInputStream));
            CharSequence V;
            while ((V=bufferedReader.readLine())!=null){
                receiptList.add(new load ((String) V));
            }
         //   dropAdapt.notifyDataSetChanged();
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        final ArrayList<Integer> listIndex;
        listIndex=new ArrayList<Integer>();
        //super.delete();
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
                        // countItem.setText(""+itemlistCount);
                        selectList.add(dropList.get(i));

                    }
                    else{
                        list.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.lightBackground));
                        count--;
                        // itemCount();
                        selectList.remove(dropList.get(i));

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
                            for(CharSequence msg:selectList){
                                dropAdapt.remove(msg);
                            }
                            count=0;
                            actionMode.finish();
                            RefreshDrop();
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
                    getSupportActionBar().show();

                }
            });
        }

    @Override
    public void RefreshDrop() {
        //super.RefreshDrop();
        dropAdapt.notifyDataSetChanged();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File route = Environment.getExternalStorageDirectory();
            File dir = new File(route.getAbsolutePath() + "/DIGITALRECEIPTS", "Resources");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, "Dominion" + ".txt");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                for (int i = 0; i < dropList.size(); i++) {
                    CharSequence a = dropList.get(i)+"\n";
                    fileOutputStream.write(a.toString().getBytes());
                }
                fileOutputStream.close();
                dropAdapt.notifyDataSetChanged();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.btntry:
                Intent dom=new Intent(this,dropDown.class);
                startActivity(dom);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void DELETE() {
        customListAdapter customListAdapter=new customListAdapter(this,R.layout.adapter_view,receiptList);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        final com.example.dedan.digitalreceipts.customListAdapter finalCustomListAdapter = customListAdapter;
        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if(b){
                    count  = list.getCheckedItemCount();
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
                        SparseBooleanArray selected = finalCustomListAdapter.getSelectedIds();

                        for (int i =  (selected.size() - 1); i >= 0; i--) {
                            if  (selected.valueAt(i)) {
                                load load;
                                load= finalCustomListAdapter.getItem(selected.keyAt(i));
                                 String  selecteditem =finalCustomListAdapter.getItem(selected.keyAt(i)).getFilename();
                                // Remove  selected items following the ids
                                finalCustomListAdapter.remove(load);

                                String path = Environment.getExternalStorageDirectory().toString()+"/DIGITALRECEIPTS";
                                File directory = new File(path,"Resources");

                                if (!directory.exists()) {
                                    directory.mkdir();
                                }
                                File file = new File(directory, "Dominion" + ".txt");
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                                    for (int j = 0; j < receiptList.size(); j++) {
                                        CharSequence a = receiptList.get(j)+"\n";
                                        fileOutputStream.write(a.toString().getBytes());
                                    }
                                    fileOutputStream.close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }

                             //   File file = new File(directory, "Dominion" + ".txt");

/*                                String H= null;
                                if (load != null) {
                                    H = load.getFilename();
                                    File V=new File(file,H);
                                    V.delete();
                                }*/
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
