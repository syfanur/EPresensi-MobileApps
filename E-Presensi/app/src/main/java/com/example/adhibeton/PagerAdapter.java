package com.example.adhibeton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TabAbsen();
            case 1: return new TabFoto();
            case 2: return new TabLokasi();
            default: return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
