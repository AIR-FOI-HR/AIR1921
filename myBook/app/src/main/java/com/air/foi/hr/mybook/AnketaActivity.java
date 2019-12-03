package com.air.foi.hr.mybook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class AnketaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa);
        Toolbar toolbar = findViewById(R.id.toolbarAnketa);
        setSupportActionBar(toolbar);
    }
}
