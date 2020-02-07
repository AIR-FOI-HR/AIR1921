package com.air.foi.hr.mybook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import fragments.LoginFragment;
import fragments.RegistrationFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.buttonLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.i(TAG, "Prijava clicked!");
                setContentView(R.layout.layout_login_register);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameReg, new LoginFragment())
                        .commit();
            }
        });

        Button register = findViewById(R.id.buttonRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.i(TAG, "Registracija clicked!");
                setContentView(R.layout.layout_login_register);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameReg, new RegistrationFragment())
                        .commit();
            }
        });
    }
}
