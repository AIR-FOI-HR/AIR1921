package hr.foi.air.mybook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hr.foi.air.hr.database.entities.Korisnik;
import hr.foi.air.hr.database.entities.KorisnikZanr;
import hr.foi.air.hr.database.entities.Zanr;
import hr.foi.air.mybook.R;

public class InteresiFragment extends Fragment {

    private static final String TAG = "InteresiFragment";

    private DatabaseReference databaseZanr;
    private DatabaseReference databaseKorisnikZanr;
    private ArrayList<Zanr> genres = new ArrayList<>();
    private ArrayList<Integer> checkedGenres = new ArrayList<>();
    private Korisnik korisnik;
    private ArrayList<Korisnik> korisnici = new ArrayList<>();
    private DatabaseReference databaseReferenceKorisnik;
    private ArrayList<KorisnikZanr> zanroviKorisnika= new ArrayList<>();

    public InteresiFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interesi,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReferenceKorisnik = FirebaseDatabase.getInstance().getReference("korisnik");

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String firebaseUser = firebaseAuth.getCurrentUser().getEmail();

        dohvatiPodatke(firebaseUser);

        databaseZanr = FirebaseDatabase.getInstance().getReference("zanr");
        databaseKorisnikZanr = FirebaseDatabase.getInstance().getReference("korisnik_zanr");

        dohvatiInterese();
        fillWithGenres();


        Button saveForm = view.findViewById(R.id.button_interesi_spremi);
        saveForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                for (Integer integer: checkedGenres){
                    KorisnikZanr korisnikZanr = new KorisnikZanr(korisnik.getKorime(), integer.toString());
                    String id = databaseKorisnikZanr.push().getKey();
                    databaseKorisnikZanr.child(id).setValue(korisnikZanr);
                }

                Toast.makeText(getActivity(), "Uspješna spremanje interesa!", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Save Form Interesi!");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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

    private void dohvatiInterese() {
        databaseKorisnikZanr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                zanroviKorisnika.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    KorisnikZanr zanr = data.getValue(KorisnikZanr.class);
                    zanroviKorisnika.add(zanr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addCheckBox(){
        LinearLayout listOfGenres = getView().findViewById(R.id.listaInteresa);
        for (int i = 0; i < genres.size(); i++){
            final CheckBox checkBox = new CheckBox(getContext());
            checkBox.setId(i);
            checkBox.setText(genres.get(i).getNaziv());
            checkBox.setTextSize(18);
            for (KorisnikZanr odabraniZanr: zanroviKorisnika) {
                if (odabraniZanr.getZanrIdZanr().equals(genres.get(i).getIdZanr()) && korisnik.getKorime().equals(odabraniZanr.getKorisnikKorime())){
                    checkBox.setChecked(true);
                }
            }
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

    private void dohvatiPodatke(final String korisnikMail) {
        databaseReferenceKorisnik.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                korisnici.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Korisnik korisnik = item.getValue(Korisnik.class);
                    korisnici.add(korisnik);
                }
                pronađiKorisnika(korisnikMail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void pronađiKorisnika(String korisnikMail) {
        for (Korisnik k : korisnici) {
            Log.e(TAG, "pronađiKorisnika: " + k);
            if (k.getMail().equals(korisnikMail)) {
                korisnik = new Korisnik(k.getKorime(),k.getIme(),k.getPrezime(),k.getMail(),k.getDatumRodenja());
                Log.i(TAG,"korisnik je" + k.getKorime());
                break;
            }
        }
    }
}
