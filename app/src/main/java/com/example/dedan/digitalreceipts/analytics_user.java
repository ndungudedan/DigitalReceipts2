package com.example.dedan.digitalreceipts;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextClock;
import android.widget.TextView;

import com.example.dedan.digitalreceipts.Database.Today_Database.TodayEntity;
import com.example.dedan.digitalreceipts.Database.Today_Database.TodayViewModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class analytics_user extends Fragment {
    MonthSalesViewModel monthSalesViewModel;
    TodayViewModel todayViewModel;
    long current_empNo=0;
    public String user;
    SharedPreferences sharedPreferences;
    TextView yrSales,yrClients;
    TextView todSales,todClients;
    MonthTotalAdapter monthTotalAdapter;

    private OnFragmentInteractionListener mListener;
    private static analytics_user fragment;
    private RecyclerView month_recycler;

    public analytics_user() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static analytics_user newInstanceAnalytics (Long param1) {
        fragment = new analytics_user();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=getContext().getSharedPreferences("Data",MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        user = sharedPreferences.getString("current_username","");
        current_empNo=sharedPreferences.getLong("current_empNo",0);
        monthTotalAdapter=new MonthTotalAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_analytics_user, container, false);
        yrSales=view.findViewById(R.id.year_sales_sum);
        yrClients=view.findViewById(R.id.year_client_served_sum);
        todSales=view.findViewById(R.id.tod_sales_sum);
        todClients=view.findViewById(R.id.tod_client_served_sum);
        month_recycler = view.findViewById(R.id.month_sum_recycler);
        month_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        monthTotalAdapter=new MonthTotalAdapter();
        month_recycler.setAdapter(monthTotalAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     monthSalesViewModel= ViewModelProviders.of(this).get(MonthSalesViewModel.class);
     todayViewModel=ViewModelProviders.of(this).get(TodayViewModel.class);

     companystats();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void companystats(){
        todayViewModel.AllTodayEvents().observe(this, new Observer<List<TodayEntity>>() {
            @Override
            public void onChanged(List<TodayEntity> todayEntities) {
                int todaySum=0;
                int todayClients=0;
                for(TodayEntity todayEntity:todayEntities){
                    todaySum=todayEntity.getKEY_SALES()+todaySum;
                    todayClients=todayEntity.getKEY_NO_OF_CLIENTS()+todayClients;
                }
                todSales.setText(String.valueOf(todaySum));
                todClients.setText(String.valueOf(todayClients));
            }
        });
    }
}
