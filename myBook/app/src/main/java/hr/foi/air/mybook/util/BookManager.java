package hr.foi.air.mybook.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hr.foi.air.hr.database.entities.Citanje;
import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.mybook.objects.BookListObject;

public class BookManager {

    private static final String TAG = "BookManager";
    private DatabaseReference databaseReference;

    public BookListObject calculateRating (final Knjiga knjiga) {
        BookListObject bookListObject = new BookListObject();
        databaseReference = FirebaseDatabase.getInstance().getReference("citanje");

        final ArrayList<Citanje> citanjeArrayList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                citanjeArrayList.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Citanje citanje = data.getValue(Citanje.class);
                    if (citanje.getKnjigaIdKnjiga().equals(knjiga.getIdKnjiga())) {
                        Log.i(TAG, citanje.toString());
                        citanjeArrayList.add(citanje);
                    }
                }
                //convertToBookListObject(citanjeArrayList, knjiga);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        bookListObject.setIdKnjiga(knjiga.getIdKnjiga());
        bookListObject.setAutor(knjiga.getAutor());
        bookListObject.setNaziv(knjiga.getNaziv());
        bookListObject.setSazetak(knjiga.getSazetak());
        bookListObject.setUrlSlike(knjiga.getUrlSlike());

        if (citanjeArrayList.isEmpty()) {
            Log.i(TAG, "No rating");
            bookListObject.setOcjena(0);
        }
        else {
            float rating;
            int sum = 0;
            for (Citanje citanje : citanjeArrayList) {
                sum += citanje.getOcjena();
                Log.i(TAG, "Current sum: " + sum);
            }
            rating = sum/citanjeArrayList.size();
            Log.i(TAG, "Rating: " + rating);
            bookListObject.setOcjena(rating);
        }

        return bookListObject;
    }

    private BookListObject convertToBookListObject(ArrayList<Citanje> citanjeArrayList, Knjiga knjiga) {
        BookListObject bookListObject = new BookListObject();

        bookListObject.setIdKnjiga(knjiga.getIdKnjiga());
        bookListObject.setAutor(knjiga.getAutor());
        bookListObject.setNaziv(knjiga.getNaziv());
        bookListObject.setSazetak(knjiga.getSazetak());
        bookListObject.setUrlSlike(knjiga.getUrlSlike());

        if (citanjeArrayList.isEmpty()) {
            Log.i(TAG, "No rating");
            bookListObject.setOcjena(0);
        }
        else {
            float sum = 0;
            for (Citanje citanje : citanjeArrayList) {
                sum += citanje.getOcjena();
                Log.i(TAG, "Current sum: " + sum);
            }
            float rating = sum/citanjeArrayList.size();
            bookListObject.setOcjena(rating);
        }

        return bookListObject;
    }
}
