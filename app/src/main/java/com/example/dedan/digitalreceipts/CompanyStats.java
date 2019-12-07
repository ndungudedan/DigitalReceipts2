package com.example.dedan.digitalreceipts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class CompanyStats extends AppCompatActivity {
    ReceiptViewModel receiptViewModel;
    MonthSalesViewModel monthSalesViewModel;
    WeekSalesViewModel weekSalesViewModel;

    private FragmentStatePagerAdapter adapter;
    private ViewPager viewPager;
    PagerTabStrip pagerTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_stats);

        viewPager = findViewById(R.id.viewpager);
        if (viewPager != null) {
            pagerTabStrip = findViewById(R.id.pager_header);
            adapter = new ViewpagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            pagerTabStrip = findViewById(R.id.pager_header);
        }

    }
}
