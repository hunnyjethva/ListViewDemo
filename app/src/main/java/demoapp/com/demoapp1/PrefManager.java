package demoapp.com.demoapp1;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "_Preferences";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setArrayInfor(String arrayInfo) {

        editor.putString("add_data_new", arrayInfo);
        editor.commit();

    }

    public String getArrayInfor() {
        return pref.getString("add_data_new", null);
    }

    public void setTitle(String title) {

        editor.putString("title", title);
        editor.commit();

    }

    public String getTitle() {
        return pref.getString("title", null);
    }




}
