package com.air.foi.hr.mybook;

import androidx.appcompat.app.AppCompatActivity;

import fragments.PreporuceneKnjigeFragment;
import android.net.Uri;
import android.os.Bundle;



public class PreporuceneKnjigeActivity extends AppCompatActivity implements PreporuceneKnjigeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preporucene_knjige);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
