package hr.foi.air.mybook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.hr.database.entities.Korisnik;
import hr.foi.air.mybook.R;

public class StatistikaFragment extends Fragment {

   private DatabaseReference databaseReferenceKnjiga;
   private DatabaseReference databaseReferenceCitanje;
   private DatabaseReference databaseReferenceKorisnik;
   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_statistika,container,false);
   }
      databaseReferenceKnjiga = FirebaseDatabase.getInstance().getReference("knjiga");
      databaseReferenceCitanje = FirebaseDatabase.getInstance().getReference("citanje");
      databaseReferenceKorisnik = FirebaseDatabase.getInstance().getReference("korisnik");
}
