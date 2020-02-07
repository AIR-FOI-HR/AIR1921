package com.air.foi.hr.mybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import fragments.NovaKnjigaFragment;
import fragments.PocetnaFragment;
import fragments.PretragaFragment;
import fragments.ProfilFragment;
import fragments.SveKnjigeFragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GlavniIzbornikActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni_izbornik);

        BottomNavigationView bottomNavigationView =findViewById(R.id.nav_view_glavna);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

       getSupportFragmentManager().beginTransaction().replace(R.id.frame_izbornik,new PocetnaFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment oznaceniFragment=null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_pocetna:
                            oznaceniFragment=new PocetnaFragment();
                            break;
                        case R.id.nav_sveknjige:
                            oznaceniFragment=new SveKnjigeFragment();
                            break;
                        case R.id.nav_pretrazi:
                            oznaceniFragment=new PretragaFragment();
                            break;
                        case R.id.nav_novaknjiga:
                            oznaceniFragment=new NovaKnjigaFragment();
                            break;
                        case R.id.nav_profil:
                            oznaceniFragment=new ProfilFragment();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_izbornik,oznaceniFragment).commit();


                    return true;
                }
            };
}
