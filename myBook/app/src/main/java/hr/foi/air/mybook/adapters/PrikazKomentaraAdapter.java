package hr.foi.air.mybook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.mybook.R;

public class PrikazKomentaraAdapter extends RecyclerView.Adapter<PrikazKomentaraAdapter.ViewHolder> {

    private static final String TAG = "PrikazKomentaraAdapter";

    private ArrayList<Citanje> komentari;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView autorKomentara;
        public TextView komentar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            autorKomentara=itemView.findViewById(R.id.autor_komentara);
            komentar=itemView.findViewById(R.id.komentar);
        }
    }

    public PrikazKomentaraAdapter(ArrayList<Citanje> komentari, Context context) {
        this.komentari = komentari;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prikaz_komentara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Citanje currentBook = komentari.get(position);

        holder.autorKomentara.setText(currentBook.getKorisnikKorime());
        holder.komentar.setText(currentBook.getKomentar());
    }

    @Override
    public int getItemCount() {
        return komentari.size();
    }



}
