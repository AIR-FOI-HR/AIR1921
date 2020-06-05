package hr.foi.air.mybook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import hr.foi.air.mybook.R;

public class DetaljiProcitaneKnjigeFragment extends Fragment {

    private static final String TAG = "DetaljiProcitaneKnjige";

    private String idKnjige, korisnik;

    private ImageView slikaKnjige;
    private TextView nazivKnjige;
    private TextView autorKnjige;
    private TextView datumPocetka;
    private TextView datumZavrsetka;
    private EditText komentar;
    private RatingBar ocjenaKnjige;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalji_procitane_knjige,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        slikaKnjige=view.findViewById(R.id.img_slika_knjige_detalji_procitane);
        nazivKnjige=view.findViewById(R.id.txt_detalji_procitane_naziv);
        autorKnjige=view.findViewById(R.id.txt_detalji_procitane_autor);
        datumPocetka=view.findViewById(R.id.pocetak_datum);
        datumZavrsetka=view.findViewById(R.id.zavrsetak_datum);
        ocjenaKnjige=view.findViewById(R.id.rating_bar_detalji_procitane);
        komentar=view.findViewById(R.id.txt_komentar_korisnika);
        datumZavrsetka.setInputType(InputType.TYPE_NULL);

        prikaziDetaljeProcitaneKnjige();

    }

    private void prikaziDetaljeProcitaneKnjige(){
        if (getArguments() != null) {
            idKnjige = getArguments().getString("id");
            korisnik=getArguments().getString("korisnik");

            nazivKnjige.setText(getArguments().getString("naziv"));
            autorKnjige.setText(getArguments().getString("autor"));
            Picasso.with(getContext())
                    .load(getArguments().getString("slika"))
                    .into(slikaKnjige);
            datumPocetka.setText(getArguments().getString("datumPocetka"));
            datumZavrsetka.setText(getArguments().getString("datumZavrsetka"));
            ocjenaKnjige.setRating(getArguments().getFloat("ocjena"));
            komentar.setText(getArguments().getString("komentar"));

        }
    }

}
