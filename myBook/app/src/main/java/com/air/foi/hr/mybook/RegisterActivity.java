package com.air.foi.hr.mybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fragments.AnketaInteresiFragment;

import android.app.DatePickerDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.air.foi.hr.database.entities.Korisnik;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    ProgressBar progressBar;
    TextView login;

    DatabaseReference databaseKorisnici;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseKorisnici = FirebaseDatabase.getInstance().getReference("korisnik");
        firebaseAuth = FirebaseAuth.getInstance();

        displayDate = findViewById(R.id.birth_date);
        displayDate.setInputType(InputType.TYPE_NULL);
        username = findViewById(R.id.username);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordRepeat = findViewById(R.id.password_repeat);
        progressBar = findViewById(R.id.progressBar);
        login = findViewById(R.id.login);

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.i(TAG, "Prijava clicked!");
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        Button registracija=findViewById(R.id.registration);
        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String userName = username.getText().toString();
                usernameVerification(userName);
            }
        });
    }

    private void registration() {
        final String username = this.username.getText().toString().trim();
        final String firstName = this.firstName.getText().toString().trim();
        final String lastName = this.lastName.getText().toString().trim();
        final String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String passRepeat = passwordRepeat.getText().toString().trim();
        final String date = displayDate.getText().toString().trim();

        String regex = "^([^.]([A-Za-z0-9]*[.]?[A-Za-z0-9]*)@[A-Za-z0-9]*\\.[A-Za-z0-9]{2,})$";
        Pattern pattern = Pattern.compile(regex);

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
                && !TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(passRepeat)
                && !TextUtils.isEmpty(date)) //provjera popunjenosti polja
        {
                if (pass.length() >= 6) {
                    if (pass.equals(passRepeat)) //provjera jednakosti lozinka
                    {
                            Matcher matcher = pattern.matcher(mail);
                            if (matcher.matches()) //provjera regularnog izraza email adrese
                            {
                                progressBar.setVisibility(View.VISIBLE);
                                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                                        password.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                progressBar.setVisibility(View.GONE);
                                                if (task.isSuccessful()){
                                                    Toast.makeText(RegisterActivity.this, "Registracija uspješna",
                                                            Toast.LENGTH_LONG).show();
                                                    Korisnik korisnik = new Korisnik(username, firstName, lastName, mail, date);
                                                    databaseKorisnici.child(username).setValue(korisnik);
                                                    setContentView(R.layout.fragment_anketa_interesi);
                                                    getSupportFragmentManager()
                                                            .beginTransaction()
                                                            .replace(R.id.anketaFragment, new AnketaInteresiFragment())
                                                            .commit();
                                                    Toolbar toolbarAnketa = findViewById(R.id.toolbarAnketa);
                                                    setSupportActionBar(toolbarAnketa);
                                                }else{
                                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(this, "Pogrešno upisan email", Toast.LENGTH_LONG).show();
                            }
                    } else {
                        Toast.makeText(this, "Lozinke se ne podudaraju", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Lozinke je prekratka", Toast.LENGTH_LONG).show();
                }
        }
        else {
            Toast.makeText(this, "Unesite podatke", Toast.LENGTH_LONG).show();
        }
    }

    private void usernameVerification(final String username){
        databaseKorisnici.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if (!username.isEmpty()) {
                        Korisnik korisnik = dataSnapshot.child(username).getValue(Korisnik.class);
                        if (korisnik.getKorime().equals(username)) {
                            //Korisničko ime već postoji;
                            Log.i("login","Zauzeto korime");
                            Toast.makeText(RegisterActivity.this, "Korisničko ime zauzeto", Toast.LENGTH_LONG).show();
                        }
                        else {
                            //Korisničko ime je ok;
                            Log.i("login","Ne postoji :)");
                            registration();
                        }
                    }
                    else {
                        //Korisničko ime već postoji;
                        Log.i("login","Nije unešeno");
                        Toast.makeText(RegisterActivity.this, "Unesite korisničko ime", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    //Korisničko ime je ok;
                    Log.i("login","Ne postoji :)");
                    registration();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
