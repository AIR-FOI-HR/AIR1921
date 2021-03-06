package hr.foi.air.mybook;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hr.foi.air.mybook.fragments.PocetnaFragment;

public class Home extends Application {

    private static final String TAG = "Home";

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            Log.i(TAG, "User "+ firebaseUser.getEmail() +" is already logged in!");

            Intent intent = new Intent(Home.this, GlavniIzbornikActivity.class);
            //TODO: FLAG_ACTIVITY_CLEAR_TASK -> Brisanje za tipku back (proučiti, što ako se klikne back i otvori app?)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
