package hr.foi.air.read10books;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.example.read10books.R;
import com.tbp.foi.hr.core.DataPresenter;
import com.tbp.foi.hr.core.objects.ModulDataObject;

import java.util.List;

public class ReadTenBooksModule implements DataPresenter {

    private static final String TAG = "ReadTenBooksModule";

    private List<ModulDataObject> modulData;

    @Override
    public Drawable getImage(Context context) {
        Resources resources = context.getResources();
        Log.i(TAG, "Modul data size = " + modulData.size());
        if (modulData.size() < 1) {
            Log.i(TAG, "Korisnik nema značku!");
            return ResourcesCompat.getDrawable(resources, R.drawable.reading10_bw, null);
        } else {
            int brProcitanih = 0;
            for (ModulDataObject data : modulData) {
                if (!data.getDatumKrajaCitanja().isEmpty()) {
                    brProcitanih = brProcitanih + 1;
                }
            }
            if (brProcitanih >= 10) {
                Log.i(TAG, "Korisnik ima značku!");
                return ResourcesCompat.getDrawable(resources, R.drawable.reading10_color, null);
            }
            else {
                Log.i(TAG, "Korisnik nema značku!");
                return ResourcesCompat.getDrawable(resources, R.drawable.reading10_bw, null);
            }
        }
    }

    @Override
    public String getName(Context context) {
        return context.getString(R.string.ten_books);
    }

    @Override
    public String getDescription(Context context) {
        return context.getString(R.string.ten_books_description);
    }

    @Override
    public void setData(List<ModulDataObject> data) {
        this.modulData = data;
        Log.i(TAG, "Broj knjiga = " + data.size());
    }
}
