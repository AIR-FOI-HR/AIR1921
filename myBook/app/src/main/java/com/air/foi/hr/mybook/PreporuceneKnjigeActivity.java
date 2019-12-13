package com.air.foi.hr.mybook;

import androidx.appcompat.app.AppCompatActivity;

import fragments.PreporuceneKnjigeFragment;
import android.net.Uri;
import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;

public class PreporuceneKnjigeActivity extends AppCompatActivity implements PreporuceneKnjigeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preporucene_knjige);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayoutPreporuke);
        tabLayout.addTab(tabLayout.newTab().setText("Prijedlozi"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
