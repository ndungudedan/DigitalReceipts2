package com.example.dedan.digitalreceipts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewpagerAdapter extends FragmentStatePagerAdapter {
    private int count=2;

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
                fragment=new analytics_user();
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
