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

import com.google.firebase.database.DatabaseReference;
import hr.foi.air.mybook.R;


public class DetaljiKnjigeFragment extends Fragment {

    private static final String TAG = "DetaljiKnjigeFragment";

    private String korisnickoIme;
    private String idKnjige, naziv;

    private ImageView slikaKnjige;
    private TextView nazivKnjige;
    private TextView opisKnjige;
    private TextView autorKnjige;
    private RatingBar ocjenaKnjige;

    private DatabaseReference databaseReferenceCitanje;
    private DatabaseReference databaseReferenceKorisnik;
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


        databaseReferenceCitanje = FirebaseDatabase.getInstance().getReference("citanje");
        databaseReferenceKorisnik = FirebaseDatabase.getInstance().getReference("korisnik");

        pocniCitati = view.findViewById(R.id.txt_pocni_citati);
        slikaKnjige = view.findViewById(R.id.img_slika_knjige);
        nazivKnjige = view.findViewById(R.id.txt_detalji_naziv);
        autorKnjige = view.findViewById(R.id.txt_detalji_autor);
        opisKnjige = view.findViewById(R.id.txt_detalji_opis);
        ocjenaKnjige = view.findViewById(R.id.rating_bar_detalji_knjige);

        prikaziDetaljeKnjige();

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
}
