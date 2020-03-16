package com.example.dedan.digitalreceipts;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

public class company_reports_fragment extends Fragment {
    private RecyclerView recyclerView;
    private ReceiptAdapter receiptAdapter;
    private ReceiptViewModel receiptViewModel;

    public company_reports_fragment() {
        // Required empty public constructor
    }

    public static company_reports_fragment newInstance(String param1, String param2) {
        company_reports_fragment fragment = new company_reports_fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiptViewModel= ViewModelProviders.of(this).get(ReceiptViewModel.class);
        receiptAdapter=new ReceiptAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_company_reports_fragment, container, false);
        recyclerView=view.findViewById(R.id.report_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(receiptAdapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        receiptViewModel.getAllReports().observe(this, new Observer<List<ReceiptEntity>>() {
            @Override
            public void onChanged(List<ReceiptEntity> receiptEntities) {
                receiptAdapter.submitList(receiptEntities);
            }
        });
        receiptAdapter.setOnItemClickListener(new ReceiptAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ReceiptEntity receiptEntity) {
                openPdf(receiptEntity.getKEY_rcpt_ref());
            }
        });
    }

    private void openPdf(String filename) {
        Uri uri = Uri.fromFile(new File(filename));
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
