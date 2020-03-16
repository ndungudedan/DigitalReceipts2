package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class company_stat_fragment extends Fragment {
    MonthSalesViewModel monthSalesViewModel;
    private TextView txtTotal,txtClients;
    private int total_sales,total_clients;
    private MonthTotalAdapter weekTotalAdapter;
    private RecyclerView recyclerMT;
    private RecyclerView mnthrecycler;
    private comp_month_adapter monthTotalAdapter;

    public company_stat_fragment() {
        // Required empty public constructor
    }

    public static company_stat_fragment newInstance() {
        company_stat_fragment fragment = new company_stat_fragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        monthSalesViewModel= ViewModelProviders.of(this).get(MonthSalesViewModel.class);
        weekTotalAdapter = new MonthTotalAdapter();
        monthTotalAdapter = new comp_month_adapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_company_stat_fragment, container, false);

        recyclerMT = view.findViewById(R.id.comp_week_recycler);
        recyclerMT.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        mnthrecycler = view.findViewById(R.id.comp_month_recycler);
        mnthrecycler.setLayoutManager(new GridLayoutManager(getActivity(),4));

        txtClients=view.findViewById(R.id.client_served);
        txtTotal=view.findViewById(R.id.comp_sales);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        monthSalesViewModel.getWeekSale("Week").observe(this, new Observer<List<MonthSalesEntity>>() {
            @Override
            public void onChanged(List<MonthSalesEntity> monthSalesEntities) {
                weekTotalAdapter.submitList(monthSalesEntities);
                recyclerMT.setAdapter(weekTotalAdapter);
            }
        });
        monthSalesViewModel.getMonthSale("Month").observe(this, new Observer<List<MonthSalesEntity>>() {
            @Override
            public void onChanged(List<MonthSalesEntity> monthSalesEntities) {
                for(MonthSalesEntity entity:monthSalesEntities){
                    total_sales=total_sales+entity.getKEY_total();
                    total_clients=total_clients+entity.getKEY_clients();
                }
                txtClients.setText(String.valueOf(total_clients));
                txtTotal.setText(String.valueOf(total_sales));
                monthTotalAdapter.submitList(monthSalesEntities);
                mnthrecycler.setAdapter(monthTotalAdapter);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
