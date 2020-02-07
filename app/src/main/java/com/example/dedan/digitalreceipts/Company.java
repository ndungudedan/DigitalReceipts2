package com.example.dedan.digitalreceipts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class Company extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);

        ViewpagerAdapter adapter=new ViewpagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new company_user_fragment(),"Users");
        adapter.AddFragment(new company_stat_fragment(),"Statistics");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
