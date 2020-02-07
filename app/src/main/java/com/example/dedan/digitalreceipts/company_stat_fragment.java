package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class company_stat_fragment extends Fragment {
    MonthSalesViewModel monthSalesViewModel;
    public company_stat_fragment() {
        // Required empty public constructor
    }

    public static company_stat_fragment newInstance(String param1, String param2) {
        company_stat_fragment fragment = new company_stat_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        monthSalesViewModel= ViewModelProviders.of(this).get(MonthSalesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_company_stat_fragment, container, false);
        RecyclerView recyclerMT=view.findViewById(R.id.comp_month_recycler);
        recyclerMT.setLayoutManager(new GridLayoutManager(getActivity(),4));
        final MonthTotalAdapter monthTotalAdapter=new MonthTotalAdapter();
        recyclerMT.setAdapter(monthTotalAdapter);
        monthSalesViewModel.getWeekSale("Month").observe(this, new Observer<List<MonthSalesEntity>>() {
            @Override
            public void onChanged(List<MonthSalesEntity> monthSalesEntities) {
                monthTotalAdapter.submitList(monthSalesEntities);
            }
        });

        RecyclerView recyclerMT1=view.findViewById(R.id.comp_week_recycler);
        recyclerMT1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        final MonthTotalAdapter monthTotalAdapter1=new MonthTotalAdapter();
        recyclerMT1.setAdapter(monthTotalAdapter1);

        monthSalesViewModel.getMonthSale("Week").observe(this, new Observer<List<MonthSalesEntity>>() {
            @Override
            public void onChanged(List<MonthSalesEntity> monthSalesEntities) {
                monthTotalAdapter.submitList(monthSalesEntities);
            }
        });


        return view;
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
        monthSalesViewModel.deleteAll();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
