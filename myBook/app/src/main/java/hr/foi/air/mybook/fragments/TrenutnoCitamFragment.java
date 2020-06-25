package hr.foi.air.mybook.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.hr.database.entities.Korisnik;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.adapters.TrenutnoCitamAdapter;
import hr.foi.air.mybook.objects.ProcitanaKnjigaObject;

public class TrenutnoCitamFragment extends Fragment {

    private static final String TAG = "TrenutnoCitamFragment";

    private String korisnickoIme;

    private DatabaseReference databaseReferenceKnjiga;
    private DatabaseReference databaseReferenceCitanje;
    private DatabaseReference databaseReferenceKorisnik;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ArrayList<ProcitanaKnjigaObject> korisnikCita = new ArrayList<>();
    private ArrayList<Korisnik> korisnici = new ArrayList<>();

    public TrenutnoCitamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trenutno_citam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String firebaseKorisnik;

        databaseReferenceKnjiga = FirebaseDatabase.getInstance().getReference("knjiga");
        databaseReferenceCitanje = FirebaseDatabase.getInstance().getReference("citanje");
        databaseReferenceKorisnik = FirebaseDatabase.getInstance().getReference("korisnik");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseKorisnik = firebaseAuth.getCurrentUser().getEmail();

        dohvatiKorisnickoIme(firebaseKorisnik);

        recyclerView = view.findViewById(R.id.recycler_trenutno_citam);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new TrenutnoCitamAdapter(getContext(),korisnikCita);
        recyclerView.setAdapter(adapter);

    }

    private void dohvatiKorisnickoIme(final String firebaseKorisnik) {
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
                    if (korisnik.getMail().equals(firebaseKorisnik)) {
                        korisnickoIme = korisnik.getKorime();
                        Log.i(TAG, "Korisnik: "+korisnickoIme);
                    }
                }
                Log.i(TAG, "Dohvacanje knjiga koje se čitaju");
                dohvatiKnjige(korisnickoIme);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    
    private void dohvatiKnjige(String korisnickoIme) {

        final ArrayList<Citanje> citanjeKnjige = new ArrayList<>();

        databaseReferenceCitanje.orderByChild("korisnikKorime").equalTo(korisnickoIme).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                korisnikCita.clear();
                citanjeKnjige.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Citanje citanje = item.getValue(Citanje.class);
                    Log.i(TAG, citanje.toString());
                    citanjeKnjige.add(citanje);
                }
                databaseReferenceKnjiga.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                            final Knjiga book = data.getValue(Knjiga.class);
                            for (Citanje citanjeKnjige:citanjeKnjige){
                                if(citanjeKnjige.getKnjigaIdKnjiga().equals(book.getIdKnjiga())){
                                    if(citanjeKnjige.getDatumKraja().equals("")) {
                                        Log.i(TAG, citanjeKnjige.toString());
                                        ProcitanaKnjigaObject procitanaKnjigaObject = new ProcitanaKnjigaObject();

                                        procitanaKnjigaObject.setIdKnjiga(book.getIdKnjiga());
                                        procitanaKnjigaObject.setAutor(book.getAutor());
                                        procitanaKnjigaObject.setNaziv(book.getNaziv());
                                        procitanaKnjigaObject.setDatumPocetka(citanjeKnjige.getDatumPocetka());
                                        procitanaKnjigaObject.setDatumKraja(citanjeKnjige.getDatumKraja());
                                        procitanaKnjigaObject.setUrlSlike(book.getUrlSlike());
                                        procitanaKnjigaObject.setKomentar(citanjeKnjige.getKomentar());
                                        procitanaKnjigaObject.setOcjena(citanjeKnjige.getOcjena());
                                        procitanaKnjigaObject.setKorisnikKorime(citanjeKnjige.getKorisnikKorime());

                                        korisnikCita.add(procitanaKnjigaObject);
                                    }
                                }
                                prikaziKnjige(korisnikCita);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void prikaziKnjige(ArrayList<ProcitanaKnjigaObject> knjige) {
        adapter = new TrenutnoCitamAdapter(getContext(), knjige);
        recyclerView.setAdapter(adapter);
    }
}

