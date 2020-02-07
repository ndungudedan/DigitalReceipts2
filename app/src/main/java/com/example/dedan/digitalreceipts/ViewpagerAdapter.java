package com.example.dedan.digitalreceipts;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private int count=2;
    private final List<Fragment> fragmentList=new ArrayList();
    private final List<String> fragmentListTitles=new ArrayList();


    public ViewpagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragmentListTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles.get(position);
    }
    public void AddFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        fragmentListTitles.add(title);
    }
}
