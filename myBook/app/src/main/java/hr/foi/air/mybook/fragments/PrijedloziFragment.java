package hr.foi.air.mybook.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.objects.BookListObject;
import hr.foi.air.mybook.recyclerview.BookRecyclerAdapter;
import hr.foi.air.mybook.util.BookManager;


public class PrijedloziFragment extends Fragment {

    private static final String TAG = "PrijedloziFragment";

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceNew;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<BookListObject> books = new ArrayList<>();

    BookManager bookManager = new BookManager();

    public PrijedloziFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prijedlozi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        databaseReference = FirebaseDatabase.getInstance().getReference("knjiga");
        databaseReferenceNew = FirebaseDatabase.getInstance().getReference("citanje");

        Log.i(TAG, "Retrieve books from DB");
        getAllBooks(databaseReference);

        recyclerView = view.findViewById(R.id.recycler_preporuke);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new BookRecyclerAdapter(view.getContext(), books);

        recyclerView.setAdapter(adapter);
    }

    private void getAllBooks(DatabaseReference databaseReference){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                books.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    final Knjiga book = data.getValue(Knjiga.class);
                    Log.i(TAG, book.toString());

                    final ArrayList<Citanje> citanjeArrayList = new ArrayList<>();

                    databaseReferenceNew.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            citanjeArrayList.clear();
                            for (DataSnapshot data: dataSnapshot.getChildren()) {
                                Citanje citanje = data.getValue(Citanje.class);
                                if (citanje.getKnjigaIdKnjiga().equals(book.getIdKnjiga())) {
                                    Log.i(TAG, citanje.toString());
                                    citanjeArrayList.add(citanje);
                                }
                            }
                            calculateRating(citanjeArrayList, book);
                            showData(books);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateRating(ArrayList<Citanje> citanjeArrayList, Knjiga book) {
        BookListObject bookListObject = new BookListObject();

        bookListObject.setIdKnjiga(book.getIdKnjiga());
        bookListObject.setAutor(book.getAutor());
        bookListObject.setNaziv(book.getNaziv());
        bookListObject.setSazetak(book.getSazetak());
        bookListObject.setUrlSlike(book.getUrlSlike());

        if (citanjeArrayList.isEmpty()) {
            Log.i(TAG, "No rating");
            bookListObject.setOcjena(0);
        }
        else {
            float sum = 0;
            int books = 0;
            for (Citanje citanje : citanjeArrayList) {
                if (!citanje.getDatumKraja().isEmpty()) {
                    sum += citanje.getOcjena();
                    books++;
                }
                Log.i(TAG, "Current sum: " + sum);
            }
            float rating = sum/books;
            Log.i(TAG, "Rating: " + rating);
            bookListObject.setOcjena(rating);
        }

        books.add(bookListObject);
    }

    private void showData(ArrayList<BookListObject> books) {
        adapter = new BookRecyclerAdapter(getContext(), books);

        recyclerView.setAdapter(adapter);
    }
}
