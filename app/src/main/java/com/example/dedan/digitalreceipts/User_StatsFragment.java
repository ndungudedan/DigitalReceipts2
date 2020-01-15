package com.example.dedan.digitalreceipts;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class User_StatsFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserStatsAdapter userStatsAdapter;
    private UserStatsViewModel userStatsViewModel;

    public static User_StatsFragment newInstance() {
        return new User_StatsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user__stats_fragment, container, false);
        recyclerView = view.findViewById(R.id.userStatsRecycler);
        userStatsAdapter=new UserStatsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(userStatsAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userStatsViewModel=ViewModelProviders.of(this).get(UserStatsViewModel.class);
        userStatsViewModel.getAllstats().observe(this, new Observer<List<UserStatsEntity>>() {
            @Override
            public void onChanged(List<UserStatsEntity> userStatsEntities) {
                userStatsAdapter.submitList(userStatsEntities);
            }
        });
    }

    @Override
    public void onDestroy() {
        userStatsViewModel.deleteAll();
        super.onDestroy();
    }
}
