package hr.foi.air.mybook.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import hr.foi.air.hr.database.entities.Citanje;
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

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private DatabaseReference databaseReferenceCitanje;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalji_procitane_knjige,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReferenceCitanje = FirebaseDatabase.getInstance().getReference("citanje");

        slikaKnjige=view.findViewById(R.id.img_slika_knjige_detalji_procitane);
        nazivKnjige=view.findViewById(R.id.txt_detalji_procitane_naziv);
        autorKnjige=view.findViewById(R.id.txt_detalji_procitane_autor);
        datumPocetka=view.findViewById(R.id.pocetak_datum);
        datumZavrsetka=view.findViewById(R.id.zavrsetak_datum);
        ocjenaKnjige=view.findViewById(R.id.rating_bar_detalji_procitane);
        komentar=view.findViewById(R.id.txt_komentar_korisnika);
        datumZavrsetka.setInputType(InputType.TYPE_NULL);

        prikaziDetaljeProcitaneKnjige();

        ImageView ikonaDatum=view.findViewById(R.id.zavrsetak_procitana);
        ikonaDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_DeviceDefault_Light_Panel,
                        mDateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FDF7F9")));
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d(TAG, "onDateSet: date dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                datumZavrsetka.setText(date);
            }
        };

        Button spremiPromjene=view.findViewById(R.id.btn_procitana_spremi);
        spremiPromjene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Spremi clicked ");

                spremiPromjene();

                Toast.makeText(getActivity(), "Promjene spremljene ", Toast.LENGTH_SHORT).show();
            }
        });
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

private void spremiPromjene(){

    final String kraj=this.datumZavrsetka.getText().toString().trim();

    databaseReferenceCitanje.orderByChild("korisnikKorime").equalTo(korisnik).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot item : dataSnapshot.getChildren()){
                Citanje citanje=item.getValue(Citanje.class);
                Log.i(TAG, citanje.toString());

                if(citanje.getKnjigaIdKnjiga().equals(idKnjige)){
                    Log.i(TAG, "CITANJE: "+citanje.toString());
                    String citanjeKey=item.getKey();

                    databaseReferenceCitanje.child(citanjeKey).child("datumKraja").setValue(kraj);

                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });


}
}
