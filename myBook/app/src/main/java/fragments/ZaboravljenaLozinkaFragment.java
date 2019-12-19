package fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.air.foi.hr.mybook.R;

public class ZaboravljenaLozinkaFragment extends Fragment {

    private static final String TAG = "ZaboravLozinkaFragment";

    public ZaboravljenaLozinkaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zaboravljena_lozinka, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.getRootView().findViewById(R.id.toolbar);
        toolbar.setTitle("Zaboravljena lozinka");

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
    }
}
