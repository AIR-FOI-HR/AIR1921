package hr.foi.air.mybook.fragments;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.foi.air.hr.database.entities.Knjiga;
import hr.foi.air.mybook.R;
import hr.foi.air.mybook.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PocetnaFragment extends Fragment {

    private static final String TAG = "PocetnaFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pocetna,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tabLayoutPocetna);
        tabLayout.addTab(tabLayout.newTab().setText("Prijedlozi"));
        tabLayout.addTab(tabLayout.newTab().setText("Trenutno čitam"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        openPrijedloziFragment();

//        final ViewPager viewPager=(ViewPager)view.findViewById(R.id.viewPagerPocetna);
//        final ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());


      //  viewPager.setAdapter(adapter);
      //  viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getText() == "Prijedlozi") {
                    Log.i(TAG, "On TAB Prijedlozi!");
                    openPrijedloziFragment();
                }
                else {
                    Log.i(TAG, "On TAB Trenutno čitam!");

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    TrenutnoCitamFragment trenutnoCitamFragment = new TrenutnoCitamFragment();
                    fragmentTransaction.replace(R.id.framePocetna, trenutnoCitamFragment);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void openPrijedloziFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PrijedloziFragment prijedloziFragment = new PrijedloziFragment();
        fragmentTransaction.replace(R.id.framePocetna, prijedloziFragment);
        fragmentTransaction.commit();
    }
}
