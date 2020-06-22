package hr.foi.air.mybook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tbp.foi.hr.core.DataPresenter;

import java.util.List;

import hr.foi.air.mybook.R;

public class ZnackeAdapter extends RecyclerView.Adapter<ZnackeAdapter.ViewHolder> {

    private List<DataPresenter> modules;
    private Context context;

    public ZnackeAdapter(Context context, List<DataPresenter> modules) {
        this.modules = modules;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prikaz_readingaward, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataPresenter modul = modules.get(position);

        holder.nazivZnacke.setText(modul.getName(context));
        holder.opisZnacke.setText(modul.getDescription(context));
        holder.slikaZnacke.setImageDrawable(modul.getImage(context));
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nazivZnacke;
        public TextView opisZnacke;
        public ImageView slikaZnacke;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nazivZnacke = itemView.findViewById(R.id.naziv_znacke);
            opisZnacke = itemView.findViewById(R.id.opis_znacke);
            slikaZnacke = itemView.findViewById(R.id.slika_znacke);
        }
    }

}
