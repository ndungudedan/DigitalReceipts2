package com.example.dedan.digitalreceipts;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dedan.digitalreceipts.Month_Database.December.DecEntity;
import com.example.dedan.digitalreceipts.Month_Database.December.DecViewModel;
import com.example.dedan.digitalreceipts.Month_Database.January.JanEntity;
import com.example.dedan.digitalreceipts.Month_Database.January.JanViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class security extends AppCompatActivity {
    List<UserEntity> userList;
    ArrayAdapter<UserEntity> useradapter;
    private Fragment statFrag;
    private Fragment statFrag2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        if (savedInstanceState == null) {
            statFrag = User_StatsFragment.newInstance();
            statFrag2 = User_StatsFragment2.newInstance();
        }
        dispstatfrag2();

        }
    protected void dispstatfrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (statFrag.isAdded()) {
            ft.show(statFrag);
        } else {
            ft.add(R.id.userStatsFrame, statFrag, "A");
        }
        if (statFrag2.isAdded()) { ft.hide(statFrag2); }

        ft.commit();
    }
    protected void dispstatfrag2() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (statFrag2.isAdded()) {
            ft.show(statFrag2);
        } else {
            ft.add(R.id.userStatsFrame, statFrag2, "B");
        }
        if (statFrag.isAdded()) { ft.hide(statFrag); }

        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        MenuItem item = menu.findItem(R.id.toolspinner);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolspinner:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}