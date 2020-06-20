package hr.foi.air.mybook.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import hr.foi.air.hr.database.entities.Zanr;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.objects.BookListObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        databaseZanrovi = FirebaseDatabase.getInstance().getReference("zanr");

        zanr = view.findViewById(R.id.zanr);

        spinnerDataList = new ArrayList<>();
        zanrAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
        zanr.setAdapter(zanrAdapter);
        retrieveData();
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
}
