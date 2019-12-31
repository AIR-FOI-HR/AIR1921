package fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.air.foi.hr.database.entities.Korisnik;
import com.air.foi.hr.database.entities.KorisnikZanr;
import com.air.foi.hr.database.entities.Zanr;
import com.air.foi.hr.mybook.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnketaInteresiFragment extends Fragment {

    private static final String TAG = "AnketaFragment";

    private DatabaseReference databaseZanr;
    private DatabaseReference databaseKorisnikZanr;
    private ArrayList<Zanr> genres = new ArrayList<>();
    private ArrayList<Integer> checkedGenres = new ArrayList<>();
    private Korisnik korisnik;

    public AnketaInteresiFragment(Korisnik korisnik){
        this.korisnik = korisnik;
    }
    public AnketaInteresiFragment(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anketa_interesi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        databaseZanr = FirebaseDatabase.getInstance().getReference("zanr");
        databaseKorisnikZanr = FirebaseDatabase.getInstance().getReference("korisnik_zanr");

        fillWithGenres();


        Button saveForm = view.findViewById(R.id.buttonSpremiAnketu);
        saveForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                for (Integer integer: checkedGenres){
                    KorisnikZanr korisnikZanr = new KorisnikZanr(korisnik.getKorime(), integer.toString());
                    String id = databaseKorisnikZanr.push().getKey();
                    databaseKorisnikZanr.child(id).setValue(korisnikZanr);
                }

                Log.i(TAG, "Save Form Anketa!");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.hide(AnketaInteresiFragment.this);
                fragmentTransaction.replace(R.id.frameReg, loginFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void fillWithGenres() {
        databaseZanr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                genres.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Zanr zanr = data.getValue(Zanr.class);
                    genres.add(zanr);
                    Log.i(TAG, genres.toString());
                }
                addCheckBox();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addCheckBox(){
        LinearLayout listOfGenres = getView().findViewById(R.id.listOfGenres);
        for (int i = 0; i < genres.size(); i++){
            final CheckBox checkBox = new CheckBox(getContext());
            checkBox.setId(i);
            checkBox.setText(genres.get(i).getNaziv());
            checkBox.setTextSize(18);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        checkedGenres.add(buttonView.getId());
                        Log.i(TAG, "ID: " + buttonView.getId() + " Text: "+ buttonView.getText().toString());
                        Log.i(TAG, checkedGenres.toString());
                    }
                    else {
                            if (checkedGenres.contains(buttonView.getId())){
                                checkedGenres.remove(Integer.valueOf(buttonView.getId()) );
                            }
                        Log.i(TAG, checkedGenres.toString());
                    }
                }
            });
            listOfGenres.addView(checkBox);
        }
    }
}
