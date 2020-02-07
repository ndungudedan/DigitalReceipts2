package com.example.dedan.digitalreceipts;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    ReceiptViewModel receiptViewModel;
    Toolbar Rtool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcpt_list);
        Rtool= findViewById(R.id.Rbar);
        setSupportActionBar(Rtool);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView=findViewById(R.id.receiptsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ReceiptAdapter adapter=new ReceiptAdapter();
        recyclerView.setAdapter(adapter);

        receiptViewModel= ViewModelProviders.of(this).get(ReceiptViewModel.class);
        receiptViewModel.getAllReceipts().observe(this, new Observer<List<ReceiptEntity>>() {
            @Override
            public void onChanged(List<ReceiptEntity> receiptEntities) {
                adapter.submitList(receiptEntities);
            }
        });
        adapter.setOnItemClickListener(new ReceiptAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ReceiptEntity receiptEntity) {
                openPdf(receiptEntity.getKEY_rcpt_ref());
            }
        });
    }

    public void openPdf(String name_of_file){
        Uri uri = Uri.fromFile(new File(name_of_file));
        Intent pdfIntent=new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(uri,"application/pdf");
        Intent intent = Intent.createChooser(pdfIntent, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
// Instruct the user to install a PDF reader here, or something
        }
    }
}
