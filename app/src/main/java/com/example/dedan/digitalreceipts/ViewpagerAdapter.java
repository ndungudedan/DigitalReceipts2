package com.example.dedan.digitalreceipts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewpagerAdapter extends FragmentStatePagerAdapter {
    private int count=3;
    public String z;
    public long l;
    private security security;

    public ViewpagerAdapter(FragmentManager fm) {
        super(fm);
        this.z = z;
        this.l = l;
    }
    /*public ViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }*/

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        switch(i){
            case 0:

                    z= com.example.dedan.digitalreceipts.security.checkuser;
                    l= com.example.dedan.digitalreceipts.security.empID;

                    fragment=secFragment.newInstance(l,z);
                break;
            case 1:
                fragment=analytics_user.newInstanceAnalytics(l);
                break;
            case 2:
                fragment=new summaryFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Users";
            case 1:
                return "User_Analytics";
            case 2:
                return "Company_Analytics";
        }
        return null;
    }
}
