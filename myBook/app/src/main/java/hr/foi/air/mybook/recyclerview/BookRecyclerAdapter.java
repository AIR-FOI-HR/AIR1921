package hr.foi.air.mybook.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hr.foi.air.mybook.R;
import hr.foi.air.mybook.objects.BookListObject;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder> {
    private ArrayList<BookListObject> allBooks;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView bookName;
        public TextView bookDescription;
        public ImageView bookImage;
        public RatingBar bookRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.naslov_knjige);
            bookDescription = itemView.findViewById(R.id.opis_knjige);
            bookImage = itemView.findViewById(R.id.slika_knjige);
            bookRating = itemView.findViewById(R.id.rating_bar_ocjene);
        }
    }

    public BookRecyclerAdapter(Context context, ArrayList<BookListObject> books) {
        allBooks = books;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prikaz_knjiga, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookListObject currentBook = allBooks.get(position);
        Log.i("MSG", currentBook.getNaziv());

        holder.bookName.setText(currentBook.getNaziv());
        holder.bookDescription.setText(currentBook.getSazetak());
        Picasso.with(context)
                .load(currentBook.getUrlSlike())
                .into(holder.bookImage);
        holder.bookRating.setRating(currentBook.getOcjena());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return allBooks.size();
    }
}
