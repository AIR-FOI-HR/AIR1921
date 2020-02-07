package hr.foi.air.mybook.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

import hr.foi.air.hr.database.entities.Korisnik;
import hr.foi.air.mybook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment {

    private static final String TAG = "RegisterFragment";

    private EditText username;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText passwordRepeat;
    private EditText displayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ProgressBar progressBar;

    private DatabaseReference databaseKorisnici;
    private FirebaseAuth firebaseAuth;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Logika
        databaseKorisnici = FirebaseDatabase.getInstance().getReference("korisnik");
        firebaseAuth = FirebaseAuth.getInstance();

        displayDate = view.findViewById(R.id.birth_date);
        displayDate.setInputType(InputType.TYPE_NULL);
        username = view.findViewById(R.id.username);
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        passwordRepeat = view.findViewById(R.id.password_repeat);
        TextView login = view.findViewById(R.id.login);

        progressBar = view.getRootView().findViewById(R.id.progressBar);
        Toolbar toolbar = view.getRootView().findViewById(R.id.toolbar);

        toolbar.setTitle("Registracija");

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
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
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.hide(RegistrationFragment.this);
                fragmentTransaction.replace(R.id.frameReg, loginFragment);
                fragmentTransaction.commit();
            }
        });

        Button registracija=view.findViewById(R.id.registration);
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
                                            Toast.makeText(getActivity(), "Registracija uspješna",
                                                    Toast.LENGTH_LONG).show();
                                            Korisnik korisnik = new Korisnik(username, firstName, lastName, mail, date);
                                            databaseKorisnici.child(username).setValue(korisnik);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            AnketaInteresiFragment anketaInteresiFragment = new AnketaInteresiFragment(korisnik);
                                            fragmentTransaction.hide(RegistrationFragment.this);
                                            fragmentTransaction.replace(R.id.frameReg, anketaInteresiFragment);
                                            fragmentTransaction.commit();
                                        }else{
                                            Toast.makeText(getActivity(), task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Pogrešno upisan email", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Lozinke se ne podudaraju", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Lozinke je prekratka", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Unesite podatke", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getActivity(), "Korisničko ime zauzeto", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getActivity(), "Unesite korisničko ime", Toast.LENGTH_LONG).show();
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
