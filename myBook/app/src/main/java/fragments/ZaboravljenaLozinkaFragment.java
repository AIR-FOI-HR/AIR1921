package fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.air.foi.hr.mybook.R;

public class ZaboravljenaLozinkaFragment extends Fragment {

    private static final String TAG = "ZaboravljenaLozinkaFragment";

    public ZaboravljenaLozinkaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zaboravljena_lozinka, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.getRootView().findViewById(R.id.toolbar);
        toolbar.setTitle("Zaboravljena lozinka");
    }
}
