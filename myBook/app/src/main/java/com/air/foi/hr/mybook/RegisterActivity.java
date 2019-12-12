package com.air.foi.hr.mybook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import fragments.AnketaInteresiFragment;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.air.foi.hr.database.entities.Korisnik;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    EditText username;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText passwordRepeat;
    private EditText displayDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    DatabaseReference databaseKorisnici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseKorisnici = FirebaseDatabase.getInstance().getReference("korisnik");

        displayDate = findViewById(R.id.birth_date);
        displayDate.setInputType(InputType.TYPE_NULL);
        username = findViewById(R.id.username);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordRepeat = findViewById(R.id.password_repeat);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Panel,
                        mDateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FDF7F9")));
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d(TAG, "onDateSet: date dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                displayDate.setText(date);

            }
        };

        Button registracija=findViewById(R.id.registration);
        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                setContentView(R.layout.fragment_anketa_interesi);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.anketaFragment, new AnketaInteresiFragment())
                        .commit();
                Toolbar toolbarAnketa = findViewById(R.id.toolbarAnketa);
                setSupportActionBar(toolbarAnketa);
            }
        });
    }
}