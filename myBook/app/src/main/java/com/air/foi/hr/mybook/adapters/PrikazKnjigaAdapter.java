package com.air.foi.hr.mybook.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import fragments.PrijedloziFragment;

public class PrikazKnjigaAdapter extends FragmentStatePagerAdapter {

    int sviTabovi;

    public PrikazKnjigaAdapter(@NonNull FragmentManager fm, int brojTabova) {
        super(fm);
        this.sviTabovi=brojTabova;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PrijedloziFragment tabPreporucene = new PrijedloziFragment();
                return tabPreporucene;
            //case 1: za knjige koje se trenutno citaju
            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return sviTabovi;
    }
}
