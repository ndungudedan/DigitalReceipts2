package com.example.dedan.digitalreceipts;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private int count=3;
    public ViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        switch(i){
            case 0:
                    fragment=new secFragment();
                break;
            case 1:
                fragment=new transactionFragment();
                break;
            case 2:
                fragment=new summaryFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab"+(position+1);
    }
}
