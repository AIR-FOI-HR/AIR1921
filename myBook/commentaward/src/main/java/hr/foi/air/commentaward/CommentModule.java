package hr.foi.air.commentaward;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.example.commentaward.R;
import com.tbp.foi.hr.core.DataPresenter;
import com.tbp.foi.hr.core.objects.ModulDataObject;

import java.util.List;

public class CommentModule implements DataPresenter {

    private static final String TAG = "CommentModule";

    private List<ModulDataObject> modulData;

    @Override
    public Drawable getImage(Context context) {
        Resources resources = context.getResources();
        if (modulData.size() < 1) {
            Log.i(TAG, "Korisnik nema značku!");
            return ResourcesCompat.getDrawable(resources, R.drawable.comment_bw, null);
        } else {
            int brKomentara = 0;
            for (ModulDataObject data : modulData) {
                if (!data.getKomentar().isEmpty()) {
                    brKomentara += 1;
                }
            }
            Log.i(TAG, "Broj komentara = " + brKomentara);
            if (brKomentara >= 1) {
                Log.i(TAG, "Korisnik ima značku!");
                return ResourcesCompat.getDrawable(resources, R.drawable.comment_color, null);
            }
            else {
                Log.i(TAG, "Korisnik nema značku!");
                return ResourcesCompat.getDrawable(resources, R.drawable.comment_bw, null);
            }
        }
    }

    @Override
    public String getName(Context context) {
        return context.getString(R.string.comment_books);
    }

    @Override
    public String getDescription(Context context) {
        return context.getString(R.string.comment_description);
    }

    @Override
    public void setData(List<ModulDataObject> data) {
        this.modulData = data;
        Log.i(TAG, "Broj knjiga = " + data.size());
    }
}
