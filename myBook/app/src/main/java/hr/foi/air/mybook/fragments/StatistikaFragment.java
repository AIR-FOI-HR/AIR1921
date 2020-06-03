package hr.foi.air.mybook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.hr.database.entities.Korisnik;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.adapters.ProcitaneKnjigeAdapter;
import hr.foi.air.mybook.objects.ProcitanaKnjigaObject;

public class StatistikaFragment extends Fragment {

   private static final String TAG = "StatistikaFragment";

   private String korisnickoIme;
   private TextView brojProcitanihKnjiga;

   private DatabaseReference databaseReferenceKnjiga;
   private DatabaseReference databaseReferenceCitanje;
   private DatabaseReference databaseReferenceKorisnik;

   private RecyclerView recyclerView;
   private RecyclerView.Adapter adapter;

   private ArrayList<ProcitanaKnjigaObject> korisnikKnjige = new ArrayList<>();
   private ArrayList<Korisnik> korisnici = new ArrayList<>();

   public StatistikaFragment() {
      // Required empty public constructor
   }


   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_statistika,container,false);
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

      recyclerView = view.findViewById(R.id.recycler_procitane);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

      adapter = new ProcitaneKnjigeAdapter(view.getContext(), korisnikKnjige);
      recyclerView.setAdapter(adapter);

      brojProcitanihKnjiga = view.findViewById(R.id.txt_broj_procitanih);
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
                   Log.i(TAG, "Korisnik: "+korisnickoIme);
               }
            }
             Log.i(TAG, "Dohvacanje procitanih knjiga");
            dohvatiProcitaneKnjige(korisnickoIme);
         }
         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
      });
   }

   private void dohvatiProcitaneKnjige(final String korisnickoIme){

      final ArrayList<Citanje> citanjeKnjige = new ArrayList<>();
      final ArrayList<Citanje> procitaneKnjige = new ArrayList<>();

        databaseReferenceCitanje.orderByChild("korisnikKorime").equalTo(korisnickoIme).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              korisnikKnjige.clear();
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
                              Log.i(TAG, citanjeKnjige.toString());
                             ProcitanaKnjigaObject procitanaKnjigaObject = new ProcitanaKnjigaObject();

                             procitanaKnjigaObject.setIdKnjiga(book.getIdKnjiga());
                             procitanaKnjigaObject.setAutor(book.getAutor());
                             procitanaKnjigaObject.setNaziv(book.getNaziv());
                             procitanaKnjigaObject.setDatumPocetka("Početak čitanja: " +citanjeKnjige.getDatumPocetka());
                             procitanaKnjigaObject.setDatumKraja("Završetak čitanja: " +citanjeKnjige.getDatumKraja());
                             procitanaKnjigaObject.setUrlSlike(book.getUrlSlike());

                             korisnikKnjige.add(procitanaKnjigaObject);

                             if (!"".equals(citanjeKnjige.getDatumKraja())) {
                                procitaneKnjige.add(citanjeKnjige);
                             }
                          }
                          prikaziKnjige(korisnikKnjige);
                          izracunajBrojProcitanihKnjiga(procitaneKnjige,brojProcitanihKnjiga);
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
      adapter = new ProcitaneKnjigeAdapter(getContext(), knjige);
      recyclerView.setAdapter(adapter);
   }

   private void izracunajBrojProcitanihKnjiga(ArrayList<Citanje> procitane, TextView brojKnjiga){
      brojKnjiga.setText(String.valueOf(procitane.size()));
   }
}
