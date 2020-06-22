package hr.foi.air.readingaward;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.example.readingaward.R;
import com.tbp.foi.hr.core.DataPresenter;
import com.tbp.foi.hr.core.objects.ModulDataObject;

import java.util.List;

public class FirstBookModule implements DataPresenter {

    private static final String TAG = "FirstBookModule";

    private List<ModulDataObject> modulData;

    @Override
    public Drawable getImage(Context context) {
        Resources resources = context.getResources();
        if (modulData.size() < 1) {
            Log.i(TAG, "Korisnik nema značku!");
            return ResourcesCompat.getDrawable(resources, R.drawable.reading_award_bw, null);
        } else {
            int brProcitanih = 0;
            for (ModulDataObject data : modulData) {
                if (!"".equals(data.getDatumKrajaCitanja())) {
                    brProcitanih += 1;
                }
            }
            if (brProcitanih >= 1) {
                Log.i(TAG, "Korisnik ima značku!");
                return ResourcesCompat.getDrawable(resources, R.drawable.reading_award_color, null);
            }
            else {
                Log.i(TAG, "Korisnik nema značku!");
                return ResourcesCompat.getDrawable(resources, R.drawable.reading_award_bw, null);
            }
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
}
