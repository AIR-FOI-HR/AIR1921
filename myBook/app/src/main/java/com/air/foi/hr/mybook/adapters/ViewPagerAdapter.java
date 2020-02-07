package com.air.foi.hr.mybook.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import fragments.PrijedloziFragment;
import fragments.TrenutnoCitamFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int sviTabovi;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int brojTabova) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.sviTabovi=brojTabova;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PrijedloziFragment();
            case 1:
                return new TrenutnoCitamFragment();
            default:
                throw new IllegalStateException();


        }

    }

    @Override
    public int getCount() {
        return sviTabovi;
    }
}
