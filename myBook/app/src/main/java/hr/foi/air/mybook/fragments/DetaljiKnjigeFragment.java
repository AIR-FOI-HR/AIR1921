package hr.foi.air.mybook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.hr.database.entities.Korisnik;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.adapters.PrikazKomentaraAdapter;


public class DetaljiKnjigeFragment extends Fragment {

    private static final String TAG = "DetaljiKnjigeFragment";

    private String korisnickoIme;
    private String idKnjige, naziv;

    private ImageView slikaKnjige;
    private TextView nazivKnjige;
    private TextView opisKnjige;
    private TextView autorKnjige;
    private RatingBar ocjenaKnjige;

    private PrikazKomentaraAdapter adapterKomentari;
    private RecyclerView recyclerViewKomentari;

    private DatabaseReference databaseReferenceCitanje;
    private DatabaseReference databaseReferenceKorisnik;

    private ArrayList<Korisnik> korisnici = new ArrayList<>();
    private ArrayList<Citanje> komentari = new ArrayList<>();


    public DetaljiKnjigeFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalji_knjige,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String firebaseKorisnik;

        databaseReferenceCitanje = FirebaseDatabase.getInstance().getReference("citanje");
        databaseReferenceKorisnik = FirebaseDatabase.getInstance().getReference("korisnik");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseKorisnik = firebaseAuth.getCurrentUser().getEmail();

        dohvatiKorisnickoIme(firebaseKorisnik);

        recyclerViewKomentari = view.findViewById(R.id.recycler_detalji_komentari);
        recyclerViewKomentari.setHasFixedSize(true);
        recyclerViewKomentari.setLayoutManager(new LinearLayoutManager(view.getContext()));

        pocniCitati = view.findViewById(R.id.txt_pocni_citati);
        slikaKnjige = view.findViewById(R.id.img_slika_knjige);
        nazivKnjige = view.findViewById(R.id.txt_detalji_naziv);
        autorKnjige = view.findViewById(R.id.txt_detalji_autor);
        opisKnjige = view.findViewById(R.id.txt_detalji_opis);
        ocjenaKnjige = view.findViewById(R.id.rating_bar_detalji_knjige);

        adapterKomentari = new PrikazKomentaraAdapter(komentari, view.getContext());
        recyclerViewKomentari.setAdapter(adapterKomentari);

        prikaziDetaljeKnjige();
        dohvatiKomentare(idKnjige);


    }

    private void prikaziDetaljeKnjige() {

        String opis, autor, slika;
        Float ocjena;

        if (getArguments() != null) {
            idKnjige = getArguments().getString("id");
            naziv = getArguments().getString("naziv");
            opis = getArguments().getString("opis");
            autor = getArguments().getString("autor");
            slika = getArguments().getString("slika");
            ocjena = getArguments().getFloat("ocjena");

            nazivKnjige.setText(naziv);
            autorKnjige.setText(autor);
            opisKnjige.setText(opis);
            Picasso.with(getContext())
                    .load(slika)
                    .into(slikaKnjige);
            ocjenaKnjige.setRating(ocjena);
        }

    }

    private void dohvatiKorisnickoIme(final String korisnikMail) {
        databaseReferenceKorisnik.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                korisnici.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Korisnik korisnik = item.getValue(Korisnik.class);
                    korisnici.add(korisnik);
                }
                for (Korisnik korisnik : korisnici) {
                    Log.e(TAG, "pronađiKorisnickoIme: " + korisnik);
                    if (korisnik.getMail().equals(korisnikMail)) {
                        korisnickoIme = korisnik.getKorime();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void dohvatiKomentare(final String idKnjige) {
        databaseReferenceCitanje.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                komentari.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Citanje citanje = item.getValue(Citanje.class);
                    if (citanje.getKnjigaIdKnjiga().equals(idKnjige)) {
                        if (!"".equals(citanje.getKomentar())) {
                            komentari.add(citanje);
                            prikaziKomentare(komentari);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void prikaziKomentare(ArrayList<Citanje> komentari) {
        adapterKomentari = new PrikazKomentaraAdapter(komentari, getContext());
        recyclerViewKomentari.setAdapter(adapterKomentari);
    }


}
