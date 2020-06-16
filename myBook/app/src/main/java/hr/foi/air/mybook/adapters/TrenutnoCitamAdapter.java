package hr.foi.air.mybook.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.objects.ProcitanaKnjigaObject;

public class TrenutnoCitamAdapter extends RecyclerView.Adapter<TrenutnoCitamAdapter.ViewHolder>  {

    private ArrayList<ProcitanaKnjigaObject> trenutnoCitam;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nazivKnjige;
        public TextView autorKnjige;
        public TextView datumPocetka;
        public ImageView slikaKnjige;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nazivKnjige=itemView.findViewById(R.id.naslov_knjige_trenutno);
            autorKnjige=itemView.findViewById(R.id.autor_knjige_trenutno);
            datumPocetka=itemView.findViewById(R.id.pocetakcitanja_trenutno);
            slikaKnjige=itemView.findViewById(R.id.slika_knjige_trenutno);

        }
    }

    @NonNull
    @Override
    public TrenutnoCitamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrenutnoCitamAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
