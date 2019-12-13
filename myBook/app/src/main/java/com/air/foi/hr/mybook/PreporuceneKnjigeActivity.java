package com.air.foi.hr.mybook;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;
import fragments.PreporuceneKnjigeFragment;
import android.net.Uri;
import android.os.Bundle;


import com.air.foi.hr.mybook.adapters.PrikazKnjigaAdapter;
import com.google.android.material.tabs.TabLayout;

public class PreporuceneKnjigeActivity extends AppCompatActivity implements PreporuceneKnjigeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preporucene_knjige);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayoutPreporuke);
        tabLayout.addTab(tabLayout.newTab().setText("Prijedlozi"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager=(ViewPager)findViewById(R.id.viewPagerPreporuke);
        final PrikazKnjigaAdapter adapter=new PrikazKnjigaAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
