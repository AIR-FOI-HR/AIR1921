package hr.foi.air.mybook.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.objects.BookListObject;
import hr.foi.air.mybook.recyclerview.BookRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PretragaFragment extends Fragment {

    private static final String TAG = "PretragaFragment";

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceNew;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ArrayList<BookListObject> books = new ArrayList<>();

    private EditText search;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pretraga, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        databaseReference = FirebaseDatabase.getInstance().getReference("knjiga");
        databaseReferenceNew = FirebaseDatabase.getInstance().getReference("citanje");

        Log.i(TAG, "Retrieve books from DB");
        getAllBooks(databaseReference, null);

        recyclerView = view.findViewById(R.id.recycler_rezultat_pretrage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        search = view.findViewById(R.id.search);

        adapter = new BookRecyclerAdapter(view.getContext(), books);

        recyclerView.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "Text = " + s.toString());
                getAllBooks(databaseReference, s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getAllBooks(DatabaseReference databaseReference, CharSequence search) {
        Query query;
        if (search != null && search.length() != 0) {
            query = databaseReference.orderByChild("naziv").startAt(search.toString()).endAt(search.toString() + "\uf8ff");
        } else {
            query = databaseReference;
        }
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                books.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    final Knjiga book = data.getValue(Knjiga.class);
                    Log.i(TAG, book.toString());

                    final ArrayList<Citanje> citanjeArrayList = new ArrayList<>();

                    databaseReferenceNew.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            citanjeArrayList.clear();
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
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
        } else {
            float sum = 0;
            for (Citanje citanje : citanjeArrayList) {
                sum += citanje.getOcjena();
                Log.i(TAG, "Current sum: " + sum);
            }
            float rating = sum / citanjeArrayList.size();
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
