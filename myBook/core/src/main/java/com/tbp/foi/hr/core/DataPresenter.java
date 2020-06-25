package com.tbp.foi.hr.core;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.fragment.app.Fragment;

import com.tbp.foi.hr.core.objects.ModulDataObject;

import java.util.List;

public interface DataPresenter {
    Fragment getFragment();
    Drawable getImage (Context context);
    String getName (Context context);
    String getDescription (Context context);
    void setData (List<ModulDataObject> data);
}
