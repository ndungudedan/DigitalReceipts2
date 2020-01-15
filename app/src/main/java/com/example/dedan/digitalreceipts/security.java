package com.example.dedan.digitalreceipts;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.List;

public class security extends AppCompatActivity implements User_StatsFragment2.OnFragmentInteractionListener {
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
            ft.add(R.id.userStatsFrame, statFrag, "A").addToBackStack("A");
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

    @Override
    public void changeFragment(int id) {
        dispstatfrag();
    }
}