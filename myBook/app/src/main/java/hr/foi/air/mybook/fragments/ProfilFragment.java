package hr.foi.air.mybook.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.FragmentManager;
import hr.foi.air.mybook.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ProfilFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_profil, container, false);

        BottomNavigationView izbornik = (BottomNavigationView) v.findViewById(R.id.nav_view_profil);
        izbornik.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_profil, new PodaciFragment()).commit();

        return v;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment oznaceniFragment =null;

                switch (menuItem.getItemId()){
                    case R.id.nav_podaci:
                        oznaceniFragment=new PodaciFragment();
                        break;
                    case R.id.nav_interesi:
                        oznaceniFragment=new InteresiFragment();
                        break;
                    case R.id.nav_znacke:
                        oznaceniFragment=new ZnackeFragment();
                        break;
                    case R.id.nav_statsitika:
                        oznaceniFragment=new StatistikaFragment();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_profil, oznaceniFragment).commit();


                return true;
        }

    };


}
