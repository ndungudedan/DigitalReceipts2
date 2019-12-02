package com.example.dedan.digitalreceipts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import java.util.List;

public class CompanyStats extends AppCompatActivity {
    DailySalesViewModel dailySalesViewModel;
    MonthSalesViewModel monthSalesViewModel;
    WeekSalesViewModel weekSalesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_stats);

        dailySalesViewModel= ViewModelProviders.of(this).get(DailySalesViewModel.class);
        dailySalesViewModel.getDailySales().observe(this, new Observer<List<DailySalesEntity>>() {
            @Override
            public void onChanged(List<DailySalesEntity> dailySalesEntities) {

            }
        });

        weekSalesViewModel=ViewModelProviders.of(this).get(WeekSalesViewModel.class);
        weekSalesViewModel.getWeekSales().observe(this, new Observer<List<WeekSalesEntity>>() {
            @Override
            public void onChanged(List<WeekSalesEntity> weekSalesEntities) {

            }
        });

        monthSalesViewModel=ViewModelProviders.of(this).get(MonthSalesViewModel.class);
        monthSalesViewModel.getMonthSales().observe(this, new Observer<List<MonthSalesEntity>>() {
            @Override
            public void onChanged(List<MonthSalesEntity> monthSalesEntities) {

            }
        });
    }
}
