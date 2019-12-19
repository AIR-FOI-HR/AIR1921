package fragments;

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

import com.air.foi.hr.mybook.R;
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
        email = view.findViewById(R.id.email_zaboravljena_lozinka);
        Button save = view.findViewById(R.id.button_spremi_zaboravljena_lozinka);
    }
}
