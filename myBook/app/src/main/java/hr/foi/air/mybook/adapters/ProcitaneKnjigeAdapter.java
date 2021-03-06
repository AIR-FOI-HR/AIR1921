package hr.foi.air.mybook.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.fragments.DetaljiProcitaneKnjigeFragment;
import hr.foi.air.mybook.objects.ProcitanaKnjigaObject;

public class ProcitaneKnjigeAdapter extends RecyclerView.Adapter<ProcitaneKnjigeAdapter.ViewHolder> {

    private ArrayList<ProcitanaKnjigaObject> procitaneKnjige;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nazivKnjige;
        public TextView autorKnjige;
        public TextView datumPocetka;
        public TextView datumZavrsetka;
        public ImageView slikaKnjige;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nazivKnjige=itemView.findViewById(R.id.naslov_knjige_statistika);
            autorKnjige=itemView.findViewById(R.id.autor_knjige_statistika);
            datumPocetka=itemView.findViewById(R.id.pocetakcitanja_statistika);
            datumZavrsetka=itemView.findViewById(R.id.zavrsetakcitanja_statistika);
            slikaKnjige=itemView.findViewById(R.id.slika_knjige_statistika);

        }
    }

    public ProcitaneKnjigeAdapter(Context context, ArrayList<ProcitanaKnjigaObject> procitaneKnjige) {
        this.procitaneKnjige=procitaneKnjige;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prikaz_knjige_statistika, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProcitanaKnjigaObject knjiga = procitaneKnjige.get(position);
        Log.i("MSG", knjiga.getNaziv());

        holder.nazivKnjige.setText(knjiga.getNaziv());
        holder.autorKnjige.setText(knjiga.getAutor());
        holder.datumPocetka.setText(knjiga.getDatumPocetka());
        holder.datumZavrsetka.setText(knjiga.getDatumKraja());
        Picasso.with(context)
                .load(knjiga.getUrlSlike())
                .into(holder.slikaKnjige);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetaljiProcitaneKnjigeFragment detaljiProcitaneKnjigeFragment=new DetaljiProcitaneKnjigeFragment();

                Bundle arguments = new Bundle();
                arguments.putString("id", knjiga.getIdKnjiga());
                arguments.putString("naziv", knjiga.getNaziv());
                arguments.putString("autor", knjiga.getAutor());
                arguments.putString("slika", knjiga.getUrlSlike());
                arguments.putString("datumPocetka", knjiga.getDatumPocetka());
                arguments.putString("datumZavrsetka", knjiga.getDatumKraja());
                arguments.putFloat("ocjena",knjiga.getOcjena());
                arguments.putString("komentar",knjiga.getKomentar());
                arguments.putString("korisnik",knjiga.getKorisnikKorime());
                detaljiProcitaneKnjigeFragment.setArguments(arguments);

                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_izbornik, detaljiProcitaneKnjigeFragment)
                        .addToBackStack("")
                        .commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return procitaneKnjige.size();
    }
}
