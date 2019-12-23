package fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.air.foi.hr.database.entities.Zanr;
import com.air.foi.hr.mybook.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnketaInteresiFragment extends Fragment {

    private static final String TAG = "AnketaFragment";

    private FirebaseListAdapter adapter;


    public AnketaInteresiFragment(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anketa_interesi, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fillWithGenres();

        Button saveForm = view.findViewById(R.id.buttonSpremiAnketu);
        saveForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.i(TAG, "Save Form Anketa!");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.hide(AnketaInteresiFragment.this);
                fragmentTransaction.replace(R.id.frameReg, loginFragment);
                fragmentTransaction.commit();

//              TODO: spremanje odabira
            }
        });
    }

    private void fillWithGenres() {
        DatabaseReference databaseGenres = FirebaseDatabase.getInstance().getReference().child("zanr");

        ListView listOfGenres = getView().findViewById(R.id.listOfGenres);
        FirebaseListOptions<Zanr> options = new FirebaseListOptions.Builder<Zanr>()
                .setLayout(R.layout.item_anketa)
                .setLifecycleOwner(AnketaInteresiFragment.this)
                .setQuery(databaseGenres, Zanr.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView genre = v.findViewById(R.id.zanr);

                Zanr zanr = (Zanr) model;
                genre.setText(zanr.getNaziv().toString());
            }
        };

        listOfGenres.setAdapter(adapter);
    }
}
