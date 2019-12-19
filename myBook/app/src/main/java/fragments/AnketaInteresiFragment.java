package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.air.foi.hr.mybook.LoginActivity;
import com.air.foi.hr.mybook.R;
import com.air.foi.hr.mybook.RegisterActivity;

public class AnketaInteresiFragment extends Fragment {

    private static final String TAG = "AnketaFragment";

    public AnketaInteresiFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anketa_interesi, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        TODO: popravi error :D
//        Toolbar toolbar = view.getRootView().findViewById(R.id.toolbar);
//        toolbar.setTitle("Anketa");

        Button saveForm = view.findViewById(R.id.buttonSpremiAnketu);
        saveForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.i(TAG, "Prijava clicked!");
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}
