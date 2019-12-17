package com.air.foi.hr.mybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText email;
    private  EditText password;
    private ProgressBar progressBar;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        TextView registration = findViewById(R.id.registration);

        progressBar = findViewById(R.id.progressBar);

        final FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.i(TAG, "Go to registration!");
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        Button login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String mail = email.getText().toString();
                String pwd = password.getText().toString();

                String regex = "^([^.]([A-Za-z0-9]*[.]?[A-Za-z0-9]*)@[A-Za-z0-9]*\\.[A-Za-z0-9]{2,})$";
                Pattern pattern = Pattern.compile(regex);

                if (!mail.isEmpty() && !pwd.isEmpty()) {
                    if (pattern.matcher(mail).matches()) {
                        if (password.length() >= 6) {
                            firebaseAuth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser = firebaseAuth.getCurrentUser();
                                        Log.d(TAG, "User "+ firebaseUser.getEmail() +" logged in successfully");
                                        Toast.makeText(LoginActivity.this, "Uspješna prijava " + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        //TODO: otvoriti Main screen i onemogućiti povratak tipkom back
                                    } else {
                                        Log.i(TAG, "Wrong email or password");
                                        Toast.makeText(LoginActivity.this, "Pogrešan e-mail ili lozinka", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else {
                            Log.i(TAG, "Password is too short.");
                            Toast.makeText(LoginActivity.this, "Lozinka je prekratka", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    else {
                        Log.i(TAG, "Wrong email format.");
                        Toast.makeText(LoginActivity.this, "Pogrešno upisan email", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else {
                    Log.i(TAG, "Empty login data passed.");
                    Toast.makeText(LoginActivity.this,"Unesite podatke", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
