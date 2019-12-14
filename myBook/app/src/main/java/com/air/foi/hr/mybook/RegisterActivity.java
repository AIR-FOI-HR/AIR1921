package com.air.foi.hr.mybook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fragments.AnketaInteresiFragment;

import android.app.DatePickerDialog;
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

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                registration();
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

    private void registration() {
        String username = this.username.getText().toString().trim();
        String firstName = this.firstName.getText().toString().trim();
        String lastName = this.lastName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String passRepeat = passwordRepeat.getText().toString().trim();
        String date = displayDate.getText().toString().trim();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
                && !TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(passRepeat)
                && !TextUtils.isEmpty(date)){
            if (pass.equals(passRepeat)){
                Korisnik korisnik = new Korisnik(username, firstName, lastName, pass, mail, date);
                databaseKorisnici.child(username).setValue(korisnik);
                Toast.makeText(this, "Korisnik dodan", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this, "Lozinke se ne podudaraju", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Unesite podatke", Toast.LENGTH_LONG).show();
    // MD-5 hashiranje koje prima plain text lozinku, a vraća hashiranu vrijednost
    private String hashPassword(String pass) {
        String generatedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (Exception e)
        {
            Log.e("Exception", String.valueOf(e));
            generatedPassword = "invalid";
        }
        return generatedPassword;
    }
}
