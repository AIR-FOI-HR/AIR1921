package hr.foi.air.mybook.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ZaboravljenaLozinkaFragment extends Fragment {

    private static final String TAG = "ZaboravLozinkaFragment";

    private EditText email;
    private ProgressBar progressBar;

    public ZaboravljenaLozinkaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zaboravljena_lozinka, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.getRootView().findViewById(R.id.toolbar);
        toolbar.setTitle("Zaboravljena lozinka");

        progressBar = view.getRootView().findViewById(R.id.progressBar);

        TextView login = view.findViewById(R.id.prijava_zaboravljena_lozinka);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.i(TAG, "Prijava clicked!");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.hide(ZaboravljenaLozinkaFragment.this);
                fragmentTransaction.replace(R.id.frameReg, loginFragment);
                fragmentTransaction.commit();
            }
        });

        final FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        email = view.findViewById(R.id.email_zaboravljena_lozinka);
        Button save = view.findViewById(R.id.button_spremi_zaboravljena_lozinka);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String mail = email.getText().toString();

                String regex = "^([^.]([A-Za-z0-9]*[.]?[A-Za-z0-9]*)@[A-Za-z0-9]*\\.[A-Za-z0-9]{2,})$";
                Pattern pattern = Pattern.compile(regex);

                if (!mail.isEmpty()) {
                    if (pattern.matcher(mail).matches()) {
                        firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.i(TAG, "Password reset mail send!");
                                    Toast.makeText(getActivity(), "Poslan link za promjenu lozinke na e-mail", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    LoginFragment loginFragment = new LoginFragment();
                                    fragmentTransaction.hide(ZaboravljenaLozinkaFragment.this);
                                    fragmentTransaction.replace(R.id.frameReg, loginFragment);
                                    fragmentTransaction.commit();
                                }
                                else {
                                    Log.i(TAG, "Password reset failed!");

                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.custom_toast,
                                            (ViewGroup) view.findViewById(R.id.custom_toast_container));

                                    TextView text = layout.findViewById(R.id.text);
                                    text.setText("Greška, provjerite unesen email!");
                                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    text.setTextColor(Color.rgb(113,0,43));

                                    Toast toast = new Toast(getContext());
                                    toast.setGravity(Gravity.BOTTOM, 0, 180);
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(layout);
                                    toast.show();


                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                    else {
                        Log.i(TAG, "Wrong email");
                        Toast.makeText(getActivity(), "Pogrešan foormat e-maila", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
