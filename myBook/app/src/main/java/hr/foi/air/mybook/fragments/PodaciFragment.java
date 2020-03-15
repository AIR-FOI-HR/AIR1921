package hr.foi.air.mybook.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hr.foi.air.hr.database.entities.Korisnik;
import hr.foi.air.mybook.R;


public class PodaciFragment extends Fragment {

    public PodaciFragment() {
    }

    private static final String TAG = "ProfilFragment";

    private TextView podaciIme;
    private TextView podaciPrezime;
    private TextView podaciDatRodenja;
    private TextView podaciKorIme;
    private TextView podaciMail;
    private ArrayList<Korisnik> korisnici = new ArrayList<>();

    private DatabaseReference databaseReferenceKorisnik;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_podaci,container,false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        podaciIme = view.findViewById(R.id.txt_podaci_ime);
        podaciPrezime = view.findViewById(R.id.txt_podaci_prezime);
        podaciKorIme = view.findViewById(R.id.txt_korime);
        podaciDatRodenja = view.findViewById(R.id.txt_podaci_datumrod);
        podaciMail = view.findViewById(R.id.txt_podaci_email);

        databaseReferenceKorisnik = FirebaseDatabase.getInstance().getReference("korisnik");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String firebaseUser = firebaseAuth.getCurrentUser().getEmail();

        dohvatiPodatke(firebaseUser);

        TextView forgotPassword = view.findViewById(R.id.txt_podaci_lozinka);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Go to forgotten password!");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ZaboravljenaLozinkaFragment zaboravljenaLozinkaFragment = new ZaboravljenaLozinkaFragment();
                fragmentTransaction.hide(PodaciFragment.this);
                fragmentTransaction.replace(R.id.frame_profil, zaboravljenaLozinkaFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void dohvatiPodatke(final String korisnikMail) {
        databaseReferenceKorisnik.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                korisnici.clear();
                for (DataSnapshot item: dataSnapshot.getChildren()){
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


        for (Korisnik korisnik: korisnici) {
            Log.e(TAG, "pronađiKorisnika: "+ korisnik);
            if (korisnik.getMail().equals(korisnikMail)){
                podaciIme.setText(korisnik.getIme());
                podaciPrezime.setText(korisnik.getPrezime());
                podaciKorIme.setText(korisnik.getKorime());
                podaciDatRodenja.setText(korisnik.getDatumRodenja());
                podaciMail.setText(korisnik.getMail());
                break;
            }
        }
    }
}
