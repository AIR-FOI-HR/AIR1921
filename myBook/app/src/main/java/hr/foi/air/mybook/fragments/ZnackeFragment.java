package hr.foi.air.mybook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.tbp.foi.hr.core.DataPresenter;
import com.tbp.foi.hr.core.objects.ModulDataObject;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.commentaward.CommentModule;
import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.hr.database.entities.Korisnik;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.adapters.ZnackeAdapter;
import hr.foi.air.read10books.ReadTenBooksModule;
import hr.foi.air.readingaward.FirstBookModule;

public class ZnackeFragment extends Fragment {

    private static final String TAG = "ZnackeFragment";

    private String korisnickoIme;

    private DatabaseReference databaseReferenceKnjiga;
    private DatabaseReference databaseReferenceCitanje;
    private DatabaseReference databaseReferenceKorisnik;
    private DatabaseReference databaseReferenceZanr;
    private DatabaseReference databaseReferenceZanrKnjige;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    private ArrayList<ModulDataObject> modulDataObjects = new ArrayList<>();
    private ArrayList<Korisnik> korisnici = new ArrayList<>();


    private List<DataPresenter> modules;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_znacke, container, false);
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

        recyclerView = view.findViewById(R.id.recycler_znacke);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        modules = new ArrayList<>();
        modules.add(new FirstBookModule());
        modules.add(new ReadTenBooksModule());
        modules.add(new CommentModule());

        if (modules != null && modules.size() > 0) {
            dohvatiKorisnickoIme(firebaseKorisnik, modules);
        }
    }


    private void dohvatiKorisnickoIme(final String korisnikMail, final List<DataPresenter> modul) {

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
                        Log.i(TAG, "Korisnik: " + korisnickoIme);
                    }
                }
                Log.i(TAG, "Dohvacanje procitanih knjiga");
                dohvatiProcitaneKnjige(korisnickoIme, modul);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void dohvatiProcitaneKnjige(final String korisnickoIme, final List<DataPresenter> modul) {

        final ArrayList<Citanje> citanjeKnjige = new ArrayList<>();

        databaseReferenceCitanje.orderByChild("korisnikKorime").equalTo(korisnickoIme).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                citanjeKnjige.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Citanje citanje = item.getValue(Citanje.class);
                    citanjeKnjige.add(citanje);
                }
                databaseReferenceKnjiga.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        modulDataObjects.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            final Knjiga book = data.getValue(Knjiga.class);
                            for (Citanje citanjeKnjige : citanjeKnjige) {
                                if (citanjeKnjige.getKnjigaIdKnjiga().equals(book.getIdKnjiga())) {
                                    Log.i(TAG, citanjeKnjige.toString());
                                    ModulDataObject modulDataObject = new ModulDataObject();

                                    modulDataObject.setAutorKnjige(book.getAutor());
                                    modulDataObject.setNazivKnjige(book.getNaziv());
                                    modulDataObject.setDatumPocetkaCitanja(citanjeKnjige.getDatumPocetka());
                                    modulDataObject.setDatumKrajaCitanja(citanjeKnjige.getDatumKraja());
                                    modulDataObject.setKomentar(citanjeKnjige.getKomentar());
                                    modulDataObject.setOcjena(citanjeKnjige.getOcjena());

                                    modulDataObjects.add(modulDataObject);
                                }
                            }
                        }
                        for (DataPresenter module : modul) {
                            module.setData(modulDataObjects);
                        }
                        prikaziZnacke(modules);
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

    private void prikaziZnacke(List<DataPresenter> modules) {
        adapter = new ZnackeAdapter(getContext(), modules);
        recyclerView.setAdapter(adapter);
    }
}
