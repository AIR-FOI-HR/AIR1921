package hr.foi.air.mybook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import hr.foi.air.mybook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;


public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    private EditText email;
    private  EditText password;
    private ProgressBar progressBar;

    private FirebaseUser firebaseUser;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        TextView registration = view.findViewById(R.id.registration);
        TextView forgotPassword = view.findViewById(R.id.forgotPassword);

        progressBar = view.getRootView().findViewById(R.id.progressBar);
        Toolbar toolbar = view.getRootView().findViewById(R.id.toolbar);
        toolbar.setTitle("Prijava");

        final FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.i(TAG, "Go to registration!");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegistrationFragment registrationFragment = new RegistrationFragment();
                fragmentTransaction.hide(LoginFragment.this);
                fragmentTransaction.replace(R.id.frameReg, registrationFragment);
                fragmentTransaction.commit();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Go to forgotten password!");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ZaboravljenaLozinkaFragment zaboravljenaLozinkaFragment = new ZaboravljenaLozinkaFragment();
                fragmentTransaction.hide(LoginFragment.this);
                fragmentTransaction.replace(R.id.frameReg, zaboravljenaLozinkaFragment);
                fragmentTransaction.commit();
            }
        });

        Button login = view.findViewById(R.id.buttonLogin);
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
                                        Toast.makeText(getActivity(), "Uspješna prijava " + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        Intent intent = new Intent(getContext(), PocetnaFragment.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Log.i(TAG, "Wrong email or password");
                                        Toast.makeText(getActivity(), "Pogrešan e-mail ili lozinka", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else {
                            Log.i(TAG, "Password is too short.");
                            Toast.makeText(getActivity(), "Lozinka je prekratka", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    else {
                        Log.i(TAG, "Wrong email format.");
                        Toast.makeText(getActivity(), "Pogrešno upisan email", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else {
                    Log.i(TAG, "Empty login data passed.");
                    Toast.makeText(getActivity(),"Unesite podatke", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
