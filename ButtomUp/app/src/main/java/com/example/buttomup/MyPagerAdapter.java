package com.example.buttomup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    String id;
    public MyPagerAdapter(FragmentManager fm, int numOfTabs, String id){
        super(fm);
        this.mNumOfTabs = numOfTabs;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment1 tab1 = new Fragment1(id);
                return tab1;
            case 1:
                Fragment2 tab2 = new Fragment2(id);
                return tab2;
            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
