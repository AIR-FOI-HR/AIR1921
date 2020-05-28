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

import hr.foi.air.mybook.R;


public class DetaljiKnjigeFragment extends Fragment {

    private static final String TAG = "DetaljiKnjigeFragment";

    private ImageView slikaKnjige;
    private TextView nazivKnjige;
    private TextView opisKnjige;
    private TextView autorKnjige;
    private RatingBar ocjenaKnjige;

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


        pocniCitati = view.findViewById(R.id.txt_pocni_citati);
        slikaKnjige = view.findViewById(R.id.img_slika_knjige);
        nazivKnjige = view.findViewById(R.id.txt_detalji_naziv);
        autorKnjige = view.findViewById(R.id.txt_detalji_autor);
        opisKnjige = view.findViewById(R.id.txt_detalji_opis);
        ocjenaKnjige = view.findViewById(R.id.rating_bar_detalji_knjige);


    }
}
