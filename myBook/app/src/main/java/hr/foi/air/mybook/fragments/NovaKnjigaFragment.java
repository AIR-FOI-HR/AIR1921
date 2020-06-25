package hr.foi.air.mybook.fragments;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.hr.database.entities.Zanr;
import hr.foi.air.hr.database.entities.ZanrKnjiga;
import hr.foi.air.mybook.R;

import static android.app.Activity.RESULT_OK;

public class NovaKnjigaFragment extends Fragment {

    private static final String TAG = "NovaKnjigaFragment";
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText naziv;
    private EditText autor;
    private EditText godIzdavanja;
    private EditText opis;
    private Spinner zanr;
    private ImageView dodajSliku;
    private ProgressBar dodajKnjiguProgress;

    private Uri mImageUri;
    private StorageReference mStorageRef;
    private StorageTask uploadTask;

    private ArrayAdapter<Zanr> adapter;
    private ArrayList<Zanr> spinnerDataList;

    private DatabaseReference databaseKnjige;
    private DatabaseReference databaseZanrovi;
    private DatabaseReference databaseZanrKnjiga;

    public NovaKnjigaFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nova_knjiga,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseZanrKnjiga = FirebaseDatabase.getInstance().getReference("zanr_knjige");
        databaseKnjige = FirebaseDatabase.getInstance().getReference("knjiga");
        databaseZanrovi = FirebaseDatabase.getInstance().getReference("zanr");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        naziv = view.findViewById(R.id.txt_dodaj_naziv_knjige);
        autor = view.findViewById(R.id.txt_dodaj_autor);
        godIzdavanja = view.findViewById(R.id.txt_dodaj_god_izdavanja);
        opis = view.findViewById(R.id.txt_dodaj_opis);
        zanr = view.findViewById(R.id.txt_dodaj_žanr);
        Button spremi = view.findViewById(R.id.button_dodaj_spremi);
        dodajSliku = view.findViewById(R.id.icon_slika_knjige);
        dodajKnjiguProgress = view.findViewById(R.id.dodajKnjiguProgress);

        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
        zanr.setAdapter(adapter);
        retrieveData();

        spremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(getActivity(),"Upload u tijeku", Toast.LENGTH_SHORT).show();
                }else {
                    provjeraUnosa();
                    Log.i(TAG, "Spremi clicked!");
                }
            }
        });

        dodajSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(getContext()).load(mImageUri).into(dodajSliku);
        }
    }

    private void retrieveData(){
        ValueEventListener listener = databaseZanrovi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Zanr zanr = item.getValue(Zanr.class);
                    spinnerDataList.add(zanr);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void provjeraUnosa(){
        final String naziv = this.naziv.getText().toString().trim();
        final String autor = this.autor.getText().toString().trim();
        final String godIzdavanja = this.godIzdavanja.getText().toString().trim();
        final String opis = this.opis.getText().toString().trim();

        String regex = "^\\d{1,4}$";
        Pattern pattern = Pattern.compile(regex);

        if (naziv.isEmpty() || autor.isEmpty() || godIzdavanja.isEmpty() || opis.isEmpty())
            Toast.makeText(getActivity(),"Nisu popunjena sva polja", Toast.LENGTH_LONG).show();
        else {
            Matcher matcher = pattern.matcher(godIzdavanja);
            if (matcher.matches()){
                uploadSliku();
            }
            else{
                Toast.makeText(getActivity(),"Godina je pogrešnog formata", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void dodajKnjigu(String url){
        final String naziv = this.naziv.getText().toString().trim();
        final String autor = this.autor.getText().toString().trim();
        final String godIzdavanja = this.godIzdavanja.getText().toString().trim();
        final String opis = this.opis.getText().toString().trim();

        Zanr selectedZanr = (Zanr) zanr.getSelectedItem();
        final String selectedZanrId = selectedZanr.getIdZanr();

        String id = databaseKnjige.push().getKey();
        Knjiga knjiga = new Knjiga(id, naziv, godIzdavanja, opis, autor, url);
        databaseKnjige.child(id).setValue(knjiga);
        ZanrKnjiga zanrKnjiga = new ZanrKnjiga(selectedZanrId,id);
        databaseZanrKnjiga.child(databaseZanrKnjiga.push().getKey()).setValue(zanrKnjiga);
        Toast.makeText(getActivity(),"Spremljeno", Toast.LENGTH_LONG).show();
    }

    private String getFileExtension (Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadSliku(){
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "."
                    + getFileExtension(mImageUri));
           uploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dodajKnjiguProgress.setProgress(0);
                                }
                            },1000);

                            Toast.makeText(getActivity(), "Uspješan upload slike", Toast.LENGTH_SHORT).show();
                            String URL =  taskSnapshot.getMetadata().getReference().getPath();

                            final StorageReference ref = FirebaseStorage.getInstance().getReference().child(URL);
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.i(TAG, "URL: "+ uri.toString());
                                    dodajKnjigu(uri.toString());
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            dodajKnjiguProgress.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(getActivity(), "Niste odabrali sliku", Toast.LENGTH_SHORT).show();
        }
    }
}
