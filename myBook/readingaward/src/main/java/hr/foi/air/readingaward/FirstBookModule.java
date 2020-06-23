package hr.foi.air.readingaward;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.readingaward.R;
import com.tbp.foi.hr.core.DataPresenter;
import com.tbp.foi.hr.core.objects.ModulDataObject;

import java.util.List;

public class FirstBookModule extends Fragment implements DataPresenter {

    private static final String TAG = "FirstBookModule";

    private List<ModulDataObject> modulData;

    private ImageView image;
    private TextView name;
    private TextView details;
    private TextView information;
    private boolean moduleReadyFlag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reading_award_detalji, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image = view.findViewById(R.id.detalji_znacke_slika);
        name = view.findViewById(R.id.txt_detaljiznacke_naziv);
        details = view.findViewById(R.id.txt_detaljiznacke_opis);
        information = view.findViewById(R.id.txt_detaljiznacke_cestitka);

        moduleReadyFlag = true;
        tryToDisplayData();
    }

    private void tryToDisplayData() {
        if (moduleReadyFlag) {
            displayData();
        }
    }

    private void displayData() {
        name.setText(getContext().getString(R.string.first_book));
        details.setText(getContext().getString(R.string.first_book_description));

        Resources resources = getContext().getResources();
        if (hasPrize()) {
            image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.reading_award_color, null));
            information.setText(getContext().getString(R.string.first_book_awarded));
        } else {
            image.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.reading_award_bw, null));
            int number = 1 - numberOfReadBooks();
            information.setText(String.format("%s%d", getContext().getString(R.string.first_book_info), number));
        }
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public Drawable getImage(Context context) {
        Resources resources = context.getResources();
        if (hasPrize()) {
            return ResourcesCompat.getDrawable(resources, R.drawable.reading_award_color, null);
        } else {
            return ResourcesCompat.getDrawable(resources, R.drawable.reading_award_bw, null);
        }
    }

    @Override
    public String getName(Context context) {
        return context.getString(R.string.first_book);
    }

    @Override
    public String getDescription(Context context) {
        return context.getString(R.string.first_book_description);
    }

    @Override
    public void setData(List<ModulDataObject> data) {
        this.modulData = data;
        Log.i(TAG, "Broj knjiga = " + data.size());
    }

    private boolean hasPrize() {
        int numberOfReadBooks = numberOfReadBooks();
        if (numberOfReadBooks >= 1) {
            Log.i(TAG, "Korisnik ima značku!");
            return true;
        } else {
            Log.i(TAG, "Korisnik nema značku!");
            return false;
        }
    }

    private int numberOfReadBooks() {
        if (modulData.size() < 1) {
            Log.i(TAG, "Korisnik nema značku!");
            return 0;
        } else {
            int brProcitanih = 0;
            for (ModulDataObject data : modulData) {
                if (!data.getDatumKrajaCitanja().isEmpty()) {
                    brProcitanih += 1;
                }
            }
            Log.i(TAG, "Broj pročitanih knjiga = " + brProcitanih);
            return brProcitanih;
        }
    }
}
