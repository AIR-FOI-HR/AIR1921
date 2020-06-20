package hr.foi.air.mybook.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.hr.database.entities.Zanr;
import hr.foi.air.hr.database.entities.ZanrKnjiga;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SveKnjigeFragment extends Fragment {

    private static final String TAG = "SveKnjigeFragment";

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceNew;
    private DatabaseReference databaseZanrovi;
    private DatabaseReference databaseZanrKnjige;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ArrayList<BookListObject> books = new ArrayList<>();

    private Spinner zanr;
    private ArrayAdapter<Zanr> zanrAdapter;
    private ArrayList<Zanr> spinnerDataList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sve_knjige, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference("knjiga");
        databaseReferenceNew = FirebaseDatabase.getInstance().getReference("citanje");
        databaseZanrKnjige = FirebaseDatabase.getInstance().getReference("zanr_knjige");

        databaseZanrovi = FirebaseDatabase.getInstance().getReference("zanr");

        zanr = view.findViewById(R.id.zanr);

        spinnerDataList = new ArrayList<>();
        zanrAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
        zanr.setAdapter(zanrAdapter);
        retrieveData();

        recyclerView = view.findViewById(R.id.recycler_rezultat_filtriranja);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new BookRecyclerAdapter(view.getContext(), books);

        recyclerView.setAdapter(adapter);

        zanr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Zanr selectedZanr = (Zanr) zanr.getSelectedItem();
                Log.i(TAG, "Selected zanr id = " + selectedZanr.getIdZanr() + ", name = " + selectedZanr.getNaziv());
                Log.i(TAG, "Retrieve books from DB");
                getAllBooks(selectedZanr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void retrieveData() {
        databaseZanrovi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Zanr zanr = item.getValue(Zanr.class);
                    spinnerDataList.add(zanr);
                }
                zanrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAllBooks(Zanr filter) {
        databaseZanrKnjige.orderByChild("zanrIdZanr").equalTo(filter.getIdZanr()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot zanrKnjigaData : dataSnapshot.getChildren()) {
                    final ZanrKnjiga zanrKnjiga = zanrKnjigaData.getValue(ZanrKnjiga.class);
                    Log.i(TAG, "Knjige zanra => knjigaid =  " + zanrKnjiga.getKnjigaIdKnjiga() + ", zanrId = " + zanrKnjiga.getZanrIdZanr());

                    //TODO: knjige iz žanra i izračun...
                    databaseReference.orderByChild("idKnjiga").equalTo(zanrKnjiga.getKnjigaIdKnjiga()).addValueEventListener(new ValueEventListener() {
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
