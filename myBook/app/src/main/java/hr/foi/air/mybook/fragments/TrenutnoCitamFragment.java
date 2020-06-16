package hr.foi.air.mybook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hr.foi.air.hr.database.entities.Korisnik;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.adapters.TrenutnoCitamAdapter;
import hr.foi.air.mybook.objects.ProcitanaKnjigaObject;

public class TrenutnoCitamFragment extends Fragment {

    private static final String TAG = "TrenutnoCitamFragment";

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

        recyclerView = view.findViewById(R.id.recycler_trenutno_citam);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new TrenutnoCitamAdapter(getContext(),korisnikCita);
        recyclerView.setAdapter(adapter);

    }
}
